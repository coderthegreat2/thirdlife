package com.c0d3r_.thirdlife.events;

import com.c0d3r_.thirdlife.utils.DeathCounter;
import com.c0d3r_.thirdlife.utils.SemiHardcore;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;


public final class Events {
    private Events() {}

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;

            int deaths = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));

            MutableText multiple = Text.literal("You have " + (3 - deaths) + " lives remaining");
            MutableText last =Text.literal("You have 1 life remaining. You can now kill other players.");

            if (deaths < 2) {
                player.sendMessage(multiple);
            } else {
                player.sendMessage(last);
            }

            SemiHardcore.checkDeaths(player);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            int deaths = DeathCounter.getDeaths(player);
            MutableText multiple = Text.literal("You have " + (3 - deaths) + " lives remaining");
            MutableText last =Text.literal("You have 1 life remaining. You can now kill other players.");

            if (deaths < 2) {
                player.sendMessage(multiple);
            } else {
                player.sendMessage(last);
            }
        });
    }
}
