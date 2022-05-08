package com.darkhoundsstudios.supernaturalsweaponry.entities.player;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.TransformIntoTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.client.particle.ModParticles;
import com.darkhoundsstudios.supernaturalsweaponry.entities.ModCreatureAttribute;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations.Transformation;
import com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IPlayerFileData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModPlayerEntity extends PlayerEntity implements IPlayerFileData{
    //Transformation things
    public List<IAttributeInstance> attributes = new ArrayList<>();
    public List<AttributeModifier> modifiers;
    public PlayerEntity playerEntity;
    public Transformation transformation;
    public boolean canTransformW = false;
    public TransformationType type;


    //Leveling things
    private float LevelXp;
    private int LevelPoints;

    public ModPlayerEntity(PlayerEntity _playerEntity) {
        super(_playerEntity.getEntityWorld(), _playerEntity.getGameProfile());
        this.playerEntity = _playerEntity;
        addAttributesToList();
        initPlayer(false);
    }

    public void initPlayer(boolean levelUp) {
        if(!levelUp) {
            LevelXp = 0;
            LevelPoints = 0;
        }
        ResetAttributes();
        ApplyModifiers();
        getCreatureAttribute();
        System.out.println(playerEntity.getPersistentData());
    }

    @Override
    public @NotNull CreatureAttribute getCreatureAttribute() {
        if(transformation != null){
            return ModCreatureAttribute.Supernatural;
        }
        return super.getCreatureAttribute();
    }


    private void addAttributesToList()
    {
        attributes.clear();
        attributes.addAll(playerEntity.getAttributes().getAllAttributes());
    }

    private void ResetAttributes() {
        for (IAttributeInstance attribute : attributes) {
            attribute.removeAllModifiers();
        }
    }

    private void ResetEffects() {
        if (!getActivePotionEffects().isEmpty())
            for (EffectInstance effect : getActivePotionEffects()) {
                removeActivePotionEffect(effect.getPotion());
            }
    }

    private void ApplyModifiers(){
        if(transformation != null) {
            modifiers = Transformation.getModifiers();
            if (modifiers != null) {
                for (IAttributeInstance attribute : attributes) {
                    IAttribute attributeAttribute = attribute.getAttribute();
                    if (SharedMonsterAttributes.MAX_HEALTH.equals(attributeAttribute)) {
                        playerEntity.getAttribute(attribute.getAttribute()).applyModifier(modifiers.get(0));
                    } else if (SharedMonsterAttributes.ATTACK_DAMAGE.equals(attributeAttribute)) {
                        playerEntity.getAttribute(attribute.getAttribute()).applyModifier(modifiers.get(1));
                    } else if (SharedMonsterAttributes.ARMOR_TOUGHNESS.equals(attributeAttribute)) {
                        playerEntity.getAttribute(attribute.getAttribute()).applyModifier(modifiers.get(2));
                    }
                }
            }
            transformation.ApplyEffects(playerEntity);
        }
    }

    public void setTransformation(Transformation transformation){
        this.transformation = transformation;
        switch (transformation.getTransType()) {
            case "Werewolf":
                type = TransformationType.Werewolf;
                break;
            case "Hunter":
                type = TransformationType.Hunter;
                break;
            case "Vampire":
                type = TransformationType.Vampire;
                break;
            default:
                type = null;
                break;
        }
        writePlayerData(playerEntity);
        initPlayer(false);
    }

    private void setTransformation(){
        initPlayer(true);
    }

    public void customAttackTargetEntityWithCurrentItem(PlayerEntity playerEntity, Entity targetEntity) {
        if (!ModPlayerHooks.onPlayerOffhandAttackTarget(playerEntity, targetEntity)) {
            return;
        }
        if (targetEntity.canBeAttackedWithItem()) {
            if (!targetEntity.hitByEntity(playerEntity)) {
                float f = (float) playerEntity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue();
                float f1;
                if (targetEntity instanceof LivingEntity) {
                    f1 = EnchantmentHelper.getModifierForCreature(playerEntity.getHeldItemOffhand(), ((LivingEntity) targetEntity).getCreatureAttribute());
                } else {
                    f1 = EnchantmentHelper.getModifierForCreature(playerEntity.getHeldItemOffhand(), CreatureAttribute.UNDEFINED);
                }

                float f2 = playerEntity.getCooledAttackStrength(0.5F);
                f = f * (0.2F + f2 * f2 * 0.8F);
                f1 = f1 * f2;
                //playerEntity.resetCooldown();
                if (f > 0.0F || f1 > 0.0F) {
                    boolean flag = f2 > 0.9F;
                    boolean flag1 = false;
                    int i = 0;
                    i = i + EnchantmentHelper.getKnockbackModifier(playerEntity);
                    if (playerEntity.isSprinting() && flag) {
                        playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                        ++i;
                        flag1 = true;
                    }

                    boolean flag2 = flag && playerEntity.fallDistance > 0.0F && !playerEntity.onGround && !playerEntity.isOnLadder() && !playerEntity.isInWater() && !playerEntity.isPotionActive(Effects.BLINDNESS) && !playerEntity.isPassenger() && targetEntity instanceof LivingEntity;
                    flag2 = flag2 && !playerEntity.isSprinting();
                    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(playerEntity, targetEntity, flag2, flag2 ? 1.5F : 1.0F);
                    flag2 = hitResult != null;
                    if (flag2) {
                        f *= hitResult.getDamageModifier();
                    }

                    f = f + f1;
                    boolean flag3 = false;
                    double d0 = (double) (playerEntity.distanceWalkedModified - playerEntity.prevDistanceWalkedModified);
                    if (flag && !flag2 && !flag1 && playerEntity.onGround && d0 < (double) playerEntity.getAIMoveSpeed()) {
                        ItemStack itemstack = playerEntity.getHeldItem(Hand.OFF_HAND);
                        if (itemstack.getItem() instanceof SwordItem) {
                            flag3 = true;
                        }
                    }

                    float f4 = 0.0F;
                    boolean flag4 = false;
                    int j = EnchantmentHelper.getFireAspectModifier(playerEntity);
                    if (targetEntity instanceof LivingEntity) {
                        f4 = ((LivingEntity) targetEntity).getHealth();
                        if (j > 0 && !targetEntity.isBurning()) {
                            flag4 = true;
                            targetEntity.setFire(1);
                        }
                    }

                    Vec3d vec3d = targetEntity.getMotion();
                    boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(playerEntity), f);
                    if (flag5) {
                        if (i > 0) {
                            if (targetEntity instanceof LivingEntity) {
                                ((LivingEntity) targetEntity).knockBack(playerEntity, (float) i * 0.5F, (double) MathHelper.sin(playerEntity.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(playerEntity.rotationYaw * ((float) Math.PI / 180F))));
                            } else {
                                targetEntity.addVelocity((double) (-MathHelper.sin(playerEntity.rotationYaw * ((float) Math.PI / 180F)) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(playerEntity.rotationYaw * ((float) Math.PI / 180F)) * (float) i * 0.5F));
                            }

                            playerEntity.setMotion(playerEntity.getMotion().mul(0.6D, 1.0D, 0.6D));
                            playerEntity.setSprinting(false);
                        }

                        if (flag3) {
                            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(playerEntity) * f;

                            for (LivingEntity livingentity : playerEntity.world.getEntitiesWithinAABB(LivingEntity.class, targetEntity.getBoundingBox().grow(1.0D, 0.25D, 1.0D))) {
                                if (livingentity != playerEntity && livingentity != targetEntity && !playerEntity.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).hasMarker()) && playerEntity.getDistanceSq(livingentity) < 9.0D) {
                                    livingentity.knockBack(playerEntity, 0.4F, (double) MathHelper.sin(playerEntity.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(playerEntity.rotationYaw * ((float) Math.PI / 180F))));
                                    livingentity.attackEntityFrom(DamageSource.causePlayerDamage(playerEntity), f3);
                                }
                            }

                            playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                            playerEntity.spawnSweepParticles();

                        }

                        if (targetEntity instanceof ServerPlayerEntity && targetEntity.velocityChanged) {
                            ((ServerPlayerEntity) targetEntity).connection.sendPacket(new SEntityVelocityPacket(targetEntity));
                            targetEntity.velocityChanged = false;
                            targetEntity.setMotion(vec3d);
                        }

                        if (flag2) {
                            playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                            playerEntity.onCriticalHit(targetEntity);
                        }

                        if (!flag2 && !flag3) {
                            if (flag) {
                                playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                            } else {
                                playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                            }
                        }

                        if (f1 > 0.0F) {
                            playerEntity.onEnchantmentCritical(targetEntity);
                        }

                        playerEntity.setLastAttackedEntity(targetEntity);
                        if (targetEntity instanceof LivingEntity) {
                            EnchantmentHelper.applyThornEnchantments((LivingEntity) targetEntity, playerEntity);
                        }

                        EnchantmentHelper.applyArthropodEnchantments(playerEntity, targetEntity);
                        ItemStack itemstack1 = playerEntity.getHeldItemOffhand();
                        Entity entity = targetEntity;
                        if (targetEntity instanceof EnderDragonPartEntity) {
                            entity = ((EnderDragonPartEntity) targetEntity).dragon;
                        }

                        if (!playerEntity.world.isRemote && !itemstack1.isEmpty() && entity instanceof LivingEntity) {
                            ItemStack copy = itemstack1.copy();
                            itemstack1.hitEntity((LivingEntity) entity, playerEntity);
                            if (itemstack1.isEmpty()) {
                                net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(playerEntity, copy, Hand.OFF_HAND);
                                playerEntity.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
                            }
                        }

                        if (targetEntity instanceof LivingEntity) {
                            float f5 = f4 - ((LivingEntity) targetEntity).getHealth();
                            playerEntity.addStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                            if (j > 0) {
                                targetEntity.setFire(j * 4);
                            }

                            if (playerEntity.world instanceof ServerWorld && f5 > 2.0F) {
                                int k = (int) ((double) f5 * 0.5D);
                                ((ServerWorld) playerEntity.world).spawnParticle(ParticleTypes.DAMAGE_INDICATOR, targetEntity.getPosX(), targetEntity.getPosY() + (double) (targetEntity.getHeight() * 0.5F), targetEntity.getPosZ(), k, 0.1D, 0.0D, 0.1D, 0.2D);
                            }
                        }

                        playerEntity.addExhaustion(0.1F);
                    } else {
                        playerEntity.world.playSound((PlayerEntity) null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, playerEntity.getSoundCategory(), 1.0F, 1.0F);
                        if (flag4) {
                            targetEntity.extinguish();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isSpectator() {
        return playerEntity.isSpectator();
    }

    @Override
    public boolean isCreative() {
        return playerEntity.isCreative();
    }

    private void checkLvlUp() {
        if(transformation != null) {
            if (transformation.getLevel() > 0) {
                int lvlBef = transformation.getLevel();
                if (transformation.increaseLevel(LevelXp)) {
                    System.out.println("Level Up!!!");
                    SummonLvlUpParticle(ModEventBusEvents.getPlayer());
                    int x = Math.max(transformation.getLevel() - lvlBef, 1);
                    setTransformation();
                    for (int i = 0; i < x; i++) {
                        if (transformation.getLevel() == 10) {
                            LevelPoints += 5;
                            break;
                        } else
                            LevelPoints++;
                    }
                }
            }
        }
    }

    private void SummonLvlUpParticle(ModPlayerEntity player){
        for (int i = 0; i < 5; ++i) {
            ((ServerWorld) player.world).spawnParticle(ModParticles.LEVEL_UP_PARTICLE.get(), player.getPosXRandom(1.0D), player.getPosYRandom(), player.getPosZRandom(1.0D),
                    0, 0, 0.0D, 0, 0.1d);
        }
    }

    int updateTick = 120;
    public void playerTick() {
        if (transformation != null) {
            if (updateTick >= 0)
                updateTick--;
            else {
                updateTick = 120;
                ResetEffects();
                transformation.ApplyEffects(playerEntity);
                checkLvlUp();
            }
        }
    }

    @Override
    public void writePlayerData(@NotNull PlayerEntity player) {
        if(transformation != null) {
            player.getPersistentData().putString("transformation_name", transformation.getTransType());
            player.getPersistentData().putInt("transformation_level", transformation.getLevel());
        }
        else
        {
            player.getPersistentData().putString("transformation_name", "");
            player.getPersistentData().putInt("transformation_level", 0);
        }

        player.getPersistentData().putFloat("transformation_xp", LevelXp);
        player.getPersistentData().putInt("transformation_sp", LevelPoints);
    }
    public void writePlayerData(ServerPlayerEntity player) {
        if(transformation != null) {
            player.getPersistentData().putString("transformation_name", transformation.getTransType());
            player.getPersistentData().putInt("transformation_level", transformation.getLevel());
        }
        else
        {
            player.getPersistentData().putString("transformation_name", "");
            player.getPersistentData().putInt("transformation_level", 0);
        }

        player.getPersistentData().putFloat("transformation_xp", LevelXp);
        player.getPersistentData().putInt("transformation_sp", LevelPoints);
    }

    public void giveExperiencePoints(int amount) {
        System.out.println(amount);
        if(transformation != null) {
            this.LevelXp += amount;
            checkLvlUp();
        }
    }

    @Nullable
    @Override
    public CompoundNBT readPlayerData(@NotNull PlayerEntity player) {
        try {
            String transformation_name = player.getPersistentData().getString("transformation_name");
            int transformation_level = player.getPersistentData().getInt("transformation_level");
            this.LevelXp = player.getPersistentData().getFloat("transformation_xp");
            this.LevelPoints = player.getPersistentData().getInt("transformation_sp");
            if(!transformation_name.equals("") && transformation_level > 0){
                transformation = new Transformation(transformation_name, transformation_level);
                ApplyModifiers();
            }
            else
                System.out.println("Player is not Supernatural!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return player.getPersistentData();
    }

    public enum TransformationType{
        Werewolf,
        Hunter,
        Vampire
    }
}
