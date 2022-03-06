package com.darkhoundsstudios.supernaturalsweaponry.entities;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.entities.entity.werewolf.wolf.Werewolf_Wolf;
import com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles.BombEntity;
import com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles.DaggerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> Entities = new DeferredRegister<>(ForgeRegistries.ENTITIES, SupernaturalWeaponry.Mod_ID);

    public static final RegistryObject<EntityType<DaggerEntity>> IRON_TDAGGER_E = Entities.register("iron_tdagger_e", () ->
            EntityType.Builder.<DaggerEntity>create(DaggerEntity::new, EntityClassification.MISC).size(0.5f,0.5f).build("iron_tdagger_e"));
    public static final RegistryObject<EntityType<BombEntity>> GRENADE = Entities.register("grenade", () ->
            EntityType.Builder.<BombEntity>create(BombEntity::new, EntityClassification.MISC).size(0.35f,0.35f).build("grenade"));
    public static final RegistryObject<EntityType<Werewolf_Wolf>> WEREWOLF_WOLF = Entities.register("werewolf_wolf", () ->
       EntityType.Builder.create(Werewolf_Wolf::new,EntityClassification.MONSTER).size(1.2f,1f).build(new ResourceLocation(SupernaturalWeaponry.Mod_ID, "werewolf_wolf").toString()));
}
