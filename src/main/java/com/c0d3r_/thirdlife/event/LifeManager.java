package com.c0d3r_.thirdlife.event;

import com.c0d3r_.thirdlife.util.BanUtil;
import com.c0d3r_.thirdlife.util.DeathCounter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class LifeManager {
    public static void checkOutOfLives(ServerPlayerEntity player) {
        int deaths = DeathCounter.getDeaths(player);
        if (deaths == 3) {
            forceDropAndWipeInventory(player);
            BanUtil.ban(player, "Out of lives...");
        }else{
            deathMessage(player);
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

    public static void deathMessage(ServerPlayerEntity player){
        int deaths = DeathCounter.getDeaths(player);

        MutableText multiple = Text.literal("You have " + (3 - deaths) + " lives remaining");
        MutableText last =Text.literal("You have 1 life remaining. You can now kill other players.");

        if (deaths < 2) {
            player.sendMessage(multiple);
        } else {
            player.sendMessage(last);
        }
    }

    public static void joinMessage(ServerPlayerEntity player){
        int deaths = DeathCounter.getDeaths(player);
        MutableText multiple = Text.literal("You have " + (3 - deaths) + " lives remaining");
        MutableText last =Text.literal("You have 1 life remaining. You can now kill other players.");

        if (deaths < 2) {
            player.sendMessage(multiple);
        } else {
            player.sendMessage(last);
        }
    }
}