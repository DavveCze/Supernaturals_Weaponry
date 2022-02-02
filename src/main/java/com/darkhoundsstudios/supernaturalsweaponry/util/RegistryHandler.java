package com.darkhoundsstudios.supernaturalsweaponry.util;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.armor.ModArmorMaterial;
import com.darkhoundsstudios.supernaturalsweaponry.blocks.*;
import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.items.ItemBase;
import com.darkhoundsstudios.supernaturalsweaponry.items.useable.Bandage;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.ModWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.Two_Handed;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.daggers.DaggerWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModAxe;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModPickaxe;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
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

    public static final RegistryObject<Item> BLADE = Items.register("blade", ItemBase::new);
    public static final RegistryObject<Item> HANDLE = Items.register("handle", ItemBase::new);

    public static final RegistryObject<Item> LEATHER_POUCH = Items.register("leather_pouch", ItemBase:: new);
    public static final RegistryObject<Item> MA_LEATHER_POUCH = Items.register("ma_leather_pouch", ItemBase:: new);
    public static final RegistryObject<Item> CW_LEATHER_POUCH = Items.register("cw_leather_pouch", ItemBase:: new);
    public static final RegistryObject<Item> SD_LEATHER_POUCH = Items.register("sd_leather_pouch", ItemBase:: new);

    /*
    public static final RegistryObject<Item> SILVER_TIP = Items.register("silver_tip", ItemBase:: new);
    public static final RegistryObject<Item> MAGIC_SEASHELL = Items.register("magic_seashell", ItemBase:: new);
    public static final RegistryObject<Item> CERBEROS_HORN = Items.register("cerberos_horn", ItemBase:: new);
    public static final RegistryObject<Item> CERBEROS_LEATHER = Items.register("cerberos_leather", ItemBase:: new);_
    public static final RegistryObject<Item> MISTLETOE = Items.register("mistletoe", ItemBase:: new);
    public static final RegistryObject<Item> A_BOTTLE_OF_LIZARD_POISON = Items.register("a_bottle_of_lizard_poison", ItemBase:: new);
    public static final RegistryObject<Item> SCREAM_IN_A_BOTTLE = Items.register("scream_in_a_bottle", ItemBase:: new);
    public static final RegistryObject<Item> HARDENED_LEATHER = Items.register("hardened_leather", ItemBase:: new);
    public static final RegistryObject<Item> BOOK_OF_TRANSFORMATION = Items.register("book_otransformation", ItemBase:: new);
    public static final RegistryObject<Item> BLOODY_ROSE = Items.register("bloody_rose", ItemBase:: new);
    public static final RegistryObject<Item> LEAF_OF_BLOODY_ROSE = Items.register("leaf_of_bloody_rose", ItemBase:: new);
    public static final RegistryObject<Item> DUST_OF_THE_DEAD = Items.register("dust_of_the_dead", ItemBase:: new);
    */


    //Active Items
    public static final RegistryObject<Bandage> BANDAGE = Items.register("bandage", () -> new Bandage(new Item.Properties().group(SupernaturalWeaponry.TAB)));

    //Entity drops
    public static final RegistryObject<Item> WOLF_FUR = Items.register("wolf_fur", ItemBase::new);
    public static final RegistryObject<Item> WOLF_HEART = Items.register("wolf_heart", ItemBase::new);
    public static final RegistryObject<Item> BEAR_FANG = Items.register("bear_fang", ItemBase::new);
    public static final RegistryObject<Item> DOLPHIN_FIN = Items.register("dolphin_fin", ItemBase::new);
    public static final RegistryObject<Item> JAGUAR_FUR = Items.register("jaguar_fur", ItemBase::new);
    public static final RegistryObject<Item> JAGUAR_FANG = Items.register("jaguar_fang", ItemBase::new);

    public static final RegistryObject<Item> BIRD_WING = Items.register("bird_wing", ItemBase:: new);
    //public static final RegistryObject<Item> EAR_FLAPS = Items.register("ear_flaps", ItemBase:: new);
    public static final RegistryObject<Item> OCELOT_TALON = Items.register("ocelot_talon", ItemBase:: new);
    public static final RegistryObject<Item> LIZARD_SCALE = Items.register("lizard_scale", ItemBase:: new);
    public static final RegistryObject<Item> LIZARD_HEART = Items.register("lizard_heart", ItemBase:: new);


    //Block Drops
    public static final RegistryObject<Item> DRUID_LEAF = Items.register("druid_leaf", ItemBase::new);

    //Tools- nastroje + zbrane
    //Silver
    public static final RegistryObject<DaggerWeapon> SILVER_DAGGER = Items.register("silver_dagger",()->
            new DaggerWeapon(ModItemTier.SILVER,1, -1.8f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1), ModEffects.BLEEDING));
    public static final RegistryObject<ModPickaxe> SILVER_PICKAXE = Items.register("silver_pickaxe",()->
            new ModPickaxe(ModItemTier.SILVER, 1, -2.5f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<ModAxe> SILVER_AXE = Items.register("silver_axe",()->
            new ModAxe(ModItemTier.SILVER, 5, -2.45f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<ModWeapon> SILVER_SWORD = Items.register("silver_sword",()->
            new ModWeapon(ModItemTier.SILVER, 2, -2.1f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<Two_Handed> SILVER_LONGSWORD = Items.register("silver_longsword",()->
            new Two_Handed(ModItemTier.SILVER, 10, -3.15f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<Two_Handed> SILVER_DA = Items.register("silver_da", ()->
            new Two_Handed(ModItemTier.SILVER,9,-2.90f,new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));


    //White Gold
    public static final RegistryObject<DaggerWeapon> WG_DAGGER = Items.register("wg_dagger",()->
            new DaggerWeapon(ModItemTier.WHITE_GOLD,1, -1.9f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1), ModEffects.BLEEDING));
    public static final RegistryObject<ModPickaxe> WG_PICKAXE = Items.register("wg_pickaxe",()->
            new ModPickaxe(ModItemTier.WHITE_GOLD, 1, -2.65f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<ModAxe> WG_AXE = Items.register("wg_axe",()->
            new ModAxe(ModItemTier.WHITE_GOLD, 5, -2.55f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<ModWeapon> WG_SWORD = Items.register("wg_sword",()->
            new ModWeapon(ModItemTier.WHITE_GOLD, 2, -2.35f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<Two_Handed> WG_LONGSWORD = Items.register("wg_longsword",()->
            new Two_Handed(ModItemTier.WHITE_GOLD,8, -3.35f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<Two_Handed> WG_DA = Items.register("wg_da", ()->
            new Two_Handed(ModItemTier.WHITE_GOLD,9,-3f,new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));

    //Iron
    public static final RegistryObject<DaggerWeapon> IRON_DAGGER = Items.register("iron_dagger",()->
            new DaggerWeapon(ItemTier.IRON, 1, -2.1f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1), ModEffects.BLEEDING));
    public static final RegistryObject<Two_Handed> IRON_LONGSWORD = Items.register("iron_longsword",()->
            new Two_Handed(ItemTier.IRON, 10, -3.55f, new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));
    public static final RegistryObject<ModWeapon> IRON_DA = Items.register("iron_da", ()->
            new ModWeapon(ItemTier.IRON,9,-3.2f,new Item.Properties().group(SupernaturalWeaponry.TAB).maxStackSize(1)));

    //Armors
    public static final RegistryObject<ArmorItem> WG_HELMET = Items.register("wg_helmet", () ->
            new ArmorItem(ModArmorMaterial.WG, EquipmentSlotType.HEAD, new Item.Properties().group(SupernaturalWeaponry.TAB)));
    public static final RegistryObject<ArmorItem> WG_CHESTPLATE = Items.register("wg_chestplate", () ->
            new ArmorItem(ModArmorMaterial.WG, EquipmentSlotType.CHEST, new Item.Properties().group(SupernaturalWeaponry.TAB)));
    public static final RegistryObject<ArmorItem> WG_LEGGINGS = Items.register("wg_leggings", () ->
            new ArmorItem(ModArmorMaterial.WG, EquipmentSlotType.LEGS, new Item.Properties().group(SupernaturalWeaponry.TAB)));
    public static final RegistryObject<ArmorItem> WG_BOOTS = Items.register("wg_boots", () ->
            new ArmorItem(ModArmorMaterial.WG, EquipmentSlotType.FEET, new Item.Properties().group(SupernaturalWeaponry.TAB)));
    /* public static final RegistryObject<Item> EAR_FLAPS = Items.register("ear_flaps", () ->
            new Item(new Item.Properties().group(SupernaturalWeaponry.TAB)));*/


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
