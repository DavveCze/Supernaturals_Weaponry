package com.darkhoundsstudios.supernaturalsweaponry.effects.bleeding;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.NonNullList;

public class BleedingEffectInstance extends EffectInstance {
    NonNullList<ItemStack> cures = NonNullList.withSize(1, ItemStack.EMPTY);
    public BleedingEffectInstance() {
        super(ModEffects.BLEEDING.get());
        cures.set(0, (RegistryHandler.BANDAGE.get().getDefaultInstance()));
        setCurativeItems(cures);
    }

    public BleedingEffectInstance(int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn, boolean showParticlesIn1) {
        super(ModEffects.BLEEDING.get(), durationIn, amplifierIn, ambientIn, showParticlesIn, showParticlesIn);
        cures.set(0, (RegistryHandler.BANDAGE.get().getDefaultInstance()));
        setCurativeItems(cures);
    }

    public BleedingEffectInstance(int durationIn, int amplifierIn, boolean b, boolean b1) {
        super(ModEffects.BLEEDING.get(), durationIn, amplifierIn, false, true);
        cures.set(0, (RegistryHandler.BANDAGE.get().getDefaultInstance()));
        setCurativeItems(cures);
    }
}
