package com.darkhoundsstudios.supernaturalsweaponry.items.weapons;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModDamageSources;
import com.darkhoundsstudios.supernaturalsweaponry.entities.ModCreatureAttribute;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import org.jetbrains.annotations.NotNull;

//stará se o všechny potřebné věci co se týče zbraní - počítání dmg, kontrola materiálů, apod.
public class ModWeapon extends SwordItem {
    public float realAttackDamage;
    public float baseAttackDamage;

    public ModWeapon(IItemTier tier, int damage, float atc_spd, Properties properties) {
        super(tier, damage, atc_spd, properties);
        baseAttackDamage = tier.getAttackDamage() + damage + 1;
    }

    //vyvolá se v případě zásahu entity
    @Override
    public boolean hitEntity(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> p_220045_0_.sendBreakAnimation(attacker.getActiveHand()));

        if (this.getTier() instanceof ModItemTier) {
            switch ((ModItemTier)this.getTier()) {
                case SILVER: {
                    performSpecial_Silver(stack,target,attacker, baseAttackDamage);
                }
                break;
                case WHITE_GOLD:
                    performSpecial_WG(stack,target,attacker, baseAttackDamage);
                    break;
            }
        }

        return true;
    }

    //vyvolá a způsobí dmg, který "prochází brněním"
    public static void performSpecial_WG(ItemStack stack, LivingEntity target, LivingEntity attacker, float Damage)
    {
        System.out.println("base dmg: " + Damage);
        float vanillaDMG, realAttackDamage = 0;
        if (target.getArmorInventoryList().toString().contains("diamond")) {
            float x = (float) target.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getValue();
            vanillaDMG = (Damage - Damage * (1 - (Math.min(20, Math.max(((target.getTotalArmorValue() - x) / 5), (target.getTotalArmorValue() - x) - ((4 * Damage) / (x + 8))))) / 25));
            realAttackDamage = Damage + vanillaDMG + vanillaDMG * 0.49f + 1;
        } else {
            vanillaDMG = ((Damage - Damage * (1 - (Math.min(20, Math.max((target.getTotalArmorValue() / 5f), target.getTotalArmorValue() - ((4 * Damage) / 8)))) / 25)));
            realAttackDamage = Damage + vanillaDMG + vanillaDMG * 0.49f;
        }
        if (attacker instanceof PlayerEntity) {
            target.attackEntityFrom(ModDamageSources.PURITY, realAttackDamage);
        } else {
            target.attackEntityFrom(ModDamageSources.PURITY, realAttackDamage);
        }
        System.out.println("Damage dealt: " + realAttackDamage);
        realAttackDamage = Damage;
        System.out.println("HP: " + target.getHealth());
    }

    //působí zvýšené poškození pokud cíl je nemrtvý či nadpřirozený
    public static void performSpecial_Silver(ItemStack stack, LivingEntity target, LivingEntity attacker, float Damage)
    {
        if (target.isEntityUndead() || target.getCreatureAttribute().equals(ModCreatureAttribute.Supernatural)) {
            float realAttackDamage = Damage * 2;
            if (attacker instanceof PlayerEntity) {
                target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), realAttackDamage);
            } else {
                target.attackEntityFrom(DamageSource.causeMobDamage(attacker), realAttackDamage);
            }
        }
        System.out.println("Target's health: " + target.getHealth());
    }


}
