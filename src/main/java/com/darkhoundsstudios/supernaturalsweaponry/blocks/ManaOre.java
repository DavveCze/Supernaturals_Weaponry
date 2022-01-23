package com.darkhoundsstudios.supernaturalsweaponry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ManaOre extends OreBlock {
    public ManaOre() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(4f,500f)
                .sound(SoundType.GLASS)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
