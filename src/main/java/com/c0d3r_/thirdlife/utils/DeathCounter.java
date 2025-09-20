package com.c0d3r_.thirdlife.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public final class DeathCounter {
    public static int getDeaths(ServerPlayerEntity player) {
        return player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
    }
}