package com.darkhoundsstudios.supernaturalsweaponry.tileentity.other;

import com.darkhoundsstudios.supernaturalsweaponry.tileentity.ModTileEntities;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SunlightCollector extends TileEntity implements ITickableTileEntity {
    private final static float tot_coll_time = 1000;
    private float curr_coll_time;

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public SunlightCollector(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public SunlightCollector()
    {
        this(ModTileEntities.SUNLIGHT_COLLECTOR_TILE.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        itemHandler.deserializeNBT(compound.getCompound("inv"));
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        super.read(compound);
    }

    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(2)
        {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                switch (slot){
                    case 0:
                        return stack.getItem() == RegistryHandler.GLASS_GLOBE.get();
                    case 1:
                        return stack.getItem() == RegistryHandler.GLASS_GLOBE_FULL.get();
                    default: return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot,stack))
                    return stack;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return handler.cast();
        return super.getCapability(cap, side);
    }


    public void fillOrb()
    {
        if(hasFocus())
        {
            this.itemHandler.getStackInSlot(0).shrink(1);
            this.itemHandler.insertItem(1,new ItemStack(RegistryHandler.GLASS_GLOBE_FULL.get()),false);
        }
    }

    private boolean hasFocus()
    {
        return this.itemHandler.getStackInSlot(0).getCount() > 0 &&
                this.itemHandler.getStackInSlot(0).getItem() == RegistryHandler.GLASS_GLOBE.get();
    }

    @Override
    public void tick() {
        assert this.world != null;
        if (!this.world.isNightTime())
        {
            curr_coll_time++;
            if(curr_coll_time >= tot_coll_time)
            {
                fillOrb();
                System.out.println("Orb filled");
                curr_coll_time = 0;
            }
        }
    }
}
