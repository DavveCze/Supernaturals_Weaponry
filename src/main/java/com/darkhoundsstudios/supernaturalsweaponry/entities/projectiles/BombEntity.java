package com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles;

import com.darkhoundsstudios.supernaturalsweaponry.entities.ModEntities;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;


public class BombEntity extends ProjectileItemEntity {

    private boolean PoisonActive,FireActive;
    private float explosion_radius;
    public BombEntity(EntityType<BombEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BombEntity(double x, double y, double z, World worldIn) {
        super(ModEntities.GRENADE.get(), x, y, z, worldIn);
    }

    public BombEntity(LivingEntity livingEntityIn, World worldIn, boolean _PoisonActive,boolean _FireActive, float explosion_radius) {
        super(ModEntities.GRENADE.get(), livingEntityIn, worldIn);
        PoisonActive = _PoisonActive;
        FireActive = _FireActive;
        this.explosion_radius = explosion_radius;
    }


    @Override
    protected Item getDefaultItem() {
        return RegistryHandler.GRENADE.get().asItem();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (this.inWater) {
            ItemEntity x = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), getDefaultItem().getDefaultInstance());
            world.addEntity(x);
        } else {
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), explosion_radius, FireActive, Explosion.Mode.BREAK);
            if (PoisonActive) {
                AreaEffectCloudEntity x = new AreaEffectCloudEntity(world, getPosX(), getPosY(), getPosZ());
                x.setPotion(Potions.POISON);
                x.setColor(Effects.POISON.getLiquidColor());
                x.setRadius(1.5f);
                x.setDuration(50);
                x.setRadiusOnUse(0.75f);
                x.setWaitTime(0);
                world.addEntity(x);
            }
        }
        if (!world.isRemote())
            this.remove();
    }


    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
