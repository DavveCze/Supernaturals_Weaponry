package com.darkhoundsstudios.supernaturalsweaponry.client.particle;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleUtil {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory(ModParticles.LEVEL_UP_PARTICLE.get(), LevelUp_Particle.Factory::new);
    }
}
