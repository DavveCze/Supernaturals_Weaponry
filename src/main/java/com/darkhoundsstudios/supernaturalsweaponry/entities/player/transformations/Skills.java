package com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.effects.bleeding.BleedingEffectInstance;
import net.java.games.input.Component;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Skills {
    static class Werewolf_Skills{
        Skill Wolf_form = new Skill() {
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_health_wf"),SupernaturalWeaponry.Mod_ID + ":werewolf_health_wf",10, AttributeModifier.Operation.ADDITION));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_wf"),SupernaturalWeaponry.Mod_ID + ":werewolf_speed_wf",0.35, AttributeModifier.Operation.ADDITION));

                Skill.effects.add(Effects.HUNGER);
                Skill.effects.add(Effects.JUMP_BOOST);
                entity.entityDropItem(entity.getHeldItemMainhand());
                entity.entityDropItem(entity.getHeldItemOffhand());
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.CHEST));
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.HEAD));
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.LEGS));
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.FEET));
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(1));
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(effects.get(0),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(0))).getAmplifier() + 2,false,false));
                entity.addPotionEffect(new EffectInstance(effects.get(1),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(1))).getAmplifier() + 1,false,false));
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(1));

                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(attributes.get(1));
            }
        };
        Skill Regeneration = new Skill() {
            Skill parent = Survival_instinct;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                effects.add(Effects.REGENERATION);

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(effects.get(0),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(0))).getAmplifier() + 1,false,false));
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Poison_Reduction_I = new Skill() {
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                effects.add(Effects.POISON);

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                EffectInstance instance = entity.getActivePotionEffect(effects.get(0));
                assert instance != null;
                if(instance.getAmplifier() > 0){
                    entity.addPotionEffect(new EffectInstance(effects.get(0),instance.getDuration(),instance.getAmplifier() - 1,instance.isAmbient(),instance.doesShowParticles()));
                }
                else
                    entity.removePotionEffect(Effects.POISON);
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };

        Skill Strength_I = new Skill() {
            public int cost = 1;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_strength"), SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 2, AttributeModifier.Operation.ADDITION));

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attributes.get(0));
            }
        };
        Skill Dire_form = new Skill() {
            Skill parent = Strength_I;
            public int cost = 2;

            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_health_df"), SupernaturalWeaponry.Mod_ID + ":werewolf_health_df", 15, AttributeModifier.Operation.ADDITION));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_df"), SupernaturalWeaponry.Mod_ID + ":werewolf_armor_df", 10, AttributeModifier.Operation.ADDITION));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_df"), SupernaturalWeaponry.Mod_ID + ":werewolf_strength_df", 2, AttributeModifier.Operation.ADDITION));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_df"), SupernaturalWeaponry.Mod_ID + ":werewolf_speed_df", 0.2, AttributeModifier.Operation.ADDITION));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_kn_res_df"), SupernaturalWeaponry.Mod_ID + ":werewolf_kn_res_df", 3, AttributeModifier.Operation.ADDITION));

                Skill.effects.add(Effects.REGENERATION);
                Skill.effects.add(Effects.HUNGER);
                entity.entityDropItem(entity.getHeldItemMainhand());
                entity.entityDropItem(entity.getHeldItemOffhand());
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.CHEST));
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.HEAD));
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.LEGS));
                entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.FEET));
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(attributes.get(2));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(3));
                entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(attributes.get(4));
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(effects.get(0), 300, Objects.requireNonNull(entity.getActivePotionEffect(effects.get(0))).getAmplifier() + 2, false, false));
                entity.addPotionEffect(new EffectInstance(effects.get(1), 300, Objects.requireNonNull(entity.getActivePotionEffect(effects.get(1))).getAmplifier() + 2, false, false));
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(attributes.get(2));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(3));
                entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(attributes.get(4));

                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR).applyModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attributes.get(2));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(attributes.get(3));
                entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(attributes.get(4));
            }
        };
        Skill Strength_II = new Skill() {
            Skill parent = Dire_form;
            public int cost = 2;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_strength"), SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 3, AttributeModifier.Operation.ADDITION));

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attributes.get(0));
            }
        };
        Skill Strength_III = new Skill() {
            Skill parent = Strength_II;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_strength"), SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 4, AttributeModifier.Operation.ADDITION));

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attributes.get(0));
            }
        };
        Skill Rending_Slashes = new Skill() {
            Skill parent = Strength_II;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity target) {

                Skill.attributes.clear();
                Skill.effects.clear();

                applyEffects(target);
                applyModifiers(target);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            public void onUse(LivingEntity target, LivingEntity attacker) {
                Skill.attributes.clear();
                Skill.effects.clear();

                int x = new Random().nextInt(4);
                ItemStack stack = null;
                switch (x){
                    case 0: stack = target.getItemStackFromSlot(EquipmentSlotType.HEAD); break;
                    case 1: stack = target.getItemStackFromSlot(EquipmentSlotType.CHEST); break;
                    case 2: stack = target.getItemStackFromSlot(EquipmentSlotType.LEGS); break;
                    case 3: stack = target.getItemStackFromSlot(EquipmentSlotType.FEET); break;
                }
                ItemStack finalStack = stack;
                stack.damageItem((int) (stack.getDamage() * Math.random()), attacker, (p_220045_0_) -> p_220045_0_.sendBreakAnimation(Objects.requireNonNull(finalStack.getEquipmentSlot())));
                if(Math.random() * 100 < 2.5) {
                    target.entityDropItem(stack);
                }
                applyEffects(target);
                applyModifiers(target);
            }

            @Override
            public void applyEffects(LivingEntity target) {

            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Bleeding = new Skill() {
            Skill parent = Strength_III;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity target) {
                Skill.attributes.clear();
                Skill.effects.clear();

                applyEffects(target);
                applyModifiers(target);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity target) {
                if(Math.random() * 100 < 10) {
                    int x = 0;
                    for (EffectInstance effect : target.getActivePotionEffects()) {
                        if(effect.getPotion() == ModEffects.BLEEDING.get() && effect.getAmplifier() < 3)
                            x = effect.getAmplifier() + 1;
                    }
                    target.addPotionEffect(new BleedingEffectInstance( 10000, x, false, true));
                }
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Bloodlust = new Skill() {
            Skill parent = Rending_Slashes;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }

            @Override
            public void applyModifiers(LivingEntity entity) {

            }
            public void addHunger(LivingEntity entity,int amount, float multiplier){
                ((PlayerEntity) entity).getFoodStats().addStats(amount, multiplier);
            }
        };
        Skill Bestial_Rage = new Skill() {
            Skill parent_1 = Bloodlust;
            Skill parent_2 = Bleeding;
            public int cost = 5;

            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_br"), SupernaturalWeaponry.Mod_ID + ":werewolf_strength_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_br"), SupernaturalWeaponry.Mod_ID + ":werewolf_speed_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_atk_spd_br"), SupernaturalWeaponry.Mod_ID + ":werewolf_atk_spd_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));

                effects.add(Effects.BLINDNESS);
                effects.add(Effects.HUNGER);

                entity.addPotionEffect(new EffectInstance(effects.get(0),100,1,false,false));
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(attributes.get(2));
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(effects.get(0),300,10,false,false));
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(attributes.get(2));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(attributes.get(2));
            }

            public void addHunger(LivingEntity entity,int amount, float multiplier){
                ((PlayerEntity) entity).getFoodStats().addStats(amount, multiplier);
            }
        };

        Skill Faster_Regeneration = new Skill() {
            public int cost = 1;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                effects.add(Effects.REGENERATION);

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(effects.get(0),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(0))).getAmplifier() + 1,false,false));
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Survival_instinct = new Skill() {
            Skill parent = Faster_Regeneration;
            public int cost = 2;
            @Override
            public void onUse(LivingEntity entity) {
                Wolf_form.onUse(entity);
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE,100,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(0))).getAmplifier() + 2,false,false));
            }

            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Armor_I = new Skill() {
            Skill parent = Survival_instinct;
            public int cost = 2;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t"), SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 2, AttributeModifier.Operation.ADDITION));

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(attributes.get(0));
            }
        };
        Skill Armor_II = new Skill() {
            Skill parent = Armor_I;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t"), SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 4, AttributeModifier.Operation.ADDITION));

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(attributes.get(0));
            }
        };
        Skill Poison_Reduction_II = new Skill() {
            Skill parent = Armor_I;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                effects.add(Effects.POISON);

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                EffectInstance instance = entity.getActivePotionEffect(effects.get(0));
                assert instance != null;
                if(instance.getAmplifier() > 1){
                    entity.addPotionEffect(new EffectInstance(effects.get(0),instance.getDuration(),instance.getAmplifier() - 2,instance.isAmbient(),instance.doesShowParticles()));
                }
                else
                    entity.removePotionEffect(Effects.POISON);
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Armor_III = new Skill() {
            Skill parent = Armor_II;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t"), SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 8, AttributeModifier.Operation.ADDITION));

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {

            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(attributes.get(0));
            }
        };
        Skill Wither_Reduction = new Skill() {
            Skill parent = Poison_Reduction_II;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                effects.add(Effects.WITHER);

                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                EffectInstance instance = entity.getActivePotionEffect(effects.get(0));
                assert instance != null;
                if(instance.getAmplifier() > 0){
                    entity.addPotionEffect(new EffectInstance(effects.get(0),instance.getDuration(),instance.getAmplifier() - 1,instance.isAmbient(),instance.doesShowParticles()));
                }
                else
                    entity.removePotionEffect(Effects.POISON);
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }
        };
        Skill Hyper_Armor = new Skill() {
            Skill parent_1 = Armor_III;
            Skill parent_2 = Wither_Reduction;
            public int cost = 5;
            @Override
            public void onUse(LivingEntity entity) {
                Skill.attributes.clear();
                Skill.effects.clear();
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_ha"), SupernaturalWeaponry.Mod_ID + ":werewolf_armor_ha", 15, AttributeModifier.Operation.ADDITION));
                Skill.attributes.add(new AttributeModifier(UUID.fromString(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t_ha"), SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t_ha", 5, AttributeModifier.Operation.ADDITION));

                effects.add(Effects.SLOWNESS);
                effects.add(Effects.ABSORPTION);
                effects.add(Effects.MINING_FATIGUE);
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(attributes.get(1));
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(effects.get(0),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(0))).getAmplifier() + 2,false,false));
                entity.addPotionEffect(new EffectInstance(effects.get(1),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(1))).getAmplifier() + 5,false,false));
                entity.addPotionEffect(new EffectInstance(effects.get(2),300,Objects.requireNonNull(entity.getActivePotionEffect(effects.get(2))).getAmplifier() + 3,false,false));
            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(attributes.get(1));
                entity.getAttribute(SharedMonsterAttributes.ARMOR).applyModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(attributes.get(1));
            }
        };

    }
}
