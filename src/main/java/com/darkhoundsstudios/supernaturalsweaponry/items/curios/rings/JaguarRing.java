package com.darkhoundsstudios.supernaturalsweaponry.items.curios.rings;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class JaguarRing extends ModRing{
    public JaguarRing(Properties properties) {
        super(properties);
    }
    int x = 0, y = 0;

    @Override
    public void onEquipped(ItemStack stack, LivingEntity entity) {
        for (EffectInstance effect : entity.getActivePotionEffects()) {
            if(effect.getPotion() == Effects.STRENGTH) {
                x = effect.getAmplifier() + 1;
                System.out.println(effect.getAmplifier() + ", " + x);
            }
            else if(effect.getPotion() == Effects.RESISTANCE){
                y = effect.getAmplifier() + 1;
                System.out.println(effect.getAmplifier() + ", " + y);
            }
        }
        entity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 300, x, false, false));
        entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, y, false, false));
        super.onEquipped(stack, entity);
    }

    @Override
    public void onUnequipped(ItemStack stack, LivingEntity entity) {
        EffectInstance finA,finB;
        EffectInstance effectInstanceA = entity.getActivePotionEffect(Effects.STRENGTH);
        EffectInstance effectInstanceB = entity.getActivePotionEffect(Effects.RESISTANCE);
        entity.removePotionEffect(effectInstanceA.getPotion());
        entity.removePotionEffect(effectInstanceB.getPotion());
        x = effectInstanceA.getAmplifier() - 1;
        y = effectInstanceB.getAmplifier() - 1;
        if (x >= 0) {
            finA = new EffectInstance(effectInstanceA.getPotion(), effectInstanceA.getDuration(), x, false, false);
            entity.addPotionEffect(finA);
        }
        if (y >= 0) {
            finB = new EffectInstance(effectInstanceB.getPotion(), effectInstanceB.getDuration(), y, false, false);
            entity.addPotionEffect(finB);
        }
        super.onUnequipped(stack, entity);
    }

    int t = 0;
    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        t++;
        if (t % 160 == 0) {
            if(entity.getActivePotionEffect(Effects.STRENGTH) != null && entity.getActivePotionEffect(Effects.RESISTANCE) != null) {
                x = entity.getActivePotionEffect(Effects.STRENGTH).getAmplifier();
                y = entity.getActivePotionEffect(Effects.RESISTANCE).getAmplifier();
                entity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 300, x, false, false));
                entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, y, false, false));
            }
        }
        super.onWornTick(stack, entity);
    }
}
