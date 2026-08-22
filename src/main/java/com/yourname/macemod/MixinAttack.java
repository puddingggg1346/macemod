package com.yourname.macemod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinAttack {
    @Inject(method = "attack", at = @At("HEAD"))
    private void onAttack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (!MaceMod.killAuraEnabled) return;
        ItemStack stack = player.getMainHandStack();
        if (!stack.isOf(Items.MACE)) return;

        World world = player.getWorld();
        if (world.isClient) return;

        // 连点: 强制攻击冷却为0
        player.resetCooldown();

        // 杀戮光环: 伤害附近生物
        double range = 5.0;
        Box box = player.getBoundingBox().expand(range);
        world.getEntitiesByClass(LivingEntity.class, box, e -> e != player && e.isAlive())
            .forEach(e -> {
                if (e instanceof ServerPlayerEntity) return;
                e.damage(DamageSource.player(player), 6.0f);
                e.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2));
            });
    }
}
