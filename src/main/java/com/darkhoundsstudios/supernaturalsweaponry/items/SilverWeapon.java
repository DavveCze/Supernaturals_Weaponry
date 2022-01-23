package com.darkhoundsstudios.supernaturalsweaponry.items;

import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;

public class SilverWeapon extends SwordItem{

    public float realAttackDamage;
    public SilverWeapon(int damage, float atk_spd, Properties properties) {
        super(ModItemTier.SILVER, damage, atk_spd, properties);
        realAttackDamage = ModItemTier.SILVER.getAttackDamage() + damage + 1;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hitEntity(stack, target, attacker);
        if (target.isEntityUndead()) {
            realAttackDamage = realAttackDamage * 2;
            if (attacker instanceof PlayerEntity) {
                target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), this.realAttackDamage);
            }
            else {
                target.attackEntityFrom(DamageSource.causeMobDamage(attacker), this.realAttackDamage);
            }
            System.out.println("Damage dealt: " + realAttackDamage);
            realAttackDamage = realAttackDamage / 2;
        }
        else
            System.out.println("Damage dealt: " + realAttackDamage);
        System.out.println("Target's health: " + target.getHealth());
        return true;
    }
}
