package com.darkhoundsstudios.supernaturalsweaponry.world.gen;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.blocks.SilverOre;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.NetherBiome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModOreGen {

    @SubscribeEvent
    public static void generateOres(FMLLoadCompleteEvent event)
    {
        for (Biome biome: ForgeRegistries.BIOMES)
        {
            if (biome.getCategory() == Biome.Category.NETHER || biome.getCategory() == Biome.Category.THEEND);
            else {
                genOre(biome, 18, 15, 5, 45, OreFeatureConfig.FillerBlockType.NATURAL_STONE, RegistryHandler.SILVER_ORE.get().getDefaultState(), 5);
                if (biome.getCategory() == Biome.Category.EXTREME_HILLS || biome.getCategory() == Biome.Category.ICY || biome.getCategory() == Biome.Category.PLAINS)
                    genOre(biome, 10, 8, 2, 24, OreFeatureConfig.FillerBlockType.NATURAL_STONE, RegistryHandler.WG_ORE.get().getDefaultState(), 4);
                genOre(biome, 7, 10, 5, 35, OreFeatureConfig.FillerBlockType.NATURAL_STONE, RegistryHandler.AMETHYST_ORE.get().getDefaultState(), 3);
                if (biome.getCategory() == Biome.Category.JUNGLE || biome.getCategory() == Biome.Category.MESA || biome.getCategory() == Biome.Category.FOREST || biome.getCategory() == Biome.Category.TAIGA)
                    genOre(biome, 15, 24, 5, 55, OreFeatureConfig.FillerBlockType.NATURAL_STONE, RegistryHandler.MANA_ORE.get().getDefaultState(), 4);
            }
        }
    }

    private static void genOre(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockState, int size)
    {
        CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockState, size);
        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
    }
}
