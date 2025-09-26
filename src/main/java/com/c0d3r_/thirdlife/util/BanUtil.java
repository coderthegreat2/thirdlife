package com.c0d3r_.thirdlife.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.util.Date;

public final class BanUtil {
    private BanUtil() {}

    public static void ban(ServerPlayerEntity player, String reason) {
        player.changeGameMode(GameMode.SPECTATOR);
        MinecraftServer server = player.getServer();
        assert server != null;
        var bans = server.getPlayerManager().getUserBanList();
        var entry = new BannedPlayerEntry(
                player.getGameProfile(),
                new Date(),
                "ThirdLIfe",
                null,
                reason == null ? "" : reason
        );

        bans.add(entry);

        player.networkHandler.disconnect(Text.literal(
                "You are out of lives..."
        ));
    }
}
