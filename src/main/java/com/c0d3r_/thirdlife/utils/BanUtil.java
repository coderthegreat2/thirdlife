package com.c0d3r_.thirdlife.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.time.Duration;
import java.util.Date;

public final class BanUtil {
    private BanUtil() {}

    public static void banForever(ServerPlayerEntity player, String reason) {
        MinecraftServer server = player.getServer();
        var bans = server.getPlayerManager().getUserBanList();

        var entry = new BannedPlayerEntry(
                player.getGameProfile(),
                new Date(),
                "ThirdLIfe",
                /*expires*/ null,
                reason == null ? "" : reason
        );

        bans.add(entry);
        player.networkHandler.disconnect(Text.literal(
                "You are out of lives..."
        ));
    }
    public static void banFor(ServerPlayerEntity player, Duration duration, String reason) {
        MinecraftServer server = player.getServer();
        var bans = server.getPlayerManager().getUserBanList();

        var expiresAt = new Date(System.currentTimeMillis() + duration.toMillis());
        var entry = new BannedPlayerEntry(
                player.getGameProfile(),
                new Date(),
                "YourMod",
                expiresAt,
                reason == null ? "" : reason
        );

        bans.add(entry);
        player.networkHandler.disconnect(Text.literal(
                "You have been temporarily banned until " + expiresAt +
                        (reason == null || reason.isBlank() ? "" : (": " + reason))
        ));
    }
}
