package com.darkhoundsstudios.supernaturalsweaponry.items.useable;

import com.darkhoundsstudios.supernaturalsweaponry.effects.BleedingEffect;
import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

public class Bandage extends Item {
    public Bandage(Properties properties) {
        super(properties.maxDamage(2).defaultMaxDamage(2));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(playerIn.getActivePotionEffects().toArray().length > 0)
        {
            for (int i = 0; i < playerIn.getActivePotionEffects().toArray().length; i++) {
                if (playerIn.getActivePotionEffects().toArray()[i].toString().contains("Bleeding")) {
                    System.out.println("Bleeding is: True");
                    playerIn.removePotionEffect(ModEffects.BLEEDING.get());
                    stack.damageItem(1,playerIn,(p_220045_0_) -> {
                        p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                    });
                    break;
                } else {
                    System.out.println("Bleeding is: False");
                }
            }
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }
}
