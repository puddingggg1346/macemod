package com.yourname.macemod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.item.Item;
import static net.minecraft.server.command.CommandManager.literal;

public class MaceMod implements ModInitializer {
    public static final String MOD_ID = "macemod";
    public static boolean killAuraEnabled = false;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("km")
                .then(literal("on").executes(ctx -> {
                    killAuraEnabled = true;
                    ctx.getSource().sendFeedback(() -> net.minecraft.text.Text.literal("杀戮光环开启"), false);
                    return 1;
                }))
                .then(literal("true").executes(ctx -> {
                    killAuraEnabled = true;
                    ctx.getSource().sendFeedback(() -> net.minecraft.text.Text.literal("杀戮光环开启"), false);
                    return 1;
                }))
                .then(literal("off").executes(ctx -> {
                    killAuraEnabled = false;
                    ctx.getSource().sendFeedback(() -> net.minecraft.text.Text.literal("杀戮光环关闭"), false);
                    return 1;
                }))
                .then(literal("false").executes(ctx -> {
                    killAuraEnabled = false;
                    ctx.getSource().sendFeedback(() -> net.minecraft.text.Text.literal("杀戮光环关闭"), false);
                    return 1;
                }))
            );
        });
    }
}
