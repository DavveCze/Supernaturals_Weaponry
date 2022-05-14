package com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;

import java.util.ArrayList;
import java.util.List;

import static net.java.games.input.Component.Identifier.*;

public interface Skill {
    Skill parent_name = null;
    List<AttributeModifier> attributes = new ArrayList<>();
    List<Effect> effects = new ArrayList<>();
    Key key = null;
    List<Skill> children = new ArrayList<>();

    public void onUse(LivingEntity entity); public void onDisable(LivingEntity entity);
    public void applyEffects(LivingEntity entity);
    public void applyModifiers(LivingEntity entity);
}
