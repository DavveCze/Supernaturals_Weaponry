package com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations;

import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;

public interface Skill {
    void onUse(LivingEntity entity); void onDisable(LivingEntity entity);
    void applyEffects(LivingEntity entity); boolean cooldown(); int getID();
    void applyModifiers(LivingEntity entity); ArrayList<Boolean> parameters();
}
