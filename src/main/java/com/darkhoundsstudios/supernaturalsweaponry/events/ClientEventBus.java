package com.darkhoundsstudios.supernaturalsweaponry.events;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.client.render.ModPlayerRenderer;
import com.darkhoundsstudios.supernaturalsweaponry.client.render.Werewolf_WolfRender;
import com.darkhoundsstudios.supernaturalsweaponry.entities.ModEntities;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {
    static ClientPlayerEntity player;
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WEREWOLF_WOLF.get(), Werewolf_WolfRender::new);
    }

    @SubscribeEvent
    public static void changePlayerModel(RenderPlayerEvent.Pre event){
        event.setCanceled(true);
        new ModPlayerRenderer(event.getRenderer().getRenderManager()).render((AbstractClientPlayerEntity) event.getPlayer()
        , event.getPlayer().cameraYaw,event.getPartialRenderTick(),event.getMatrixStack(),event.getBuffers(),event.getLight());
        player = (ClientPlayerEntity) event.getPlayer();
    }

    public static ClientPlayerEntity getPlayer() {
        return player;
    }
}
