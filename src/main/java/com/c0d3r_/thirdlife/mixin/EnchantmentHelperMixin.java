package com.c0d3r_.thirdlife.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(
            method = "calculateRequiredExperienceLevel",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void bookshelfcap$zeroPower(Random random, int slotIndex, int bookshelfCount,
                                               ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int base = random.nextInt(8) + 1;

        int result;
        if (slotIndex == 0) {
            result = Math.max(base / 3, 1);
        } else if (slotIndex == 1) {
            result = (base * 2) / 3 + 1;
        } else {
            result = base;
        }

        cir.setReturnValue(result);
    }
}
