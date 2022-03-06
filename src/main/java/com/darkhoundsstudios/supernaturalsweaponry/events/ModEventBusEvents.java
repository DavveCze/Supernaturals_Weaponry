package com.darkhoundsstudios.supernaturalsweaponry.events;

import com.darkhoundsstudios.supernaturalsweaponry.SupernaturalWeaponry;
import com.darkhoundsstudios.supernaturalsweaponry.commands.transformations.ForceTransformCommand;
import com.darkhoundsstudios.supernaturalsweaponry.entities.player.ModPlayerEntity;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = SupernaturalWeaponry.Mod_ID)
public class ModEventBusEvents {
    //ukládá dvě instance hráče(ModPlayerEntity a ServerPlayerEntity)
    private static ModPlayerEntity player;
    private static ServerPlayerEntity serverPlayer;

    //vrací instance hráče
    public static ModPlayerEntity getPlayer(){
        return player;
    }

    public static ServerPlayerEntity getServerPlayer(){return serverPlayer;}

    //registruje nové commandy
    @SubscribeEvent
    public static void onCommandsRegister(FMLServerStartingEvent event) {
        new ForceTransformCommand(event.getCommandDispatcher());
        ConfigCommand.register(event.getCommandDispatcher());
    }

    //při načtení světa a hráče do něj, se načtou uložené informace a vytvoří se instance
    @SubscribeEvent
    public static void onPlayerLoadIn(PlayerEvent.PlayerLoggedInEvent event) throws CommandSyntaxException {
        player = new ModPlayerEntity(event.getPlayer());
        Entity x = event.getEntity();
        serverPlayer = x.getCommandSource().asPlayer();
        player.readPlayerData(event.getPlayer());
        player.initPlayer(false);
    }

    //při ukončení světa se uloží informace do hráčových metadat
    @SubscribeEvent
    public static void onPlayerLoadOut(PlayerEvent.PlayerLoggedOutEvent event){
        player.writePlayerData(event.getPlayer());
    }

    //při oživení se děje to samé jako u načtení světa
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) throws CommandSyntaxException {
        player.writePlayerData(event.getPlayer());
        player = new ModPlayerEntity(event.getPlayer());
        Entity x = event.getEntity();
        serverPlayer = x.getCommandSource().asPlayer();
        player.readPlayerData(event.getPlayer());
        player.initPlayer(false);
    }

    //vyvolává custom tick pro hráče, neboť on ho sám vyvolat nemůže
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            player.playerTick();
        }
    }

    //při změně zkušeností hráče se mu přičtou i zkušenosti do transformace
    @SubscribeEvent
    public static void onPlayerXpChange(PlayerXpEvent.XpChange event){
        player.giveExperiencePoints(event.getAmount());
    }
}

