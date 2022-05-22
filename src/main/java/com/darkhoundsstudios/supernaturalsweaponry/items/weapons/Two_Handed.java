package com.darkhoundsstudios.supernaturalsweaponry.items.weapons;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Two_Handed extends ModWeapon{
    static AttributeModifier speed_modifier = null;
    public Two_Handed(IItemTier tier, int damage, float atc_spd,double speed_decr_val, Properties properties) {
        super(tier, damage, atc_spd, properties);
        if (speed_modifier == null)
            speed_modifier = new AttributeModifier(UUID.randomUUID(), SupernaturalWeaponry.Mod_ID + ":th_weapon", speed_decr_val, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        if (!PlayerCreative((PlayerEntity) entityIn)) {
            if (isOffHandItem((PlayerEntity) entityIn) && isSelected) {
                if (getHandItemPl((PlayerEntity) entityIn) != stack) {
                    findSlot((PlayerEntity) entityIn);
                }
            }
            if(isSelected)
                ((PlayerEntity) entityIn).getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(speed_modifier);
            else
                ((PlayerEntity) entityIn).getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(speed_modifier);
        }
    }

    private ItemStack getHandItemPl(PlayerEntity player)
    {
        return player.getHeldItem(Hand.OFF_HAND);
    }

    private void findSlot(PlayerEntity player) {
        ItemStack stack = getHandItemPl(player);
        if (stack != ItemStack.EMPTY) {
            if (player.inventory.getFirstEmptyStack() != -1) {
                player.inventory.deleteStack(stack);
                player.replaceItemInInventory(player.inventory.getFirstEmptyStack(),stack);
            }
            else
                dropItem(player, stack);
        }
    }

    private void dropItem(PlayerEntity player, ItemStack stack)
    {
        player.inventory.deleteStack(stack);
        player.dropItem(stack,true);
    }

    private boolean isOffHandItem(PlayerEntity player)
    {
        return getHandItemPl(player) != ItemStack.EMPTY;
    }

    private boolean PlayerCreative(PlayerEntity player)
    {
        return player.isCreative();
    }
}
