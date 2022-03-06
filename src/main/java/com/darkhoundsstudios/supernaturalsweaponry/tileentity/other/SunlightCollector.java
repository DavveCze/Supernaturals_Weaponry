package com.darkhoundsstudios.supernaturalsweaponry.tileentity.other;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.container.ModContainers;
import com.darkhoundsstudios.supernaturalsweaponry.tileentity.ModTileEntities;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SunlightCollector extends LockableLootTileEntity implements ITickableTileEntity, IInventory {
    //proměnné pro generaci a ukládání předmětů
    private final float tot_coll_time = 100;
    private float curr_coll_time;
    private float ticker = 0;
    NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    private final ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public SunlightCollector(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }


    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.handler != null) {
            this.handler.invalidate();
            this.handler = null;
        }
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        items = itemsIn;
    }

    public SunlightCollector()
    {
        this(ModTileEntities.SUNLIGHT_COLLECTOR_TILE.get());
    }

    //ukládá data do souboru světa
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        System.out.println("Written");
        CompoundNBT item1 = new CompoundNBT();
        CompoundNBT item2 = new CompoundNBT();
        items.get(0).write(item1);
        items.get(1).write(item2);
        compound.put("Item1", item1);
        compound.put("Item2", item2);
        System.out.println("1: " + item1 + ", 2: " + item2);
        System.out.println("Compound: " + compound);
        return compound;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + SupernaturalWeaponry.Mod_ID + ".display_case");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return ModContainers.SUNLIGHT_COLLECTOR_CONTAINER.get().create(id,player);
    }

    //čte ze souboru a vkládá informace
    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        System.out.println("Read");
        CompoundNBT item1 = compound.getCompound("item1");
        items.set(0, ItemStack.read(item1));
        CompoundNBT item2 = compound.getCompound("item2");
        items.set(0, ItemStack.read(item2));
        System.out.println("1: " + item1 + ", 2: " + item2);
    }

    //stará se o to zda je vložený předmět správný, a celkově všechno ověřuje a ukládá
    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(2)
        {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
                System.out.println("Marked dirty");
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


    //kotrolní popř. funkční metody, slouží k ticku
    public void fillOrb()
    {
        if(hasFocus())
        {
            this.itemHandler.getStackInSlot(0).shrink(1);
            this.itemHandler.insertItem(1,new ItemStack(RegistryHandler.GLASS_GLOBE_FULL.get(),1),false);
        }
    }

    private boolean hasFocus()
    {
        return this.itemHandler.getStackInSlot(0).getCount() > 0 &&
                this.itemHandler.getStackInSlot(0).getItem() == RegistryHandler.GLASS_GLOBE.get();
    }

    //stará se o generaci orbů
    @Override
    public void tick() {
        ticker++;
        if(ticker >= 10) {
            ticker = 0;
            long light = getWorld().getDayTime();
            if (light > 15000 && !getWorld().isRaining() && !getWorld().isThundering()) {
                curr_coll_time++;
                if (curr_coll_time >= tot_coll_time) {
                    fillOrb();
                    System.out.println("Orb filled");
                    curr_coll_time = 0;
                }
            } else
                curr_coll_time = 0;
        }
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }
}
