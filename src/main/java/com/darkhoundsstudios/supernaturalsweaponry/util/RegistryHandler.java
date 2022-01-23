package com.darkhoundsstudios.supernaturalsweaponry.util;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.blocks.*;
import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.items.ItemBase;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.SilverWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.WGWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.daggers.DaggerWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> Items = new DeferredRegister<>(ForgeRegistries.ITEMS, SupernaturalWeaponry.Mod_ID);

    public static final DeferredRegister<Block> Blocks = new DeferredRegister<>(ForgeRegistries.BLOCKS, SupernaturalWeaponry.Mod_ID);

    public static void init()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Items.register(bus);
        Blocks.register(bus);
        ModEffects.EFFECTS.register(bus);
    }


    //Items
    public static final RegistryObject<Item> SILVER_INGOT = Items.register("silver_ingot", ItemBase::new);
    public static final RegistryObject<Item> SILVER_NUGGET = Items.register("silver_nugget", ItemBase::new);
    public static final RegistryObject<Item> WG_INGOT = Items.register("wg_ingot", ItemBase::new);
    public static final RegistryObject<Item> WG_NUGGET = Items.register("wg_nugget", ItemBase::new);

    public static final RegistryObject<Item> MANA_CRYSTAL = Items.register("mana_crystal", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST = Items.register("amethyst", ItemBase::new);
    public static final RegistryObject<Item> AMETHYST_BRUSHED = Items.register("amethyst_brushed", ItemBase::new);

    public static final RegistryObject<Item> Blade = Items.register("blade", ItemBase::new);
    public static final RegistryObject<Item> Handle = Items.register("handle", ItemBase::new);

    //Entity drops
    public static final RegistryObject<Item> WOLF_FUR = Items.register("wolf_fur", ItemBase::new);
    public static final RegistryObject<Item> WOLF_HEART = Items.register("wolf_heart", ItemBase::new);
    public static final RegistryObject<Item> BEAR_FANG = Items.register("bear_fang", ItemBase::new);
    public static final RegistryObject<Item> DOLPHIN_FIN = Items.register("dolphin_fin", ItemBase::new);
    public static final RegistryObject<Item> JAGUAR_FUR = Items.register("jaguar_fur", ItemBase::new);
    public static final RegistryObject<Item> JAGUAR_FANG = Items.register("jaguar_fang", ItemBase::new);

    //Block Drops
    public static final RegistryObject<Item> DRUID_LEAF = Items.register("druid_leaf", ItemBase::new);

    //Tools- nastroje + zbrane
    //Silver
    public static final RegistryObject<DaggerWeapon> SILVER_DAGGER = Items.register("silver_dagger",()->
            new DaggerWeapon(ModItemTier.SILVER,1, -1.8f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1), ModEffects.BLEEDING));
    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = Items.register("silver_pickaxe",()->
            new PickaxeItem(ModItemTier.SILVER, 1, -2.5f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<AxeItem> SILVER_AXE = Items.register("silver_axe",()->
            new AxeItem(ModItemTier.SILVER, 5, -2.45f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<SilverWeapon> SILVER_SWORD = Items.register("silver_sword",()->
            new SilverWeapon(2, -2.1f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<SilverWeapon> SILVER_LONGSWORD = Items.register("silver_longsword",()->
            new SilverWeapon(10, -3.15f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));


    //White Gold
    public static final RegistryObject<DaggerWeapon> WG_DAGGER = Items.register("wg_dagger",()->
            new DaggerWeapon(ModItemTier.WHITE_GOLD,1, -1.9f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1), ModEffects.BLEEDING));
    public static final RegistryObject<PickaxeItem> WG_PICKAXE = Items.register("wg_pickaxe",()->
            new PickaxeItem(ModItemTier.WHITE_GOLD, 1, -2.65f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<AxeItem> WG_AXE = Items.register("wg_axe",()->
            new AxeItem(ModItemTier.WHITE_GOLD, 5, -2.55f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<WGWeapon> WG_SWORD = Items.register("wg_sword",()->
            new WGWeapon(2, -2.35f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<WGWeapon> WG_LONGSWORD = Items.register("wg_longsword",()->
            new WGWeapon(8, -3.35f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));

    //Iron
    public static final RegistryObject<DaggerWeapon> IRON_DAGGER = Items.register("iron_dagger",()->
            new DaggerWeapon(ItemTier.IRON, 1, -2.1f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1), ModEffects.BLEEDING));
    public static final RegistryObject<SwordItem> IRON_LONGSWORD = Items.register("iron_longsword",()->
            new SwordItem(ItemTier.IRON, 10, -3.55f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));


    //Blocks
    public static final RegistryObject<Block> SILVER_BLOCK = Blocks.register("silver_block", SilverBlock::new);
    public static final RegistryObject<Block> WG_BLOCK = Blocks.register("wg_block", WGBlock::new);
    public static final RegistryObject<Block> SILVER_ORE = Blocks.register("silver_ore", SilverOre::new);
    public static final RegistryObject<Block> WG_ORE = Blocks.register("wg_ore", WGOre::new);
    public static final RegistryObject<Block> MANA_ORE = Blocks.register("mana_ore", ManaOre::new);
    public static final RegistryObject<Block> AMETHYST_ORE = Blocks.register("amethyst_ore", AmethystOre::new);

    //Blocks Items
    public static final RegistryObject<Item> SILVER_BLOCK_ITEM = Items.register("silver_block", () -> new BlockItemBase(SILVER_BLOCK.get()));
    public static final RegistryObject<Item> WG_BLOCK_ITEM = Items.register("wg_block", () -> new BlockItemBase(WG_BLOCK.get()));
    public static final RegistryObject<Item> WG_ORE_ITEM = Items.register("wg_ore", () -> new BlockItemBase(WG_ORE.get()));
    public static final RegistryObject<Item> SILVER_ORE_ITEM = Items.register("silver_ore", () -> new BlockItemBase(SILVER_ORE.get()));
    public static final RegistryObject<Item> MANA_ORE_ITEM = Items.register("mana_ore", () -> new BlockItemBase(MANA_ORE.get()));
    public static final RegistryObject<Item> AMETHYST_ORE_ITEM = Items.register("amethyst_ore", () -> new BlockItemBase(AMETHYST_ORE.get()));
}
