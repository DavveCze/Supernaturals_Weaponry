package com.darkhoundsstudios.supernaturalsweaponry.tools;

import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;


public enum ModItemTier implements IItemTier {
    //1+baseDmg+addedDmg

    //nastavuje vlastnosti nových materiálů
    SILVER(1, 250, 8.0f, 1f, 12, () -> {
        return Ingredient.fromItems(RegistryHandler.SILVER_INGOT.get());
    }),
    WHITE_GOLD(2, 145, 5.5f, 0f, 23, () -> {
        return Ingredient.fromItems(RegistryHandler.WG_INGOT.get());
    });
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int _harvestLevel, int _maxUses, float _efficiency, float _attackDamage, int _enchantability, Supplier<Ingredient> _repairMaterial) {
        harvestLevel = _harvestLevel;
        maxUses = _maxUses;
        efficiency = _efficiency;
        attackDamage = _attackDamage;
        enchantability = _enchantability;
        repairMaterial = _repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }

}
