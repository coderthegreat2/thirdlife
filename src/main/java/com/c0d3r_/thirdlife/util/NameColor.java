package com.c0d3r_.thirdlife.util;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Formatting;

public class NameColor {
    public static void setNameColor(ServerPlayerEntity player, Formatting color) {
        if (player == null || color == null || !color.isColor()) return;

        Scoreboard sb = player.getScoreboard();
        String holder = player.getGameProfile().getName();

        String teamName = "color_" + color.getName();
        Team target = sb.getTeam(teamName);
        if (target == null) {
            target = sb.addTeam(teamName);
            target.setColor(color);
        } else if (target.getColor() != color) {
            target.setColor(color);
        }

        Team current = player.getScoreboardTeam();

        if (current == target) return;

        if (current != null) {
            sb.removeScoreHolderFromTeam(holder, current);
        }
        if (!target.getPlayerList().contains(holder)) {
            sb.addScoreHolderToTeam(holder, target);
        }
    }

    public static void nameColorUtil(ServerPlayerEntity player) {
        int deaths = DeathCounter.getDeaths(player);

        if(deaths == 0){
            setNameColor(player, Formatting.GREEN);
        } else if (deaths == 1) {
            setNameColor(player, Formatting.YELLOW);
        } else {
            setNameColor(player, Formatting.RED);
        }
    }
}
