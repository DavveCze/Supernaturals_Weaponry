package com.darkhoundsstudios.supernaturalsweaponry.items.curios;

import com.darkhoundsstudios.supernaturalsweaponry.util.EquipmentHandler;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import top.theillusivec4.curios.api.capability.ICurio;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class ItemBauble extends Item implements ICurio {
    private static final String TAG_BAUBLE_UUID_MOST = "baubleUUIDMost";
    private static final String TAG_BAUBLE_UUID_LEAST = "baubleUUIDLeast";
    public ItemBauble(Properties properties) {
        super(properties);
    }

    public Multimap<String, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        System.out.println(stack.getAttributeModifiers(stack.getEquipmentSlot()));
        return stack.getAttributeModifiers(Objects.requireNonNull(stack.getEquipmentSlot()));
        //return HashMultimap.create();
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return EquipmentHandler.initBaubleCap(stack);
    }

    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return true;
    }
    public void onEquipped(ItemStack stack, LivingEntity entity) {}

    public void onUnequipped(ItemStack stack, LivingEntity entity) {}
    public static UUID getBaubleUUID(ItemStack stack) {
        if(!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
            UUID id = UUID.randomUUID();
            stack.getTag().putLong(TAG_BAUBLE_UUID_MOST, id.getMostSignificantBits());
            stack.getTag().putLong(TAG_BAUBLE_UUID_LEAST, id.getLeastSignificantBits());
            System.out.println(stack.getTag());
        }
        return new UUID(stack.getTag().getLong(TAG_BAUBLE_UUID_MOST), stack.getTag().getLong(TAG_BAUBLE_UUID_LEAST));
    }

    @Override
    public void onEquipped(String identifier, LivingEntity livingEntity) {
        ICurio.super.onEquipped(identifier, livingEntity);
    }

    public void onWornTick(ItemStack stack, LivingEntity entity) {}
}
