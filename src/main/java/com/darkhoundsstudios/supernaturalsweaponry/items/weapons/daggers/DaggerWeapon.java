package com.darkhoundsstudios.supernaturalsweaponry.items.weapons.daggers;

import com.darkhoundsstudios.supernaturalsweaponry.effects.ModDamageSources;
import com.darkhoundsstudios.supernaturalsweaponry.effects.ModEffects;
import com.darkhoundsstudios.supernaturalsweaponry.effects.bleeding.BleedingEffectInstance;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents;
import com.darkhoundsstudios.supernaturalsweaponry.items.weapons.ModWeapon;
import com.darkhoundsstudios.supernaturalsweaponry.tools.ModItemTier;
import com.darkhoundsstudios.supernaturalsweaponry.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public class DaggerWeapon extends ModWeapon {
    private Supplier<Effect> effect;
    public float realAttackDamage;
    public float baseAttackDamage;
    public DaggerWeapon(IItemTier tier, int damage, float atc_spd, Properties properties, Supplier<Effect> _effect) {
        super(tier, damage, atc_spd, properties);
        effect = _effect;
        baseAttackDamage = tier.getAttackDamage() + damage + 1;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (playerIn.getHeldItemOffhand().getItem().equals(this.getItem())){
            ModEventBusEvents.getPlayer().customAttackTargetEntityWithCurrentItem(playerIn, target);
            playerIn.swingArm(Hand.OFF_HAND);
        }
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220045_0_) -> {
            p_220045_0_.sendBreakAnimation(attacker.getActiveHand());
        });


        if (!attacker.getEntityWorld().isRemote) {
            if (Math.random() * 100 <= 20) {
                int x = 0;
                for (EffectInstance effect : target.getActivePotionEffects()) {
                    if(effect.getPotion() == ModEffects.BLEEDING.get() && effect.getAmplifier() < 3)
                    {
                        x = effect.getAmplifier() + 1;
                    }
                }
                target.addPotionEffect(new BleedingEffectInstance( 10000, x, false, true));
            }
        }

        if (this.getTier() instanceof ModItemTier) {
            switch ((ModItemTier)this.getTier()) {
                case SILVER:
                    performSpecial_Silver(stack,target,attacker, baseAttackDamage);
                break;
                case WHITE_GOLD:
                    performSpecial_WG(stack,target,attacker, baseAttackDamage);
                break;
            }
        }

        return true;
    }

}
