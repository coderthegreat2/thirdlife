package com.c0d3r_.thirdlife.command;

import com.c0d3r_.thirdlife.util.DeathCounter;
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
import net.minecraft.text.MutableText;
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

        // /lives
        dispatcher.register(
                CommandManager.literal("lives")
                        .requires(src -> src.hasPermissionLevel(0))
                        .executes(ctx -> {
                            ServerCommandSource source = ctx.getSource();
                            ServerPlayerEntity player = source.getPlayer();
                            assert player != null;
                            int deaths = DeathCounter.getDeaths(player);
                            MutableText multiple = Text.literal("You have " + (3 - deaths) + " lives remaining");
                            MutableText last =Text.literal("You have 1 life remaining. You can now kill other players.");

                            if (deaths < 2) {
                                player.sendMessage(multiple);
                            } else {
                                player.sendMessage(last);
                            }
                            return Command.SINGLE_SUCCESS;
                        })
        );
    }
}
