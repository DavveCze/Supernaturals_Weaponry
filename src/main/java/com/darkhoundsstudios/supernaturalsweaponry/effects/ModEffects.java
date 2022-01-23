package com.darkhoundsstudios.supernaturalsweaponry.effects;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {
    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, SupernaturalWeaponry.Mod_ID);

    public static RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", () ->
            new BleedingEffect(EffectType.HARMFUL, 4393481, "Bleeding"));
}
