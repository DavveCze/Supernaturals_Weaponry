package com.darkhoundsstudios.supernaturalsweaponry.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class WGOre extends OreBlock {
    public WGOre() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(4.5f, 22.5f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }
}

