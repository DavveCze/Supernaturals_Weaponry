package com.darkhoundsstudios.supernaturalsweaponry.items.weapons.daggers;

import com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles.DaggerEntity;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TDagger extends Item {
    private final IItemTier material;
    public TDagger(Properties properties, IItemTier _material) {
        super(properties);
        material =_material;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote())
        {
            DaggerEntity dagger = new DaggerEntity(playerIn,worldIn);
            dagger.setItem(stack);
            dagger.setMaterial(material);
            dagger.shoot(playerIn,playerIn.rotationPitch,playerIn.rotationYaw,0.0f,1.5f,2.5f);
            worldIn.addEntity(dagger);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }
        return ActionResult.resultSuccess(stack);
    }
}
