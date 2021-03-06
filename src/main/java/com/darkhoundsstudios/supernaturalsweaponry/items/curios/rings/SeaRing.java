package com.darkhoundsstudios.supernaturalsweaponry.items.curios.rings;


import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.items.curios.ItemBauble;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Objects;


public class SeaRing extends ItemBauble {
    public SeaRing(Properties properties) {
        super(properties);
    }
    int x = 0;

    @Override
    public void onEquipped(ItemStack stack, LivingEntity entity) {
        for (EffectInstance effect : entity.getActivePotionEffects()) {
            if(effect.getPotion() == Effects.WATER_BREATHING) {
                x = effect.getAmplifier() + 1;
                System.out.println(effect.getAmplifier() + ", " + x);
                break;
            }
        }
        entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 300, x, false, false));
        entity.getAttribute(LivingEntity.SWIM_SPEED).applyModifier(new AttributeModifier(getBaubleUUID(stack), "Sea Ring",
                0.25d, AttributeModifier.Operation.MULTIPLY_BASE));
        super.onEquipped(stack, entity);
    }

    @Override
    public void onUnequipped(ItemStack stack, LivingEntity entity) {
        entity.getAttribute(LivingEntity.SWIM_SPEED).removeModifier(getBaubleUUID(stack));
        EffectInstance effectInstanceA = entity.getActivePotionEffect(Effects.WATER_BREATHING);
        entity.removePotionEffect(Effects.WATER_BREATHING);
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
            if (entity.getActivePotionEffect(Effects.WATER_BREATHING) != null) {
                x = entity.getActivePotionEffect(Effects.WATER_BREATHING).getAmplifier();
                entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 300, x, false, false));
            }
        }
        super.onWornTick(stack, entity);
    }

}
