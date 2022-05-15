package com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.client.particle.LevelUp_Particle;
import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.effects.bleeding.BleedingEffectInstance;
import com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.*;

public class Skills {
    public static class Werewolf_Skills{
        public static ArrayList<Skill> skill_list = new ArrayList<>();

        public static void init(){
            skill_list.clear();
            skill_list.add(Wolf_form);
            skill_list.add(Regeneration);
            skill_list.add(Poison_Reduction_I);
            skill_list.add(Strength_I);
            skill_list.add(Dire_form);
            skill_list.add(Strength_II);
            skill_list.add(Strength_III);
            skill_list.add(Rending_Slashes);
            skill_list.add(Bleeding);
            skill_list.add(Bloodlust);
            skill_list.add(Bestial_Rage);
            skill_list.add(Faster_Regeneration);
            skill_list.add(Survival_instinct);
            skill_list.add(Armor_I);
            skill_list.add(Armor_II);
            skill_list.add(Poison_Reduction_II);
            skill_list.add(Armor_III);
            skill_list.add(Wither_Reduction);
            skill_list.add(Hyper_Armor);
        }

        public static Skill Wolf_form = new Skill() {
            int timer = 0;
            final ArrayList<AttributeModifier> attributes = new ArrayList<>();
            @Override
            public void onUse(LivingEntity entity) {
                if(cooldown()) {
                    entity.entityDropItem(entity.getHeldItemMainhand());
                    entity.entityDropItem(entity.getHeldItemOffhand());
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.CHEST));
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.HEAD));
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.LEGS));
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.FEET));
                    if(attributes.size() < 2) {
                        attributes.add(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_health_wf", 10, AttributeModifier.Operation.ADDITION));
                        attributes.add(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_wf", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
                    }
                    applyEffects(entity);
                    applyModifiers(entity);
                    ModEventBusEvents.getPlayer().transformation.setState(Transformation.TransformState.Animal);
                    entity.heal(entity.getMaxHealth() - entity.getHealth());
                }
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(1));
                ModEventBusEvents.getPlayer().transformation.setState(Transformation.TransformState.Human);
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.HUNGER,300,2,false,false));
                entity.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST,300,1,false,false));
            }

            @Override
            public boolean cooldown() {
                if(timer % 60 == 0)
                    return true;
                else {
                    timer++;
                    return false;
                }
            }

            @Override
            public int getID() {
                return 0;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                if(entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(attributes.get(0)))
                {
                    entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(attributes.get(0));
                    entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(attributes.get(1));
                }
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(attributes.get(0));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(attributes.get(1));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(false);
                return par;
            }
        };
        public static Skill Regeneration = new Skill() {
            @Override
            public void onUse(LivingEntity entity) {
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.REGENERATION,300, 0,false,false));
            }

            @Override
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 1;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(true);
                par.add(true);
                return par;
            }
        };
        public static Skill Poison_Reduction_I = new Skill() {
            EffectInstance instance;
            @Override
            public void onUse(LivingEntity entity) {
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                EffectInstance effectInstance = entity.getActivePotionEffect(Effects.POISON);
                if(effectInstance != null & instance != null) {
                    if(instance.getAmplifier() - 1 != effectInstance.getAmplifier()) {
                        if (effectInstance.getAmplifier() > 0) {
                            entity.addPotionEffect(new EffectInstance(Effects.POISON, effectInstance.getDuration(), effectInstance.getAmplifier() - 1, effectInstance.isAmbient(), effectInstance.doesShowParticles()));
                        } else
                            entity.removePotionEffect(Effects.POISON);
                    }
                }
                instance = effectInstance;
            }

            @Override
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 2;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };

        public static Skill Strength_I = new Skill() {
            public int cost = 1;
            @Override
            public void onUse(LivingEntity entity) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 3;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 2, AttributeModifier.Operation.ADDITION)))
                    entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 2, AttributeModifier.Operation.ADDITION));

                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 2, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Dire_form = new Skill() {
            Skill parent = Strength_I;
            public int cost = 2;
            int timer = 0;
            @Override
            public void onUse(LivingEntity entity) {
                if(cooldown()) {
                    entity.entityDropItem(entity.getHeldItemMainhand());
                    entity.entityDropItem(entity.getHeldItemOffhand());
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.CHEST));
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.HEAD));
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.LEGS));
                    entity.entityDropItem(entity.getItemStackFromSlot(EquipmentSlotType.FEET));
                    applyEffects(entity);
                    applyModifiers(entity);
                    ModEventBusEvents.getPlayer().transformation.setState(Transformation.TransformState.Beast);
                    timer++;
                }
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_health_df", 15, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_df", 10, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_df", 2, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_df", 0.2, AttributeModifier.Operation.MULTIPLY_BASE));
                entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_kn_res_df", 3, AttributeModifier.Operation.ADDITION));
                ModEventBusEvents.getPlayer().transformation.setState(Transformation.TransformState.Human);
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 300, 2, false, false));
                entity.addPotionEffect(new EffectInstance(Effects.HUNGER, 300,  2, false, false));
            }

            @Override
            public boolean cooldown() {
                if(timer % 60 == 0)
                    return true;
                else {
                    timer++;
                    return false;
                }
            }

            @Override
            public int getID() {
                return 4;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                if(entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_health_df", 15, AttributeModifier.Operation.ADDITION))) {
                    entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_health_df", 15, AttributeModifier.Operation.ADDITION));
                    entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_df", 10, AttributeModifier.Operation.ADDITION));
                    entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_df", 2, AttributeModifier.Operation.ADDITION));
                    entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_df", 0.2, AttributeModifier.Operation.MULTIPLY_BASE));
                    entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_kn_res_df", 3, AttributeModifier.Operation.ADDITION));

                }
                entity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_health_df", 15, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_df", 10, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_df", 2, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_df", 0.2, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_kn_res_df", 3, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(false);
                return par;
            }
        };
        public static Skill Strength_II = new Skill() {
            Skill parent = Dire_form;
            public int cost = 2;
            @Override
            public void onUse(LivingEntity entity) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 5;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 2, AttributeModifier.Operation.ADDITION)))
                    entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 2, AttributeModifier.Operation.ADDITION));

                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 3, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Strength_III = new Skill() {
            Skill parent = Strength_II;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 6;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 3, AttributeModifier.Operation.ADDITION)))
                    entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 3, AttributeModifier.Operation.ADDITION));

                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength", 4, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Rending_Slashes = new Skill() {
            Skill parent = Strength_II;
            public int cost = 3;

            @Override
            public void onDisable(LivingEntity entity) {

            }
            @Override
            public void onUse(LivingEntity target) {
                int x = new Random().nextInt(4);
                ItemStack stack = null;
                switch (x){
                    case 0: stack = target.getItemStackFromSlot(EquipmentSlotType.HEAD); break;
                    case 1: stack = target.getItemStackFromSlot(EquipmentSlotType.CHEST); break;
                    case 2: stack = target.getItemStackFromSlot(EquipmentSlotType.LEGS); break;
                    case 3: stack = target.getItemStackFromSlot(EquipmentSlotType.FEET); break;
                }
                ItemStack finalStack = stack;
                stack.damageItem((int) (stack.getDamage() * Math.random()), target, (p_220045_0_) -> p_220045_0_.sendBreakAnimation(finalStack.getEquipmentSlot()));
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 7;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Bleeding = new Skill() {
            Skill parent = Strength_III;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity target) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 8;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Bloodlust = new Skill() {
            Skill parent = Rending_Slashes;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
                ((PlayerEntity) entity).getFoodStats().addStats(1, 0.3f);

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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 9;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Bestial_Rage = new Skill() {
            Skill parent_1 = Bloodlust;
            Skill parent_2 = Bleeding;
            public int cost = 5;
            int timer = 0;
            @Override
            public void onUse(LivingEntity entity) {
                if(cooldown()) {
                    entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100, 1, false, false));
                    applyEffects(entity);
                    applyModifiers(entity);
                    timer++;
                }
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_atk_spd_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.HUNGER,300,10,false,false));
            }

            @Override
            public boolean cooldown() {
                if(timer % 300 == 0)
                    return true;
                else {
                    timer++;
                    return false;
                }
            }

            @Override
            public int getID() {
                return 10;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_strength_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
                entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_speed_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
                entity.getAttribute(SharedMonsterAttributes.ATTACK_SPEED).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_atk_spd_br", 0.08, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(false);
                return par;
            }
        };

        public static Skill Faster_Regeneration = new Skill() {
            public int cost = 1;
            @Override
            public void onUse(LivingEntity entity) {
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.REGENERATION,300,1,false,false));
            }

            @Override
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 11;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(true);
                par.add(true);
                return par;
            }
        };
        public static Skill Survival_instinct = new Skill() {
            Skill parent = Faster_Regeneration;
            public int cost = 2;
            int timer = 0;
            @Override
            public void onUse(LivingEntity entity) {
                if(cooldown()) {
                    Wolf_form.onUse(entity);
                    applyEffects(entity);
                    applyModifiers(entity);
                    timer++;
                }
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE,100,2,false,false));
            }

            @Override
            public boolean cooldown() {
                if(timer % 300 == 0)
                    return true;
                else {
                    timer++;
                    return false;
                }
            }

            @Override
            public int getID() {
                return 12;
            }

            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Armor_I = new Skill() {
            Skill parent = Survival_instinct;
            public int cost = 2;
            @Override
            public void onUse(LivingEntity entity) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 13;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 2, AttributeModifier.Operation.ADDITION)))
                    entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 2, AttributeModifier.Operation.ADDITION));

                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 2, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Armor_II = new Skill() {
            Skill parent = Armor_I;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 14;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 2, AttributeModifier.Operation.ADDITION)))
                    entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 2, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 4, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Poison_Reduction_II = new Skill() {
            Skill parent = Armor_I;
            public int cost = 3;
            EffectInstance instance;
            @Override
            public void onUse(LivingEntity entity) {
                applyEffects(entity);
                applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                EffectInstance effectInstance = entity.getActivePotionEffect(Effects.POISON);
                if(effectInstance != null & instance != null) {
                    if(instance.getAmplifier() - 2 != effectInstance.getAmplifier()) {
                        if (effectInstance.getAmplifier() > 0) {
                            entity.addPotionEffect(new EffectInstance(Effects.POISON, effectInstance.getDuration(), effectInstance.getAmplifier() - 2, effectInstance.isAmbient(), effectInstance.doesShowParticles()));
                        } else
                            entity.removePotionEffect(Effects.POISON);
                    }
                }
                instance = effectInstance;
            }

            @Override
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 15;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Armor_III = new Skill() {
            Skill parent = Armor_II;
            public int cost = 3;
            @Override
            public void onUse(LivingEntity entity) {
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
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 16;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 4, AttributeModifier.Operation.ADDITION)))
                    entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 4, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t", 8, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Wither_Reduction = new Skill() {
            Skill parent = Poison_Reduction_II;
            public int cost = 3;
            EffectInstance instance;
            @Override
            public void onUse(LivingEntity entity) {
                    applyEffects(entity);
                    applyModifiers(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {

            }

            @Override
            public void applyEffects(LivingEntity entity) {
                EffectInstance effectInstance = entity.getActivePotionEffect(Effects.WITHER);
                if(effectInstance != null & instance != null) {
                    if(instance.getAmplifier() - 1 != effectInstance.getAmplifier()) {
                        if (effectInstance.getAmplifier() > 0) {
                            entity.addPotionEffect(new EffectInstance(Effects.WITHER, effectInstance.getDuration(), effectInstance.getAmplifier() - 1, effectInstance.isAmbient(), effectInstance.doesShowParticles()));
                        } else
                            entity.removePotionEffect(Effects.WITHER);
                    }
                }
                instance = effectInstance;
            }

            @Override
            public boolean cooldown() {
                return false;
            }

            @Override
            public int getID() {
                return 17;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {

            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(true);
                return par;
            }
        };
        public static Skill Hyper_Armor = new Skill() {
            Skill parent_1 = Armor_III;
            Skill parent_2 = Wither_Reduction;
            public int cost = 5;
            int timer = 0;
            @Override
            public void onUse(LivingEntity entity) {
                if(cooldown()) {
                    applyEffects(entity);
                    applyModifiers(entity);
                    timer++;
                }
                else
                    onDisable(entity);
            }

            @Override
            public void onDisable(LivingEntity entity) {
                entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_ha", 15, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t_ha", 5, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public void applyEffects(LivingEntity entity) {
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS,300,2,false,false));
                entity.addPotionEffect(new EffectInstance(Effects.ABSORPTION,300,5,false,false));
                entity.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE,300,3,false,false));
            }

            @Override
            public boolean cooldown() {
                if(timer % 150 == 0)
                    return true;
                else {
                    timer++;
                    return false;
                }
            }

            @Override
            public int getID() {
                return 18;
            }


            @Override
            public void applyModifiers(LivingEntity entity) {
                if (entity.getAttribute(SharedMonsterAttributes.ARMOR).hasModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_ha", 15, AttributeModifier.Operation.ADDITION))){
                    entity.getAttribute(SharedMonsterAttributes.ARMOR).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_ha", 15, AttributeModifier.Operation.ADDITION));
                    entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t_ha", 5, AttributeModifier.Operation.ADDITION));
                }
                entity.getAttribute(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_ha", 15, AttributeModifier.Operation.ADDITION));
                entity.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(SupernaturalWeaponry.Mod_ID + ":werewolf_armor_t_ha", 5, AttributeModifier.Operation.ADDITION));
            }

            @Override
            public ArrayList<Boolean> parameters() {
                ArrayList<Boolean> par = new ArrayList<>();
                par.add(false);
                par.add(false);
                return par;
            }
        };

    }
}
