package com.darkhoundsstudios.supernaturalsweaponry.items.weapons.bombs;

import com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles.BombEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class Granade extends Item {
    //drží vlastnosti které bomba má(určuje se její typ)
    private final boolean PoisonActive, FireActive;
    private final float explosion_rad;
    public Granade(Properties properties, boolean _PoisonActive, boolean _FireActive, float _explosion_rad) {
        super(properties);
        PoisonActive = _PoisonActive;
        FireActive = _FireActive;
        explosion_rad = _explosion_rad;
    }

    //vyvolá se při stisku pravého tlačíka
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote())
        {
            //vytvoří se nová entity s určitými vlastnostmi
            BombEntity bomb = new BombEntity(playerIn,worldIn, PoisonActive, FireActive, explosion_rad);
            bomb.setItem(stack);
            bomb.shoot(playerIn,playerIn.rotationPitch,playerIn.rotationYaw,0.0f,0.8f,5f);
            //přidá se tato entita do světa(vystřelí/hodí)
            worldIn.addEntity(bomb);
        }

        //pokud hráč není v creativu tak se stack zmenší o jeden
        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }
        return ActionResult.resultSuccess(stack);
    }
}
