package com.darkhoundsstudios.supernaturalsweaponry.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Two_Handed extends ModWeapon{
    //vytváří koncept obouručních zbraní
    public Two_Handed(IItemTier tier, int damage, float atc_spd, Properties properties) {
        super(tier, damage, atc_spd, properties);
    }

    //zjišťuje zda v inventáři(v ruce, offhandu) se nachází správný předmět, pokud ano tak nasledují kontrolní metody
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!PlayerCreative((PlayerEntity) entityIn))
            if (isOffHandItem((PlayerEntity) entityIn) && isSelected) {
                if (getHandItemPl((PlayerEntity) entityIn, Hand.OFF_HAND) != stack) {
                    findSlot((PlayerEntity) entityIn, Hand.OFF_HAND);
                }
            }
    }

    //vrátí držený předmět v ruce
    private ItemStack getHandItemPl(PlayerEntity player, Hand hand)
    {
        return player.getHeldItem(hand);
    }

    //najde první volný slot v inventáři, pokud se nenachází tak ho vyhodí
    private void findSlot(PlayerEntity player, Hand hand) {
        ItemStack stack = getHandItemPl(player, hand);
        if (stack != ItemStack.EMPTY) {
            if (player.inventory.getFirstEmptyStack() != -1) {
                player.inventory.deleteStack(stack);
                player.replaceItemInInventory(player.inventory.getFirstEmptyStack(),stack);
            }
            else
                dropItem(player, stack);
        }
    }

    //vyhazuje předmět z hráčova inventáře
    private void dropItem(PlayerEntity player, ItemStack stack)
    {
        player.inventory.deleteStack(stack);
        player.dropItem(stack,true);
    }

    //zjišťuje zda offhand je volný či ne
    private boolean isOffHandItem(PlayerEntity player)
    {
        if (getHandItemPl(player,Hand.OFF_HAND) != ItemStack.EMPTY)
            return true;
        else
            return false;
    }

    //zjišťuje zda hráč je v creativu
    private boolean PlayerCreative(PlayerEntity player)
    {
        return player.isCreative();
    }
}
