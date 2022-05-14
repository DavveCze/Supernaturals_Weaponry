package com.darkhoundsstudios.supernaturalsweaponry.items.curios.rings;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Objects;

public class CheetahRing extends ModRing{
    public CheetahRing(Properties properties) {
        super(properties);
    }
    int x = 0;

    @Override
    public void onEquipped(ItemStack stack, LivingEntity entity) {
        for (EffectInstance effect : entity.getActivePotionEffects()) {
            if(effect.getPotion() == Effects.SPEED) {
                x = effect.getAmplifier() + 1;
                System.out.println(effect.getAmplifier() + ", " + x);
            }
        }
        entity.addPotionEffect(new EffectInstance(Effects.SPEED, 300, x, false, false));
        super.onEquipped(stack, entity);
    }

    @Override
    public void onUnequipped(ItemStack stack, LivingEntity entity) {
        EffectInstance effectInstanceA = entity.getActivePotionEffect(Effects.SPEED);
        entity.removePotionEffect(effectInstanceA.getPotion());
        EffectInstance finA;
        if (effectInstanceA != null) {
            x = effectInstanceA.getAmplifier() - 1;
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
            if(entity.getActivePotionEffect(Effects.SPEED) != null) {
                x = entity.getActivePotionEffect(Effects.SPEED).getAmplifier();
                entity.addPotionEffect(new EffectInstance(Effects.SPEED, 300, x, false, false));
            }
        }
        super.onWornTick(stack, entity);
    }
}
