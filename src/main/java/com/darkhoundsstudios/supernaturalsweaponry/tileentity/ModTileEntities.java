package com.darkhoundsstudios.supernaturalsweaponry.tileentity;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.tileentity.other.SunlightCollector;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    //registuje všechny Tile entity
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SupernaturalWeaponry.Mod_ID);

    public static final RegistryObject<TileEntityType<SunlightCollector>> SUNLIGHT_COLLECTOR_TILE =
            TILE_ENTITIES.register("sunlight_collector_tile",() ->
                    TileEntityType.Builder.create(SunlightCollector::new, RegistryHandler.SUNLIGHT_COLLECTOR.get()).build(null));
}
