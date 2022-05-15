package com.darkhoundsstudios.supernaturalsweaponry.items.useable;

import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import static com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents.getPlayer;


public class Elixir_Lycanis extends PotionItem {
    public Elixir_Lycanis(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        entityLiving.addPotionEffect(new EffectInstance(Effects.WITHER, 150, 2, false, false));
        entityLiving.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100, 0, false, false));
        entityLiving.addPotionEffect(new EffectInstance(Effects.NAUSEA, 180, 1, false, false));
        entityLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 150, 2, false, false));
        if(entityLiving instanceof PlayerEntity) {
            if (getPlayer().canTransformW & getPlayer().type == null){
                if(Math.random() * 100 <= 55) {
                    getPlayer().type = ModPlayerEntity.TransformationType.Werewolf;
                    getPlayer().canTransformW = false;
                }
            }
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
