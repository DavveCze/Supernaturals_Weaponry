package com.darkhoundsstudios.supernaturalsweaponry.items.curios.necklaces;

import com.darkhoundsstudios.supernaturalsweaponry.items.curios.ItemBauble;
import net.minecraft.entity.LivingEntity;

public class ModNeklace extends ItemBauble {
    public ModNeklace(Properties properties) {
        super(properties);
    }
    @Override
    public boolean canRightClickEquip() {
        return true;
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity livingEntity) {
        return super.canEquip("necklace", livingEntity);
    }
}
