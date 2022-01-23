package com.darkhoundsstudios.supernaturalsweaponry.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BleedingEffect extends Effect {
    private final EffectType type;
    private final int liquidColor;
    private String name;

    protected BleedingEffect(EffectType _effectType, int _liquidCol, String _Name) {
        super(_effectType, _liquidCol);
        type = _effectType;
        liquidColor = _liquidCol;
        name = _Name;
    }

    @Override
    public void performEffect(@NotNull LivingEntity entityLivingBaseIn, int amplifier) {
        if (this == ModEffects.BLEEDING.get())
            if (entityLivingBaseIn.getHealth() > 0.0F) entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == ModEffects.BLEEDING.get()) {
            int j = 20 >> amplifier;
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
    public int getLiquidColor() {
        return this.liquidColor;
    }

    @Override
    public EffectType getEffectType() {
        return this.type;
    }

    @Override
    public BleedingEffect getEffect() {
        return this;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        this.performEffect(entityLivingBaseIn, amplifier);
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}
