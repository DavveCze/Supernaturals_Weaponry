package com.darkhoundsstudios.supernaturalsweaponry.container;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, SupernaturalWeaponry.Mod_ID);

    public static final RegistryObject<ContainerType<SunlightCollectorContainer>> SUNLIGHT_COLLECTOR_CONTAINER
            = CONTAINERS.register("sunlight_collector_container", () ->
            IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.world;
                return new SunlightCollectorContainer(windowId,world,pos,inv,inv.player);
            })));
}
