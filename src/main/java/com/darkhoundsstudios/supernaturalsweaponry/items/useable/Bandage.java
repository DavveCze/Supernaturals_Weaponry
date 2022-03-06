package com.darkhoundsstudios.supernaturalsweaponry.items.useable;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Bandage extends Item {
    public Bandage(Properties properties) {
        super(properties);
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
                    break;
                } else {
                    System.out.println("Bleeding is: False");
                }
            }
        }
        playerIn.heal((float) ((Math.random() * (4.0f - 2.0f)) + 2.0f));
        return ActionResult.resultPass(stack);
    }
}
