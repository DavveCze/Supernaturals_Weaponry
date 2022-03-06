package com.darkhoundsstudios.supernaturalsweaponry.entities.entity.werewolf.wolf;

import com.darkhoundsstudios.supernaturalsweaponry.entities.ModCreatureAttribute;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;

public class Werewolf_Wolf extends WolfEntity {

    Entity lastAttacker;

    public Werewolf_Wolf(EntityType<Werewolf_Wolf> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    //registuje všechny attributy entity a jejich modifiery
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4d);
        this.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(4d);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5d);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25d);
        this.heal(getMaxHealth()-getHealth());
    }

    @Override
    //vrací to že je entita nadpřirozená
    public CreatureAttribute getCreatureAttribute() {
        return ModCreatureAttribute.Supernatural;
    }

    //registuje všechny úkoly co entita má
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(1, new LookAtGoal(this, ModPlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new LookAtGoal(this, AbstractVillagerEntity.class, 8.0F));
    }

    @Override
    //zjišťuje zda může zaútočit na entitu
    public boolean canAttack(LivingEntity target) {
        if (target.isAlive())
            if (target instanceof ServerPlayerEntity && target == ModEventBusEvents.getServerPlayer() && !target.getEntity().equals(lastAttacker))
                if (ModEventBusEvents.getPlayer().transformation != null)
                    if (ModEventBusEvents.getPlayer().transformation.getTransType().equals("Werewolf"))
                        return false;
        return super.canAttack(target);
    }

    @Override
    //zjišťuje kdo na něho zaútočil
    public boolean attackEntityFrom(DamageSource source, float amount) {
        lastAttacker = source.getTrueSource();
        return super.attackEntityFrom(source, amount);
    }


    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @Override
    //ukládá o něm data při opouštění světa
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
    }

    @Override
    //čte data a zapisuje je při načítání světa
    public void read(CompoundNBT compound) {
        super.read(compound);
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
    }

    @Override
    public void setTamed(boolean tamed) {
        tamed = false;
    }

    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        return false;
    }

    @Override
    //vrací random hodnotu xp co z něho vypadnou po smrti
    protected int getExperiencePoints(PlayerEntity player) {
        return new Random().nextInt(60);
    }


    int updateTick = 120;
    @Override
    //aplikuje effekt každých 6 vteřin
    public void tick() {
        super.tick();
        if(updateTick >= 0)
            updateTick--;
        else {
            updateTick = 120;
            this.addPotionEffect(new EffectInstance(Effects.SPEED, 300,0,false,false,false));
        }
    }
}
