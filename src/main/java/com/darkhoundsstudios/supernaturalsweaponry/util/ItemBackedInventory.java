package com.darkhoundsstudios.supernaturalsweaponry.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class ItemBackedInventory extends Inventory {
    private static final String TAG_ITEMS = "Items";
    private final ItemStack stack;

    public ItemBackedInventory(ItemStack stack, int expectedSize) {
        super(expectedSize);
        this.stack = stack;

        ListNBT lst = stack.getTag().getList(TAG_ITEMS, 10);
        int i = 0;
        for (; i < expectedSize && i < lst.size(); i++) {
            setInventorySlotContents(i, ItemStack.read(lst.getCompound(i)));
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return !stack.isEmpty();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        ListNBT list = new ListNBT();
        for (int i = 0; i < getSizeInventory(); i++) {
            list.add(getStackInSlot(i).write(new CompoundNBT()));
        }
        stack.getTag().getList(TAG_ITEMS, 10).set(10,list);
    }
}
