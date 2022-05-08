package com.darkhoundsstudios.supernaturalsweaponry.events;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.client.render.Werewolf_WolfRender;
import com.darkhoundsstudios.supernaturalsweaponry.entities.ModEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {
    static ClientPlayerEntity player;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WEREWOLF_WOLF.get(), Werewolf_WolfRender::new);
    }

    public static ClientPlayerEntity getPlayer() {
        return player;
    }
}
