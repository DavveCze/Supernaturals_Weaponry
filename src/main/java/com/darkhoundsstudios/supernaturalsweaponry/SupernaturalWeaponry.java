package com.darkhoundsstudios.supernaturalsweaponry;

import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.ChildrenBlock;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.MoonPhaseTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.ParentDoneTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.TransformIntoTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.container.ModContainers;
import com.darkhoundsstudios.supernaturalsweaponry.entities.ModEntities;
import com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents;
import com.darkhoundsstudios.supernaturalsweaponry.integration.curios.CuriosIntegration;
import com.darkhoundsstudios.supernaturalsweaponry.screen.SunlightCollectorScreen;
import com.darkhoundsstudios.supernaturalsweaponry.util.EquipmentHandler;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosAPI;

import java.util.function.Supplier;


@Mod(SupernaturalWeaponry.Mod_ID)
public class SupernaturalWeaponry
{
//gradlew genIntellijRuns na generování spouštěcího souboru
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String Mod_ID = "snweaponry";
    public static boolean curiosLoaded = false;

    public SupernaturalWeaponry() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        curiosLoaded = ModList.get().isLoaded("curios");

        RegistryHandler.init();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        Advancements.MOON_PHASE = (MoonPhaseTrigger) ModEventBusEvents.registerAdvancementTrigger(new MoonPhaseTrigger());
        Advancements.TRANSFORM_INTO = (TransformIntoTrigger) ModEventBusEvents.registerAdvancementTrigger(new TransformIntoTrigger());
        Advancements.PARENT_DONE = (ParentDoneTrigger) ModEventBusEvents.registerAdvancementTrigger(new ParentDoneTrigger());
        Advancements.CHILDREN_BLOCK = (ChildrenBlock) ModEventBusEvents.registerAdvancementTrigger(new ChildrenBlock());
    }

    //registruje modely a jejich render přímo ve hře
    private void registerEntityModels(Supplier<Minecraft> minecraft)
    {
        ItemRenderer renderer = minecraft.get().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.IRON_TDAGGER_E.get(), (renderManager) ->
                new SpriteRenderer<>(renderManager, renderer));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GRENADE.get(), (renderManager) ->
                new SpriteRenderer<>(renderManager, renderer));
    }

    //registruje GUI Tile Entit, napr. sunlight collector
    private void registerTEScreens()
    {
        ScreenManager.registerFactory(ModContainers.SUNLIGHT_COLLECTOR_CONTAINER.get(), SunlightCollectorScreen::new);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        EquipmentHandler.init();

    }

    //stará se o registry na straně clienta
    private void doClientStuff(final FMLClientSetupEvent event) {

        registerEntityModels(event.getMinecraftSupplier());
        registerTEScreens();
    }


    //vytváří nový item tab v creative inventáři
    public static final ItemGroup TAB = new ItemGroup("SN_tab") {
        @Override
        public @NotNull ItemStack createIcon() {
            return RegistryHandler.SILVER_INGOT.get().getDefaultInstance();
        }
    };



    //upravuje loot tables(dropy) mobů a blocků z vanilly(popř. z jiných módů)
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
    }

    public static class Advancements {
        /**
         * moon_phase_change {<br>
         * &nbsp;&nbsp; moon_phase:(Phase)
         *(FULL_MOON,
         * WANING_GIBBOUS,
         * THIRD_QUARTER,
         * WANING_CRESCENT,
         * NEW_MOON,
         * WAXING_CRESCENT,
         * FIRST_QUARTER,
         * WAXING_GIBBOUS)(<br>
         * }<br>
         */
        public static MoonPhaseTrigger MOON_PHASE;
        /**
         * transform_into {<br>
         * &nbsp;&nbsp; transformation:(transformation)<br>
         * (Werewolf,
         * Hunter,
         * Vampire)
         * }<br>
         */
        public static TransformIntoTrigger TRANSFORM_INTO;
        /**
         * parent_done {<br>
         * &nbsp;&nbsp; parent_id:(resource_location of parent)<br>
         * }<br>
         */
        public static ParentDoneTrigger PARENT_DONE;
        /**
         * children_block {<br>
         * &nbsp;&nbsp; parent_id:(resource_location of parent)<br>
         * }<br>
         */
        public static ChildrenBlock CHILDREN_BLOCK;
    }
}
