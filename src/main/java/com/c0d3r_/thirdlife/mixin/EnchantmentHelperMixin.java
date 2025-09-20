package com.c0d3r_.thirdlife.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EnchantableComponent;
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
    private static void bookshelfNerf(Random random, int slotIndex, int bookshelfCount,
                                               ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int result;
        EnchantableComponent enchantableComponent = (EnchantableComponent)stack.get(DataComponentTypes.ENCHANTABLE);

        if (enchantableComponent == null) {
            result = 0;
        } else {
            int i = random.nextInt(8) + 1 + random.nextInt(1);
            if (slotIndex == 0) {
                result = Math.max(i / 3, 1);
            } else {
                result = slotIndex == 1 ? i * 2 / 3 + 1 : Math.max(i, 0);
            }
        }
        cir.setReturnValue(result);
    }
}
