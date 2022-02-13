package com.darkhoundsstudios.supernaturalsweaponry.entities.projectiles;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModDamageSources;
import com.darkhoundsstudios.supernaturalsweaponry.entities.ModEntities;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.ModWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import static com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier.SILVER;
import static com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier.WHITE_GOLD;

public class DaggerEntity extends ProjectileItemEntity {
    private IItemTier material;

    public DaggerEntity(EntityType<DaggerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public DaggerEntity(double x, double y, double z, World worldIn) {
        super(ModEntities.IRON_TDAGGER_E.get(), x, y, z, worldIn);
    }

    public DaggerEntity(LivingEntity livingEntityIn, World worldIn) {
        super(ModEntities.IRON_TDAGGER_E.get(), livingEntityIn, worldIn);
    }

    public void setMaterial(IItemTier _material)
    {
        material = _material;
    }

    @Override
    protected Item getDefaultItem() {
        return RegistryHandler.IRON_TDAGGER.get().asItem();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY)
        {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            float damage = 3;
            if (material != null)
            {
                if (SILVER.equals(material) && ((LivingEntity)entity).isEntityUndead()) {
                    damage = damage * 2;
                } else if (WHITE_GOLD.equals(material)) {
                    damage = WG_dmg(damage, (LivingEntity) entity);
                }
            }
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this,this.getThrower()),damage);
        }
        if(!world.isRemote())
        {
            this.remove();
        }
    }

    private float WG_dmg(float _damage, LivingEntity target){
        float vanillaDMG = 0;
        float realAttackDamage = 0;
        if (target.getArmorInventoryList().toString().contains("diamond")) {
            float x = 8;
            x = x * target.getArmorCoverPercentage();
            vanillaDMG = (_damage - _damage * (1 - (Math.min(20, Math.max(((target.getTotalArmorValue() - x) / 5), (target.getTotalArmorValue() - x) - ((4 * _damage) / (x + 8))))) / 25));
            realAttackDamage = _damage + vanillaDMG + vanillaDMG * 0.49f + 1;
        } else {
            vanillaDMG = ((_damage - _damage * (1 - (Math.min(20, Math.max((target.getTotalArmorValue() / 5f), target.getTotalArmorValue() - ((4 * _damage) / 8)))) / 25)));
            realAttackDamage = _damage + vanillaDMG + vanillaDMG * 0.49f;
        }
        return realAttackDamage;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
