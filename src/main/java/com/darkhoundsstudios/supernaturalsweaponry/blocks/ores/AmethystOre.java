package com.darkhoundsstudios.supernaturalsweaponry.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class AmethystOre extends OreBlock {
    public AmethystOre() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(4f,12.5f)
                .sound(SoundType.GLASS)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
