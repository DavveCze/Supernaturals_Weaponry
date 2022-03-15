package com.darkhoundsstudios.supernaturalsweaponry.items;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    //pouze nastavuje v jakém Creative tabu se má předmět nacházet
    public ItemBase() {
        super(new Item.Properties().group(SupernaturalWeaponry.TAB));
    }
}
