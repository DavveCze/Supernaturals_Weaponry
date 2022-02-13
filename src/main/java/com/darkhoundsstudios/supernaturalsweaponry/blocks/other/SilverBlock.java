package com.darkhoundsstudios.supernaturalsweaponry.blocks.other;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SilverBlock extends Block {
    public SilverBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5f,25f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
