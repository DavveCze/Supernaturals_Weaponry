package com.darkhoundsstudios.supernaturalsweaponry.items.curios;

import com.darkhoundsstudios.supernaturalsweaponry.container.ContainerBaubleBox;
import com.darkhoundsstudios.supernaturalsweaponry.util.EquipmentHandler;
import com.darkhoundsstudios.supernaturalsweaponry.util.ItemBackedInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemBaubleBox extends Item {
    public static final int SIZE = 24;

    public ItemBaubleBox(Properties props) {
        super(props);
    }

    public static Inventory getInventory(ItemStack stack) {
        return new ItemBackedInventory(stack, SIZE) {
            @Override
            public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
                return EquipmentHandler.instance.isAccessory(stack);
            }
        };
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT oldCapNbt) {
        return new InvProvider(stack);
    }

    private static class InvProvider implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> opt;

        public InvProvider(ItemStack stack) {
            opt = LazyOptional.of(() -> new InvWrapper(getInventory(stack)));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(capability, opt);
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            INamedContainerProvider container = new SimpleNamedContainerProvider((w, p, pl) -> new ContainerBaubleBox(w, p, stack), stack.getDisplayName());
            NetworkHooks.openGui((ServerPlayerEntity) player, container, b -> {
                b.writeBoolean(hand == Hand.MAIN_HAND);
            });
        }
        return ActionResult.resultSuccess(player.getHeldItem(hand));
    }
}
