package com.darkhoundsstudios.supernaturalsweaponry.items;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class ItemBase extends Item {

    public ItemBase() {
        super(new Item.Properties().group(SupernaturalWeaponry.TAB));
    }
}
