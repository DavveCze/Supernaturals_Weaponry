package com.darkhoundsstudios.supernaturalsweaponry.events;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.MoonPhaseTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.advancements.triggers.TransformIntoTrigger;
import com.darkhoundsstudios.supernaturalsweaponry.commands.transformations.ForceTransformCommand;
import com.darkhoundsstudios.supernaturalsweaponry.entities.entity.werewolf.wolf.Werewolf_Wolf;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations.Skills;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations.Transformation;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.advancements.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
        Skills.Werewolf_Skills.init();
        player = new ModPlayerEntity(event.getEntity().getCommandSource().asPlayer());
        getPlayer().readPlayerData(event.getEntity().getCommandSource().asPlayer());
        getPlayer().initPlayer(false);
        serverPlayer = event.getEntity().getCommandSource().asPlayer();
    }
    @SubscribeEvent
    public static void onPlayerEat(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().isFood()) {
            if (event.getEntity() instanceof PlayerEntity) {
                if (getPlayer().transformation != null) {
                    if (Objects.equals(getPlayer().transformation.getTransType(), "Werewolf")) {
                        if (Objects.requireNonNull(event.getItem().getItem().getFood()).isMeat()) {
                            ItemStack stack = event.getItem();
                            if (!event.getItem().toString().contains("Cooked")) {
                                ((PlayerEntity) event.getEntity()).getFoodStats().consume(stack.getItem(), stack);
                            }
                        } else
                            ((PlayerEntity) event.getEntity()).getFoodStats().setFoodLevel(((PlayerEntity) event.getEntity()).getFoodStats().getFoodLevel());

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (getPlayer() != null)
            getPlayer().writePlayerData((PlayerEntity) event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) throws CommandSyntaxException {
        getPlayer().writePlayerData(event.getEntity().getCommandSource().asPlayer());
        player = new ModPlayerEntity(event.getEntity().getCommandSource().asPlayer());
        getPlayer().readPlayerData(event.getEntity().getCommandSource().asPlayer());
        getPlayer().initPlayer(false);
        serverPlayer = event.getEntity().getCommandSource().asPlayer();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (getPlayer() != null)
                getPlayer().playerTick();
        }
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            MoonPhaseTrigger.MoonPhase totPhase;
            switch (event.player.world.getMoonPhase()) {
                case 0:
                    totPhase = MoonPhaseTrigger.MoonPhase.FULL_MOON;
                    break;
                case 1:
                    totPhase = MoonPhaseTrigger.MoonPhase.WANING_GIBBOUS;
                    break;
                case 2:
                    totPhase = MoonPhaseTrigger.MoonPhase.THIRD_QUARTER;
                    break;
                case 3:
                    totPhase = MoonPhaseTrigger.MoonPhase.WANING_CRESCENT;
                    break;
                case 4:
                    totPhase = MoonPhaseTrigger.MoonPhase.NEW_MOON;
                    break;
                case 5:
                    totPhase = MoonPhaseTrigger.MoonPhase.WAXING_CRESCENT;
                    break;
                case 6:
                    totPhase = MoonPhaseTrigger.MoonPhase.FIRST_QUARTER;
                    break;
                case 7:
                    totPhase = MoonPhaseTrigger.MoonPhase.WAXING_GIBBOUS;
                    break;
                default:
                    totPhase = MoonPhaseTrigger.MoonPhase.UNKNOWN;
            }
            if(getPlayer().world.getDayTime() % 24000 > 12500)
                SupernaturalWeaponry.Advancements.MOON_PHASE.trigger((ServerPlayerEntity) event.player, totPhase);


            if (getPlayer().transformation != null && getPlayer().transformation.getState() != Transformation.TransformState.Human && getPlayer().world.getDayTime() % 24000 <= 12500 ) {
                getPlayer().Fullmoon(false, false);
            }
            else if (getPlayer().type == ModPlayerEntity.TransformationType.Werewolf && event.player.world.getMoonPhase() == 0 && getPlayer().world.getDayTime() % 24000 >12500) {
                if (getPlayer().transformation == null) {
                    getPlayer().transformation = new Transformation(event.player, "Werewolf", 1);
                    getPlayer().transformation.addBasicSkills();
                    getPlayer().Fullmoon(true, true);
                } else if ((getPlayer().transformation.getState() == Transformation.TransformState.Human))
                    getPlayer().Fullmoon(true, false);
            }
        }
        assert getPlayer() != null;
        if (getPlayer().type != null) {
            TransformIntoTrigger.Type totType;
            switch (getPlayer().type) {
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
            SupernaturalWeaponry.Advancements.TRANSFORM_INTO.trigger((ServerPlayerEntity) getPlayer().playerEntity, totType);
        }

    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        if (getPlayer() != null)
            getPlayer().giveExperiencePoints(event.getAmount());
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            if (((PlayerEntity) event.getEntity()).getLastAttackedEntity() instanceof Werewolf_Wolf & getPlayer().canTransformW & getPlayer().type == null) {
                if (Math.random() * 100 <= 5) {
                    getPlayer().type = ModPlayerEntity.TransformationType.Werewolf;
                    getPlayer().canTransformW = false;
                }
            }
            if(getPlayer().transformation != null) {
                if (getPlayer().transformation.getTransType().equals("Werewolf")) {
                    if (((PlayerEntity) event.getEntity()).getHealth() < ((PlayerEntity) event.getEntity()).getHealth() * 0.15)
                        getPlayer().transformation.useSkill(event.getEntityLiving(), Skills.Werewolf_Skills.Survival_instinct);
                    if (event.getSource() == DamageSource.WITHER | ((PlayerEntity) event.getEntity()).getActivePotionEffect(Effects.POISON) != null) {
                        getPlayer().transformation.useSkill(event.getEntityLiving(), Skills.Werewolf_Skills.Poison_Reduction_I);
                        getPlayer().transformation.useSkill(event.getEntityLiving(), Skills.Werewolf_Skills.Poison_Reduction_II);
                        getPlayer().transformation.useSkill(event.getEntityLiving(), Skills.Werewolf_Skills.Wither_Reduction);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event){
        if(event.getEntity() instanceof PlayerEntity) {
            if(getPlayer().transformation != null) {
                if (getPlayer().transformation.getTransType().equals("Werewolf")) {
                    getPlayer().transformation.useSkill(event.getEntityLiving(), Skills.Werewolf_Skills.Bloodlust);
                    getPlayer().transformation.useSkill((LivingEntity) event.getTarget(), Skills.Werewolf_Skills.Bleeding);
                    getPlayer().transformation.useSkill((LivingEntity) event.getTarget(), Skills.Werewolf_Skills.Rending_Slashes);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerKeyDown(InputEvent.KeyInputEvent event){
        if(event.getAction() == 1 & getPlayer() != null){
            if(getPlayer().transformation != null) {
                if (event.getKey() == 74 & (getPlayer().transformation.getState() == Transformation.TransformState.Human) & getPlayer().transformation.getLevel() >= 5) {
                    getPlayer().transformation.useSkill(getPlayer(), Skills.Werewolf_Skills.Wolf_form);
                    getPlayer().transformation.setState(Transformation.TransformState.Animal);
                } else if (event.getKey() == 74 & getPlayer().transformation.getLevel() >= 5) {
                    getPlayer().transformation.disableSkill(getPlayer(), Skills.Werewolf_Skills.Wolf_form);
                    getPlayer().transformation.setState(Transformation.TransformState.Human);
                } else if (event.getKey() == 74 & event.getModifiers() == 2 & (getPlayer().transformation.getState() == Transformation.TransformState.Human)) {
                    getPlayer().transformation.useSkill(getPlayer(), Skills.Werewolf_Skills.Dire_form);
                    getPlayer().transformation.setState(Transformation.TransformState.Beast);
                } else if (event.getKey() == 74 & event.getModifiers() == 2) {
                    getPlayer().transformation.disableSkill(getPlayer(), Skills.Werewolf_Skills.Dire_form);
                    getPlayer().transformation.setState(Transformation.TransformState.Human);
                }
                else if (event.getKey() == 72) {
                    getPlayer().transformation.useSkill(getPlayer(), Skills.Werewolf_Skills.Hyper_Armor);
                }
                else if (event.getKey() == 72 & event.getModifiers() == 2) {
                    getPlayer().transformation.useSkill(getPlayer(), Skills.Werewolf_Skills.Bestial_Rage);
                }
            }
        }
    }


    @SubscribeEvent
    public static void advancementCheck(AdvancementEvent event) {
        if (player != null) {
            boolean f1 = getPlayer().playerEntity == event.getPlayer();
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

                if (event.getAdvancement().getId().toString().equals("snweaponry:werewolf/root"))
                    getPlayer().canTransformW = true;
                else if (event.getAdvancement().getId().toString().equals("snweaponry:vampire/root"))
                    getPlayer().canTransformV = true;
                else if (event.getAdvancement().getId().toString().equals("snweaponry:hunter/root"))
                    getPlayer().canTransformH = true;
            }
        }
    }
}

