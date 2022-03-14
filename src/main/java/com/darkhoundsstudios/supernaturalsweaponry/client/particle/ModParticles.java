package com.darkhoundsstudios.supernaturalsweaponry.client.particle;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, SupernaturalWeaponry.Mod_ID);

    public static final RegistryObject<BasicParticleType> LEVEL_UP_PARTICLE = PARTICLES.register("level_up_particle", () ->
            new BasicParticleType(true));
}
