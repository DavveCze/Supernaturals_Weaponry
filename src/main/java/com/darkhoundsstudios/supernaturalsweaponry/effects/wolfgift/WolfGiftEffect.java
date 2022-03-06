package com.darkhoundsstudios.supernaturalsweaponry.effects.wolfgift;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.*;

import javax.annotation.Nullable;

public class WolfGiftEffect extends Effect {
    //spojení regenerace a rychlosti
    public int amplifier1, amplifier2;

    private final EffectType type;
    private final int liquidColor;
    private final String name;

    public WolfGiftEffect(EffectType typeIn, int liquidColorIn, String nameIn) {
        super(typeIn, liquidColorIn);

        type = typeIn;
        liquidColor = liquidColorIn;
        name = nameIn;
    }

    @Override
    //upravuje speed modifier
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * amplifier2;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        if (this == ModEffects.WOLF_GIFT.get()) {
            if (amplifier == 0) {
                amplifier1 = 1;
                amplifier2 = 0;
            } else if (amplifier == 1) {
                amplifier1 = 1;
                amplifier2 = 1;
            }else if (amplifier == 2) {
                amplifier1 = 2;
                amplifier2 = 1;
            }
            if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
                entityLivingBaseIn.heal(1.0F);
            }
        }
    }


    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == ModEffects.WOLF_GIFT.get()) {
            int j = 50 >> amplifier1;
            if (j > 0) {
                return duration % j == 0;
            } else {
                return true;
            }
        }
        return super.isReady(duration, amplifier);
    }

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public EffectType getEffectType() {
        return this.type;
    }

    @Override
    public WolfGiftEffect getEffect() {
        return this;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        this.performEffect(entityLivingBaseIn, amplifier);
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}