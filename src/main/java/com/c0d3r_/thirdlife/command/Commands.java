package com.c0d3r_.thirdlife.command;

import com.c0d3r_.thirdlife.util.NameColor;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;

public final class Commands {
    private Commands() {}

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // /setdeaths <player> <value>
        dispatcher.register(
            CommandManager.literal("setdeaths")
                .requires(src -> src.hasPermissionLevel(2))
                .then(
                    CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("value", IntegerArgumentType.integer(0))
                            .executes(ctx -> {
                                ServerCommandSource source = ctx.getSource();
                                ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                                int value = IntegerArgumentType.getInteger(ctx, "value");

                                Stat<?> deathsStat = Stats.CUSTOM.getOrCreateStat(Stats.DEATHS);
                                target.getStatHandler().setStat(target, deathsStat, value);

                                source.sendFeedback(() ->
                                        Text.literal("Set " + target.getName().getString() + "'s deaths to " + value), true);
                                target.sendMessage(Text.literal("Your death count was set to " + value));
                                NameColor.nameColorUtil(target);
                                return Command.SINGLE_SUCCESS;
                            })
                        )
                )
        );

        // /deaths [player]
        dispatcher.register(
            CommandManager.literal("deaths")
                .requires(src -> src.hasPermissionLevel(0))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                    .executes(ctx -> {
                        ServerCommandSource source = ctx.getSource();
                        ServerPlayerEntity target = EntityArgumentType.getPlayer(ctx, "player");
                        Stat<?> deathsStat = Stats.CUSTOM.getOrCreateStat(Stats.DEATHS);
                        int count = target.getStatHandler().getStat(deathsStat);

                        source.sendFeedback(() ->
                                Text.literal(target.getName().getString() + " has died " + count + (count == 1 ? " time." : " times.")), false);
                        return Command.SINGLE_SUCCESS;
                    })
                )
                .executes(ctx -> {
                    ServerCommandSource source = ctx.getSource();
                    ServerPlayerEntity self = source.getPlayer();
                    Stat<?> deathsStat = Stats.CUSTOM.getOrCreateStat(Stats.DEATHS);
                    assert self != null;
                    int count = self.getStatHandler().getStat(deathsStat);

                    source.sendFeedback(() ->
                            Text.literal("You have died " + count + (count == 1 ? " time." : " times.")), false);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
}
