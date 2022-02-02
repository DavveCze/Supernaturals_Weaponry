package com.darkhoundsstudios.supernaturalsweaponry.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Two_Handed extends ModWeapon{
    public Two_Handed(IItemTier tier, int damage, float atc_spd, Properties properties) {
        super(tier, damage, atc_spd, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(itemSlot == EquipmentSlotType.MAINHAND.getSlotIndex() && isOffHandItem((PlayerEntity) entityIn,EquipmentSlotType.OFFHAND))
        {
            dropItem((PlayerEntity) entityIn, EquipmentSlotType.OFFHAND);
        }
        else if(itemSlot == EquipmentSlotType.OFFHAND.getSlotIndex() && isOffHandItem((PlayerEntity) entityIn,EquipmentSlotType.MAINHAND))
        {
            dropItem((PlayerEntity) entityIn,EquipmentSlotType.MAINHAND);
        }
    }

    private ItemStack getOffHandItemPl(PlayerEntity player, EquipmentSlotType slot)
    {
        return player.getItemStackFromSlot(slot);
    }

    private void dropItem(PlayerEntity player, EquipmentSlotType slot)
    {
        player.entityDropItem(getOffHandItemPl(player,slot),slot.getSlotIndex());
    }

    private boolean isOffHandItem(PlayerEntity player, EquipmentSlotType slot)
    {
        if (player.getItemStackFromSlot(slot) != ItemStack.EMPTY)
            return true;
        else
            return false;
    }
}
