package com.darkhoundsstudios.supernaturalsweaponry;

import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.java.games.input.Component;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(SupernaturalWeaponry.Mod_ID)
public class SupernaturalWeaponry
{
//gradlew genIntellijRuns na generování spouštěcího souboru
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String Mod_ID = "snweaponry";

    public SupernaturalWeaponry() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    public static final ItemGroup TAB = new ItemGroup("SN_tab") {
        @Override
        public ItemStack createIcon() {
            return RegistryHandler.SILVER_INGOT.get().getDefaultInstance();
        }
    };

    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {
        if (evt.getName().toString().equals("minecraft:entities/wolf")) {
            evt.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupernaturalWeaponry.Mod_ID, "inject/wolf"))).build());
        }
        if (evt.getName().toString().equals("minecraft:blocks/grass")) {
            evt.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupernaturalWeaponry.Mod_ID, "inject/grass"))).build());
        }
        if (evt.getName().toString().equals("minecraft:entities/dolphin")) {
            evt.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupernaturalWeaponry.Mod_ID, "inject/dolphin"))).build());
        }
        if (evt.getName().toString().equals("minecraft:entities/polar_bear")) {
            evt.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupernaturalWeaponry.Mod_ID, "inject/polar_bear"))).build());
        }
        if (evt.getName().toString().equals("minecraft:entities/ocelot")) {
            evt.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(SupernaturalWeaponry.Mod_ID, "inject/ocelot"))).build());
        }
    }
}
