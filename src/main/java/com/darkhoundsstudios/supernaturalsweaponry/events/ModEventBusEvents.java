package com.darkhoundsstudios.supernaturalsweaponry.events;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.MoonPhaseTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.TransformIntoTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.commands.transformations.ForceTransformCommand;
import com.darkhoundsstudios.supernaturalsweaponry.entities.entity.werewolf.wolf.Werewolf_Wolf;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.advancements.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.server.command.ConfigCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID)
public class ModEventBusEvents {
    private static ModPlayerEntity player;
    private static ServerPlayerEntity serverPlayer;
    private static Method CriterionRegister;

    private static int prevMoonPhase;

    public static ModPlayerEntity getPlayer() {
        return player;
    }

    public static ServerPlayerEntity getServerPlayer() {
        return serverPlayer;
    }


    @SubscribeEvent
    public static void onCommandsRegister(FMLServerStartingEvent event) {
        new ForceTransformCommand(event.getCommandDispatcher());
        ConfigCommand.register(event.getCommandDispatcher());
    }

    public static <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
        if (CriterionRegister == null) {
            CriterionRegister = ObfuscationReflectionHelper.findMethod(CriteriaTriggers.class, "func_192118_a", ICriterionTrigger.class);
            CriterionRegister.setAccessible(true);
        }
        try {
            trigger = (ICriterionTrigger<T>) CriterionRegister.invoke(null, trigger);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("Failed to register trigger " + trigger.getId() + "!");
            e.printStackTrace();
        }
        return trigger;
    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException {
        player = new ModPlayerEntity(event.getEntity().getCommandSource().asPlayer());
        player.readPlayerData(event.getEntity().getCommandSource().asPlayer());
        serverPlayer = event.getEntity().getCommandSource().asPlayer();
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (player != null)
            player.writePlayerData((PlayerEntity) event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) throws CommandSyntaxException {
        player.writePlayerData(event.getEntity().getCommandSource().asPlayer());
        player = new ModPlayerEntity(event.getEntity().getCommandSource().asPlayer());
        player.readPlayerData(event.getEntity().getCommandSource().asPlayer());
        serverPlayer = event.getEntity().getCommandSource().asPlayer();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (player != null)
                player.playerTick();
        }
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            int phase = event.player.world.getMoonPhase();
            if (prevMoonPhase != phase) {
                MoonPhaseTrigger.MoonPhase totPhase;
                switch (phase) {
                    case 1:
                        totPhase = MoonPhaseTrigger.MoonPhase.FULL_MOON;
                        break;
                    case 2:
                        totPhase = MoonPhaseTrigger.MoonPhase.WANING_GIBBOUS;
                        break;
                    case 3:
                        totPhase = MoonPhaseTrigger.MoonPhase.THIRD_QUARTER;
                        break;
                    case 4:
                        totPhase = MoonPhaseTrigger.MoonPhase.WANING_CRESCENT;
                        break;
                    case 5:
                        totPhase = MoonPhaseTrigger.MoonPhase.NEW_MOON;
                        break;
                    case 6:
                        totPhase = MoonPhaseTrigger.MoonPhase.WAXING_CRESCENT;
                        break;
                    case 7:
                        totPhase = MoonPhaseTrigger.MoonPhase.FIRST_QUARTER;
                        break;
                    case 8:
                        totPhase = MoonPhaseTrigger.MoonPhase.WAXING_GIBBOUS;
                        break;
                    default:
                        totPhase = MoonPhaseTrigger.MoonPhase.UNKNOWN;
                }
                SupernaturalWeaponry.Advancements.MOON_PHASE.trigger((ServerPlayerEntity) event.player, totPhase);
                prevMoonPhase = phase;
            }
            if (player.type != null) {
                TransformIntoTrigger.Type totType;
                switch (player.type) {
                    case Werewolf:
                        totType = TransformIntoTrigger.Type.WEREWOLF;
                        break;
                    case Hunter:
                        totType = TransformIntoTrigger.Type.HUNTER;
                        break;
                    case Vampire:
                        totType = TransformIntoTrigger.Type.VAMPIRE;
                        break;
                    default:
                        totType = TransformIntoTrigger.Type.UNKNOWN;
                        break;
                }
                SupernaturalWeaponry.Advancements.TRANSFORM_INTO.trigger((ServerPlayerEntity) event.player, totType);
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        if (player != null)
            player.giveExperiencePoints(event.getAmount());
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event){
        if(event.getEntity() instanceof PlayerEntity) {
            if(Objects.requireNonNull(((PlayerEntity) event.getEntity()).getLastDamageSource()).getTrueSource() instanceof  Werewolf_Wolf)
                if(Math.random() * 100 <= 50 && getPlayer().canTransformW){
                    getPlayer().type = ModPlayerEntity.TransformationType.Werewolf;
                    getPlayer().canTransformW = false;
                    System.out.println("Transformed, into: " + getPlayer().type);
                }
        }
    }


    @SubscribeEvent
    public static void advancementCheck(AdvancementEvent event) {
        if (player != null) {
            boolean f1 = player.playerEntity == event.getPlayer();
            if (f1) {
                PlayerAdvancements advancements = ((ServerPlayerEntity) event.getPlayer()).getAdvancements();
                int doneCount = 0, progressed = 0;
                SupernaturalWeaponry.Advancements.PARENT_DONE.trigger((ServerPlayerEntity) event.getPlayer(), event.getAdvancement().getId().toString());
                if (event.getAdvancement().getParent() != null) {
                    for (Advancement advancement : event.getAdvancement().getParent().getChildren()) {
                        if (!advancements.getProgress(advancement).isDone() && !advancement.equals(event.getAdvancement()))
                            advancements.getProgress(advancement).revokeCriterion("children_off");
                    }
                }

                for (Advancement advancement : event.getAdvancement().getChildren()) {
                    if (advancements.getProgress(advancement).isDone())
                        doneCount++;
                    if (advancements.getProgress(advancement).getPercent() > (double) advancement.getRequirementCount() * 0.5d)
                        progressed++;
                }
                if (doneCount == 0 && progressed == 0) {
                    SupernaturalWeaponry.Advancements.CHILDREN_BLOCK.trigger((ServerPlayerEntity) event.getPlayer(), event.getAdvancement().getId().toString());
                }

                if(event.getAdvancement().getId().toString().equals("snweaponry:werewolf/root"))
                        player.canTransformW = true;
            }
        }
    }
}

