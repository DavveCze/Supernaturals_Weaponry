package com.darkhoundsstudios.supernaturalsweaponry.items.curios.rings;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Objects;

public class TurtleRing extends ModRing{
    public TurtleRing(Properties properties) {
        super(properties);
    }
    int x = 0;

    @Override
    public void onEquipped(ItemStack stack, LivingEntity entity) {
        for (EffectInstance effect : entity.getActivePotionEffects()) {
            if(effect.getPotion() == Effects.RESISTANCE) {
                x = effect.getAmplifier() + 2;
                System.out.println(effect.getAmplifier() + ", " + x);
            }
        }
        if(x == 0)
            x = 1;
        entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, x, false, false));
        super.onEquipped(stack, entity);
    }

    @Override
    public void onUnequipped(ItemStack stack, LivingEntity entity) {
        EffectInstance effectInstanceA = entity.getActivePotionEffect(Effects.RESISTANCE);
        entity.removePotionEffect(effectInstanceA.getPotion());
        EffectInstance finA;
        if (effectInstanceA != null) {
            x = effectInstanceA.getAmplifier() - 2;
            if (x >= 0) {
                finA = new EffectInstance(effectInstanceA.getPotion(), effectInstanceA.getDuration(), x, false, false);
                entity.addPotionEffect(finA);
            }
        }
        super.onUnequipped(stack, entity);
    }

    int t = 0;
    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        t++;
        if (t % 160 == 0) {
            if(entity.getActivePotionEffect(Effects.RESISTANCE) != null) {
                x = entity.getActivePotionEffect(Effects.RESISTANCE).getAmplifier();
                entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, x, false, false));
            }
        }
        super.onWornTick(stack, entity);
    }
}
