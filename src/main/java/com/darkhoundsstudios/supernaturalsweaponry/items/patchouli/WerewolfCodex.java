package com.darkhoundsstudios.supernaturalsweaponry.items.patchouli;

import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;

public class WerewolfCodex extends Item {
    public WerewolfCodex(Properties properties) {
        super(properties);
    }

    public static boolean isOpen() {
        return Registry.ITEM.getKey(RegistryHandler.CODEX_LYCANIS.get()).equals(PatchouliAPI.instance.getOpenBookGui());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (playerIn instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) playerIn;
            PatchouliAPI.instance.openBookGUI((ServerPlayerEntity) playerIn, Registry.ITEM.getKey(this));
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
