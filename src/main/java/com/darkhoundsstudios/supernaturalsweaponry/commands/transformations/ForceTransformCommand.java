package com.darkhoundsstudios.supernaturalsweaponry.commands.transformations;

import com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations.Transformation;
import com.darkhoundsstudios.supernaturalsweaponry.events.ClientEventBus;
import com.darkhoundsstudios.supernaturalsweaponry.events.ModEventBusEvents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ForceTransformCommand {
    public ForceTransformCommand(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> transformationCommand = Commands.literal("transformation")
                .then(Commands.literal("become")
                        .then(Commands.literal("werewolf")
                                .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                                .executes((commandContext) -> BecomeTransformation("werewolf", commandContext))
                                )
                        )
                        .then(Commands.literal("human")
                                .executes((command) -> BecomeHuman())
                        )
                );
        dispatcher.register(transformationCommand);
    }


    public int BecomeTransformation(String transformation, CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        int level = 0;
        if(IntegerArgumentType.getInteger(commandContext,"level") != 0)
            level = IntegerArgumentType.getInteger(commandContext,"level");
        System.out.println("level: " + level);
        switch (transformation) {
            case "werewolf":
                ModEventBusEvents.getPlayer().setTransformation(new Transformation("Werewolf", level));
                break;
            case "human":
                ModEventBusEvents.getPlayer().setTransformation(null);
                break;
            default:
                break;
        }
        return 1;
    }

    public int BecomeHuman(){
        ModEventBusEvents.getPlayer().setTransformation(null);
        return 1;
    }
}
