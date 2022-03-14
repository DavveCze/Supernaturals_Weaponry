package com.darkhoundsstudios.supernaturalsweaponry.tools;

import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.ModWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import org.jetbrains.annotations.NotNull;

public class ModPickaxe extends PickaxeItem {
    private final Properties prop;
    private final float baseAttackDamage;

    public ModPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttackDamage = tier.getAttackDamage() + attackDamageIn + 1;
        prop = builder;
    }

    @Override
    public boolean hitEntity(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> p_220045_0_.sendBreakAnimation(attacker.getActiveHand()));

        if (this.getTier() instanceof ModItemTier) {
            ModWeapon x = new ModWeapon(getTier(),(int)baseAttackDamage, attackSpeed,prop);
            switch ((ModItemTier)this.getTier()) {
                case SILVER:
                    x.performSpecial_Silver(stack,target,attacker,baseAttackDamage);
                    break;
                case WHITE_GOLD:
                    x.performSpecial_WG(stack,target,attacker,baseAttackDamage);
                    break;
            }
        }

        return true;
    }
}
