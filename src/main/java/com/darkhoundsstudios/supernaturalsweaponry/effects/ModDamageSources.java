package com.darkhoundsstudios.supernaturalsweaponry.effects;

import net.minecraft.util.DamageSource;

public class ModDamageSources extends DamageSource {
    public static final DamageSource PURITY = new ModDamageSources("purity").setMagicDamage().setDamageBypassesArmor();

    public ModDamageSources(String name) {
        super(name);
    }

}
