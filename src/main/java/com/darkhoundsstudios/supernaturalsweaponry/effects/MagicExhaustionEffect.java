package com.darkhoundsstudios.supernaturalsweaponry.effects;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MagicExhaustionEffect extends Effect {
    private final EffectType type;
    private final int liquidColor;
    private final String name;

    protected MagicExhaustionEffect(EffectType typeIn, int liquidColorIn, String nameIn) {
        super(typeIn, liquidColorIn);
        type = typeIn;
        liquidColor = liquidColorIn;
        name = nameIn;
    }

    @Override
    public void performEffect(@NotNull LivingEntity entityLivingBaseIn, int amplifier) {
        super.performEffect(entityLivingBaseIn, amplifier);
        if (this == ModEffects.MAGIC_EXHAUSTION.get()) {
            if (amplifier >= 0) {
                for (EffectInstance effect : entityLivingBaseIn.getActivePotionEffects()) {
                    System.out.println(effect);
                    if (effect.getPotion() == ModEffects.MAGIC_EXHAUSTION.get() || effect.getPotion()
                            == ModEffects.BLEEDING.get() || effect.getPotion() == Effects.POISON ||
                            effect.getPotion() == Effects.HUNGER || effect.getPotion() == Effects.SATURATION) {
                    } else {
                        entityLivingBaseIn.removePotionEffect(effect.getPotion());

                    }
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == ModEffects.MAGIC_EXHAUSTION.get()) {
            int j = 6 >> amplifier;
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
    public MagicExhaustionEffect getEffect() {
        return this;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        this.performEffect(entityLivingBaseIn, amplifier);
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}
