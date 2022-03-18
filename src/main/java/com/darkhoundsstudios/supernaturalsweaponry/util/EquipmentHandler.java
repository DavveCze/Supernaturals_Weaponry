package com.darkhoundsstudios.supernaturalsweaponry.util;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.integration.curios.CuriosIntegration;
import com.darkhoundsstudios.supernaturalsweaponry.items.curios.ItemBauble;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class EquipmentHandler {
    public static EquipmentHandler instance;

    public static void init() {
        if (SupernaturalWeaponry.curiosLoaded) {
            instance = new CuriosIntegration();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(CuriosIntegration::sendImc);
            MinecraftForge.EVENT_BUS.addListener(CuriosIntegration::keepCurioDrops);
        } else {
            InventoryEquipmentHandler handler = new InventoryEquipmentHandler();
            instance = handler;
            MinecraftForge.EVENT_BUS.addListener(handler::onPlayerTick);
        }
    }

    public boolean isAccessory(ItemStack stack) {
        return stack.getItem() instanceof ItemBauble;
    }


    public static LazyOptional<IItemHandlerModifiable> getAllWorn(LivingEntity living) {
        return instance.getAllWornItems(living);
    }

    public static ItemStack findOrEmpty(Item item, LivingEntity living) {
        return instance.findItem(item, living);
    }

    public static ItemStack findOrEmpty(Predicate<ItemStack> pred, LivingEntity living) {
        return instance.findItem(pred, living);
    }

    public static ICapabilityProvider initBaubleCap(ItemStack stack) {
        if (instance != null) // Happens to be called in ModItems class init, which is too early to know about which handler to use
        {
            return instance.initCap(stack);
        }
        return null;
    }

    protected abstract LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living);

    protected abstract ItemStack findItem(Item item, LivingEntity living);

    protected abstract ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living);

    protected abstract ICapabilityProvider initCap(ItemStack stack);


    // Fallback equipment handler for curios-less (or baubles-less) installs.
    static class InventoryEquipmentHandler extends EquipmentHandler {
        private final Map<PlayerEntity, ItemStack[]> map = new WeakHashMap<>();

        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase != TickEvent.Phase.START || event.player.world.isRemote) {
                return;
            }
            PlayerEntity player = event.player;
            player.world.getProfiler().startSection("botania:tick_wearables");

            ItemStack[] oldStacks = map.computeIfAbsent(player, p -> {
                ItemStack[] array = new ItemStack[9];
                Arrays.fill(array, ItemStack.EMPTY);
                return array;
            });

            PlayerInventory inv = player.inventory;
            for (int i = 0; i < 9; i++) {
                ItemStack old = oldStacks[i];
                ItemStack current = inv.getStackInSlot(i);

                if (!ItemStack.areItemStacksEqual(old, current)) {
                    if (old.getItem() instanceof ItemBauble) {
                        player.getAttributes().removeAttributeModifiers(((ItemBauble) old.getItem()).getEquippedAttributeModifiers(old));
                        ((ItemBauble) old.getItem()).onUnequipped(old, player);
                    }
                    if (canEquip(current, player)) {
                        player.getAttributes().applyAttributeModifiers(((ItemBauble) current.getItem()).getEquippedAttributeModifiers(current));
                        ((ItemBauble) current.getItem()).onEquipped(current, player);
                    }
                    oldStacks[i] = current.copy(); // shift-clicking mutates the stack we stored,
                    // making it empty and failing the equality check - let's avoid that
                }

                if (canEquip(current, player)) {
                    ((ItemBauble) current.getItem()).onWornTick(current, player);
                }
            }
            player.world.getProfiler().endSection();
        }

        @Override
        protected LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living) {
            return instance.getAllWornItems(living);
        }

        @Override
        protected ItemStack findItem(Item item, LivingEntity living) {
            if (living instanceof PlayerEntity) {
                PlayerInventory inv = ((PlayerEntity) living).inventory;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack.getItem() == item && canEquip(stack, living)) {
                        return stack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }

        @Override
        protected ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
            if (living instanceof PlayerEntity) {
                PlayerInventory inv = ((PlayerEntity) living).inventory;
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (pred.test(stack) && canEquip(stack, living)) {
                        return stack;
                    }
                }
            }
            return ItemStack.EMPTY;
        }

        @Override
        protected ICapabilityProvider initCap(ItemStack stack) {
            return null;
        }

        private static boolean canEquip(ItemStack stack, LivingEntity player) {
            return stack.getItem() instanceof ItemBauble && ((ItemBauble) stack.getItem()).canEquip(stack, player);
        }
    }
}
