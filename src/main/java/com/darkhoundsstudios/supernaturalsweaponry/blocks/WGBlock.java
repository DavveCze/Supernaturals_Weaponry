package com.darkhoundsstudios.supernaturalsweaponry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.ToolType;

public class WGBlock extends RotatedPillarBlock {

    public WGBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(4.5f,22.5f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }

    void getAxis()
    {
        
    }

}
