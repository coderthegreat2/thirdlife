package com.c0d3r_.thirdlife.utils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class SemiHardcore {
    public static void checkDeaths(ServerPlayerEntity player) {
        int priorDeaths = DeathInfo.getDeaths(player);
        if (priorDeaths == 3) {
            forceDropAndWipeInventory(player);
            BanUtil.banForever(player, "Out of lives...");
        }
    }

    private static void forceDropAndWipeInventory(ServerPlayerEntity player) {
        PlayerInventory inv = player.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                player.dropItem(stack.copy(), true, false);
            }
        }
        inv.clear();
    }
}