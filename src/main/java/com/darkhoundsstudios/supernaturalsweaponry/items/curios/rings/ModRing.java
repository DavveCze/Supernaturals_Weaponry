package com.darkhoundsstudios.supernaturalsweaponry.items.curios.rings;

import com.darkhoundsstudios.supernaturalsweaponry.items.curios.ItemBauble;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;

public class ModRing extends ItemBauble {
    public ModRing(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquipped(ItemStack stack, LivingEntity entity) {
        for (EffectInstance eff: entity.getActivePotionEffects()) {
            if (eff != null)
                entity.removePotionEffect(eff.getPotion());
        }
        super.onEquipped(stack, entity);
    }
}
