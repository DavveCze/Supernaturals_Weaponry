package com.darkhoundsstudios.supernaturalsweaponry.events;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.commands.transformations.ForceTransformCommand;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID)
public class ModEventBusEvents {
    private static ModPlayerEntity player;
    private static ServerPlayerEntity serverPlayer;

    public static ModPlayerEntity getPlayer(){
        return player;
    }

    public static ServerPlayerEntity getServerPlayer(){return serverPlayer;}

    @SubscribeEvent
    public static void onCommandsRegister(FMLServerStartingEvent event) {
        new ForceTransformCommand(event.getCommandDispatcher());
        ConfigCommand.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException {
        player = new ModPlayerEntity(event.getPlayer());
        player.readPlayerData(event.getPlayer());

        serverPlayer = event.getEntity().getCommandSource().asPlayer();
    }



    @SubscribeEvent
    public static void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event){
        player.writePlayerData(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) throws CommandSyntaxException {
        player.writePlayerData(event.getPlayer());
        player = new ModPlayerEntity(event.getPlayer());
        player.readPlayerData(event.getPlayer());
        serverPlayer = event.getEntity().getCommandSource().asPlayer();
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            player.playerTick();
        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event){
        player.giveExperiencePoints(event.getAmount());
    }
}

