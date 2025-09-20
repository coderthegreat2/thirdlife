package com.c0d3r_.thirdlife.events;

import com.c0d3r_.thirdlife.utils.DeathCounter;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;


public final class Events {
    private Events() {}

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;

            SemihardcoreManager.checkDeaths(player);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            SemihardcoreManager.joinMessage(player);
        });
    }
}
