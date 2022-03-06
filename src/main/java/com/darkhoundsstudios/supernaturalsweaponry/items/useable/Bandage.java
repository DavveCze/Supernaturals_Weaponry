package com.darkhoundsstudios.supernaturalsweaponry.items.useable;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Bandage extends Item {
    public Bandage(Properties properties) {
        super(properties);
    }

    @Override
    //vyvolá se při stisku pravého tlačítka
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        //zjistí zda počet efektů na hráči je více jak 0
        if(playerIn.getActivePotionEffects().toArray().length > 0)
        {
            //projdou se všechny efekty co hráč má na sobě, pokud obsahuje krvácení, tak krvácení se zastaví
            for (EffectInstance effect: playerIn.getActivePotionEffects()) {
                if (effect.getPotion() == ModEffects.BLEEDING.get()){
                    playerIn.removePotionEffect(effect.getPotion());
                    break;
                }
            }
        }
        //přidá hráči náhodný počet životů navíc
        playerIn.heal((float) ((Math.random() * (4.0f - 2.0f)) + 2.0f));
        return ActionResult.resultPass(stack);
    }
}
