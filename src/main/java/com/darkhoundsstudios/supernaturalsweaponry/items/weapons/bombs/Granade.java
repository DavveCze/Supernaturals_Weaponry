package com.darkhoundsstudios.supernaturalsweaponry.items.weapons.bombs;

import com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles.BombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class Granade extends Item {
    private final boolean PoisonActive, FireActive;
    private final float explosion_rad;
    public Granade(Properties properties, boolean _PoisonActive, boolean _FireActive, float _explosion_rad) {
        super(properties);
        PoisonActive = _PoisonActive;
        FireActive = _FireActive;
        explosion_rad = _explosion_rad;
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote())
        {
            BombEntity bomb = new BombEntity(playerIn,worldIn, PoisonActive, FireActive, explosion_rad);
            bomb.setItem(stack);
            bomb.shoot(playerIn,playerIn.rotationPitch,playerIn.rotationYaw,0.0f,0.8f,5f);
            worldIn.addEntity(bomb);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }
        return ActionResult.resultSuccess(stack);
    }
}
