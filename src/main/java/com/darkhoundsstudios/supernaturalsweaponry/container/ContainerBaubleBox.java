package com.darkhoundsstudios.supernaturalsweaponry.container;

import com.darkhoundsstudios.supernaturalsweaponry.items.curios.ItemBaubleBox;
import com.darkhoundsstudios.supernaturalsweaponry.items.curios.SlotLocked;
import com.darkhoundsstudios.supernaturalsweaponry.util.EquipmentHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;

public class ContainerBaubleBox extends Container {

    public ContainerBaubleBox(int windowId, PlayerInventory inv, ItemStack box) {
        super(ModContainers.BAUBLE_BOX_CONTAINER.get(),windowId);
        int i;
        int j;

        this.box = box;
        IInventory baubleBoxInv;
        if (!inv.player.world.isRemote) {
            baubleBoxInv = ItemBaubleBox.getInventory(box);
        } else {
            baubleBoxInv = new Inventory(ItemBaubleBox.SIZE);
        }

        for (i = 0; i < 4; ++i) {
            for (j = 0; j < 6; ++j) {
                int k = j + i * 6;
                addSlot(new Slot(baubleBoxInv, k, 62 + j * 18, 8 + i * 18) {
                    @Override
                    public boolean isItemValid(@Nonnull ItemStack stack) {
                        return EquipmentHandler.instance.isAccessory(stack);
                    }
                });
            }
        }

        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            if (inv.getStackInSlot(i) == box) {
                addSlot(new SlotLocked(inv, i, 8 + i * 18, 142));
            } else {
                addSlot(new Slot(inv, i, 8 + i * 18, 142));
            }
        }

    }

    public static ContainerBaubleBox fromNetwork(int windowId, PlayerInventory inv, PacketBuffer buf) {
        Hand hand = buf.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
        return new ContainerBaubleBox(windowId, inv, inv.player.getHeldItem(hand));
    }

    private final ItemStack box;
    @Override
    public boolean canInteractWith(PlayerEntity player) {
        ItemStack main = player.getHeldItemMainhand();
        ItemStack off = player.getHeldItemOffhand();
        return !main.isEmpty() && main == box || !off.isEmpty() && off == box;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int boxStart = 0;
            int boxEnd = boxStart + 24;
            int invEnd = boxEnd + 36;

            if (slotIndex < boxEnd) {
                if (!mergeItemStack(itemstack1, boxEnd, invEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!itemstack1.isEmpty() && EquipmentHandler.instance.isAccessory(itemstack1) && !mergeItemStack(itemstack1, boxStart, boxEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
