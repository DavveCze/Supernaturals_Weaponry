package com.darkhoundsstudios.supernaturalsweaponry.effects;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.effects.bleeding.BleedingEffect;
import com.darkhoundsstudios.supernaturalsweaponry.effects.wolfgift.WolfGiftEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class ModEffects {
    //registuje všechny effekty
    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, SupernaturalWeaponry.Mod_ID);

    public static RegistryObject<Effect> BLEEDING = EFFECTS.register("bleeding", () ->
            new BleedingEffect(EffectType.HARMFUL, 4393481, "Bleeding"));
    public static RegistryObject<Effect> MAGIC_EXHAUSTION = EFFECTS.register("magic_exhaustion", () ->
            new MagicExhaustionEffect(EffectType.NEUTRAL, 3115194, "Magic Exhaustion"));
    public static RegistryObject<Effect> WOLF_GIFT = EFFECTS.register("wolf_gift", () ->
            new WolfGiftEffect(EffectType.NEUTRAL, 3115194, "Wolf's Gift").addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, String.valueOf(UUID.randomUUID()), (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));
}
