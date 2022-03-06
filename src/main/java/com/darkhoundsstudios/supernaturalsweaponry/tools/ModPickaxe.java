package com.darkhoundsstudios.supernaturalsweaponry.tools;

import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.ModWeapon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

//rozšiřuje krumpáče, upravuje jejich vlastnosti v závislosti na jejich materiálu
public class ModPickaxe extends PickaxeItem {
    public float realAttackDamage;
    public float baseAttackDamage;

    private final Properties properties;

    public ModPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttackDamage = tier.getAttackDamage() + attackDamageIn + 1;
        properties = builder;
    }
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(attacker.getActiveHand());
        });

        if (this.getTier() instanceof ModItemTier) {
            switch ((ModItemTier)this.getTier()) {
                case SILVER:
                    new ModWeapon(getTier(), (int) baseAttackDamage,this.attackSpeed, properties).performSpecial_Silver(stack,target,attacker,baseAttackDamage);
                    break;
                case WHITE_GOLD:
                    new ModWeapon(getTier(), (int) baseAttackDamage,this.attackSpeed, properties).performSpecial_WG(stack,target,attacker,baseAttackDamage);
                    break;
            }
        }

        return true;
    }
}
