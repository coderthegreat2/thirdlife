package com.c0d3r_.thirdlife.event;

import com.c0d3r_.thirdlife.util.NameColor;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;


public final class Events {
    private Events() {}

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;

            LifeManager.checkOutOfLives(player);
            NameColor.nameColorUtil(player);
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            LifeManager.joinMessage(player);
            NameColor.nameColorUtil(player);
        });
    }
}
