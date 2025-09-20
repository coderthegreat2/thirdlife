package com.c0d3r_.thirdlife.mixin;

import net.minecraft.entity.mob.ZombieVillagerEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieVillagerEntity.class)
public abstract class ZombieVillagerEntityMixin {
    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void injected(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        cir.setReturnValue(ActionResult.CONSUME);
    }
}