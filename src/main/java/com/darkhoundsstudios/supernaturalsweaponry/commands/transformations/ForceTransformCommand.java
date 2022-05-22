package com.darkhoundsstudios.supernaturalsweaponry.commands.transformations;

import com.darkhoundsstudios.supernaturalsweaponry.entities.player.transformations.Skills;
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

//ovlada vsechny commandy, ktere mod pridava
public class ForceTransformCommand {
    public ForceTransformCommand(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> transformationCommand = Commands.literal("transformation")
                .then(Commands.literal("become")
                        .then(Commands.literal("werewolf")
                                .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                                        .executes((commandContext) -> BecomeTransformation("werewolf", commandContext))
                                )
                                .executes((commandContext) -> BecomeTransformation("werewolf", null))
                        )
                        .then(Commands.literal("human")
                                .executes((command) -> BecomeHuman())
                        )
                )
                .then(Commands.literal("add")
                        .then(Commands.literal("skill")
                                .then(Commands.argument("id", IntegerArgumentType.integer(0, Skills.Werewolf_Skills.skill_list.size()))
                                        .executes((this::AddSkill))
                                )
                        )
                        .then(Commands.literal("skillpoint")
                        )
                        .then(Commands.literal("xp")
                        )
                );
        dispatcher.register(transformationCommand);
    }


    public int BecomeTransformation(String transformation, CommandContext<CommandSource> commandContext) throws CommandSyntaxException {
        int level = 0;
        if(commandContext == null)
            level = 1;
        else if (IntegerArgumentType.getInteger(commandContext,"level") != 0)
            level = IntegerArgumentType.getInteger(commandContext,"level");
        System.out.println("level: " + level);
        switch (transformation) {
            case "werewolf":
                ModEventBusEvents.getPlayer().setTransformation(new Transformation(ModEventBusEvents.getPlayer(), "Werewolf", level));
                break;
            case "vampire":
                ModEventBusEvents.getPlayer().setTransformation(new Transformation(ModEventBusEvents.getPlayer(), "Vampire", level));
                break;
            case "hunter":
                ModEventBusEvents.getPlayer().setTransformation(new Transformation(ModEventBusEvents.getPlayer(), "Hunter", level));
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

    public int AddSkill(CommandContext<CommandSource> commandContext){
        if(commandContext != null) {
            int skill_id = IntegerArgumentType.getInteger(commandContext, "id");
            if (ModEventBusEvents.getPlayer().transformation.tryAddSkill(Skills.Werewolf_Skills.skill_list.get(skill_id))) {
                ModEventBusEvents.getPlayer().transformation.addSkill(Skills.Werewolf_Skills.skill_list.get(skill_id),Skills.Werewolf_Skills.skill_list.get(skill_id).parameters().get(0),Skills.Werewolf_Skills.skill_list.get(skill_id).parameters().get(1));
            }
        }
        return 1;
    }
}
