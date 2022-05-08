package com.darkhoundsstudios.supernaturalsweaponry.integration.curios;

import com.darkhoundsstudios.supernaturalsweaponry.items.curios.ItemBauble;
import com.darkhoundsstudios.supernaturalsweaponry.util.EquipmentHandler;
import com.darkhoundsstudios.supernaturalsweaponry.util.SimpleCapProvider;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.event.LivingCurioDropRulesEvent;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;
import java.util.function.Predicate;


public class CuriosIntegration extends EquipmentHandler {
    public static void sendImc(InterModEnqueueEvent event){
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("necklace").setSize(1));
        InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("ring").setSize(2));
    }

    public static void keepCurioDrops(LivingCurioDropRulesEvent event) {
        event.addOverride(stack -> false, ICurio.DropRule.ALWAYS_KEEP);
    }

    @Override
    protected LazyOptional<IItemHandlerModifiable> getAllWornItems(LivingEntity living) {
        return getAllWorn(living);
    }

    @Override
    public boolean isAccessory(ItemStack stack) {
        return super.isAccessory(stack) || stack.getCapability(CuriosCapability.ITEM).isPresent();
    }

    @Override
    protected ItemStack findItem(Item item, LivingEntity living) {
        return CuriosAPI.getCurioEquipped(item, living)
                .map(ImmutableTriple::getRight)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    protected ItemStack findItem(Predicate<ItemStack> pred, LivingEntity living) {
        return CuriosAPI.getCurioEquipped(pred, living)
                .map(ImmutableTriple::getRight)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    protected ICapabilityProvider initCap(ItemStack stack) {
        return new SimpleCapProvider<>(CuriosCapability.ITEM, new Wrapper(stack));
    }
    public static class Wrapper implements ICurio {
        private final ItemStack stack;

        Wrapper(ItemStack stack) {
            this.stack = stack;
        }

        private ItemBauble getItem() {
            return (ItemBauble) stack.getItem();
        }

        @Override
        public void onCurioTick(String identifier, int index, LivingEntity entity) {
            getItem().onWornTick(stack, entity);
        }

        @Override
        public void onEquipped(String identifier, LivingEntity entity) {
            getItem().onEquipped(stack, entity);
        }

        @Override
        public void onUnequipped(String identifier, LivingEntity entity) {
            getItem().onUnequipped(stack, entity);
        }


        @Override
        public boolean canEquip(String identifier, LivingEntity entity) {
            return getItem().canEquip(stack, entity);
        }

        @Override
        public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
            return getItem().getAttributeModifiers(stack.getEquipmentSlot(),stack);
        }

        @Override
        public boolean shouldSyncToTracking(String identifier, LivingEntity livingEntity) {
            return true;
        }

        @Override
        public void playEquipSound(LivingEntity entity) {
            entity.world.playSound((PlayerEntity) entity,entity.getPosX(),entity.getPosY(),entity.getPosZ(), SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,entity.getSoundCategory(),0.1f,1.3f);
        }

        @Override
        public boolean canRightClickEquip() {
            return true;
        }

        @Override
        public boolean hasRender(String identifier, LivingEntity livingEntity) {
            return false;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            EntityRenderer<?> renderer = Minecraft.getInstance().getRenderManager().getRenderer(livingEntity);
            if (!(renderer instanceof IEntityRenderer<?, ?>)) {
                return;
            }
            EntityModel<?> model = ((IEntityRenderer<?, ?>) renderer).getEntityModel();
            if (!(model instanceof BipedModel<?>)) {
                return;
            }
        }

    }
}
