package com.darkhoundsstudios.supernaturalsweaponry.items.weapons.daggers;

import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import java.util.function.Supplier;

public class DaggerWeapon extends SwordItem {
    private Supplier<Effect> effect;
    public float realAttackDamage;
    public float baseAttackDamage;

    public DaggerWeapon(IItemTier tier, int damage, float atc_spd, Properties properties, Supplier<Effect> _effect) {
        super(tier, damage, atc_spd, properties);
        effect = _effect;
        baseAttackDamage = tier.getAttackDamage() + damage + 1;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(attacker.getActiveHand());
        });

        if (!attacker.getEntityWorld().isRemote) {
            if (Math.random() * 100 <= 20) {
                target.addPotionEffect(new EffectInstance(effect.get(), 900, 0, false, true));
            }
        }

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
