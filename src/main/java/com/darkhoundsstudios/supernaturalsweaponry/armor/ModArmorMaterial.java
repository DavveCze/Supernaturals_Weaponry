package com.darkhoundsstudios.supernaturalsweaponry.armor;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {
    //stará se a drží informace o armor materiálech
    WG(SupernaturalWeaponry.Mod_ID + ":wg", 11, new int[]{1, 2, 4, 1},23,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,0, () -> {return Ingredient.fromItems(RegistryHandler.WG_INGOT.get());});

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{11,16,15,13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterial(String _name, int _maxDamageFactor, int[] _damageReductionAmountArray, int _enchantability, SoundEvent _soundEvent,float _toughness,Supplier<Ingredient> _repairMaterial){
        name = _name; maxDamageFactor = _maxDamageFactor; damageReductionAmountArray = _damageReductionAmountArray;
        enchantability = _enchantability; soundEvent = _soundEvent;
        toughness = _toughness; repairMaterial = _repairMaterial;
    };


    @Override
    public int getDurability(EquipmentSlotType equipmentSlotType) {
        return MAX_DAMAGE_ARRAY[equipmentSlotType.getIndex()];
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType equipmentSlotType) {
        return damageReductionAmountArray[equipmentSlotType.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }
}
