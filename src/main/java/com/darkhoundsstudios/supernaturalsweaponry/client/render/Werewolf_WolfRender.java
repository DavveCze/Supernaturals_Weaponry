package com.darkhoundsstudios.supernaturalsweaponry.client.render;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;

public class Werewolf_WolfRender extends WolfRenderer {
    //upravuje modelu textury
    private static final ResourceLocation WOLF_TEXTURES = new ResourceLocation(SupernaturalWeaponry.Mod_ID, "textures/entity/werewolf/werewolf_wolf.png");

    public Werewolf_WolfRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(WolfEntity entity) {
        return WOLF_TEXTURES;
    }
}
