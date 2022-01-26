package com.darkhoundsstudios.supernaturalsweaponry.tools;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;

public class ModAxe extends AxeItem {
    public float realAttackDamage;
    public float baseAttackDamage;

    public ModAxe(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttackDamage = tier.getAttackDamage() + attackDamageIn + 1;
    }
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(attacker.getActiveHand());
        });

        if (this.getTier() instanceof ModItemTier) {
            switch ((ModItemTier)this.getTier()) {
                case SILVER: {
                    if (target.isEntityUndead()) {
                        realAttackDamage = baseAttackDamage * 2;
                        if (attacker instanceof PlayerEntity) {
                            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), this.realAttackDamage);
                        } else {
                            target.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.realAttackDamage);
                        }
                        System.out.println("Damage dealt: " + realAttackDamage);
                        realAttackDamage = baseAttackDamage;
                    } else
                        System.out.println("Damage dealt: " + realAttackDamage);
                    System.out.println("Target's health: " + target.getHealth());
                }
                break;
                case WHITE_GOLD:
                    if (target.hasItemInSlot(EquipmentSlotType.CHEST) || target.hasItemInSlot(EquipmentSlotType.HEAD) || target.hasItemInSlot(EquipmentSlotType.LEGS) || target.hasItemInSlot(EquipmentSlotType.FEET)) {
                        System.out.println("cover %: " + target.getArmorCoverPercentage());
                        System.out.println("total armor: " + target.getTotalArmorValue());
                        System.out.println("equip: " + target.getEquipmentAndArmor());
                        System.out.println("base dmg: " + baseAttackDamage);
                        if (target.getArmorInventoryList().toString().contains("diamond")) {
                            float x = 0;
                            if (target.getItemStackFromSlot(EquipmentSlotType.HEAD).toString().contains("diamond"))
                                x += 2;
                            if (target.getItemStackFromSlot(EquipmentSlotType.CHEST).toString().contains("diamond"))
                                x += 2;
                            if (target.getItemStackFromSlot(EquipmentSlotType.LEGS).toString().contains("diamond"))
                                x += 2;
                            if (target.getItemStackFromSlot(EquipmentSlotType.FEET).toString().contains("diamond"))
                                x += 2;
                            System.out.println("toughness: " + x);
                            realAttackDamage = baseAttackDamage + (baseAttackDamage - baseAttackDamage * (1 - (Math.min(20, Math.max(((target.getTotalArmorValue() - x) / 5), (target.getTotalArmorValue() - x) - ((4 * baseAttackDamage) / (x + 8))))) / 25));
                        } else
                            realAttackDamage = baseAttackDamage + (baseAttackDamage - baseAttackDamage * (1 - (Math.min(20, Math.max((target.getTotalArmorValue() / 5f), target.getTotalArmorValue() - ((4 * baseAttackDamage) / 8)))) / 25));
                        if (attacker instanceof PlayerEntity) {
                            target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), this.realAttackDamage);
                        } else {
                            target.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.realAttackDamage);
                        }
                        System.out.println("Damage dealt: " + realAttackDamage);
                        realAttackDamage = baseAttackDamage;
                    } else
                        System.out.println("Damage dealt: " + realAttackDamage);
                    System.out.println("Target's health: " + target.getHealth());
                    System.out.println("After: " + target.getHealth());
                    break;
            }
        }

        return true;
    }
}
