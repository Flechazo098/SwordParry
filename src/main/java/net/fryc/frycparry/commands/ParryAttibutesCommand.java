package net.fryc.frycparry.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fryc.frycparry.attributes.ParryAttributes;
import net.fryc.frycparry.enchantments.ModEnchantments;
import net.fryc.frycparry.util.interfaces.ParryItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import oshi.util.tuples.Quartet;

import java.util.Iterator;
import java.util.Map;

public class ParryAttibutesCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(
                CommandManager.literal("parryAttributes")
                        .requires(src -> !dedicated || src.hasPermissionLevel(2))
                        .executes(ctx -> {
                            ServerCommandSource src = ctx.getSource();
                            Entity entity = src.getEntity();
                            if (entity == null) {
                                src.sendError(new LiteralText("This command must be executed by a player."));
                                return 0;
                            }
                            return execute(src, entity);
                        })
        );
    }

    private static int execute(ServerCommandSource source, Entity target) {
        if (target instanceof ServerPlayerEntity player) {
            if (!player.getMainHandStack().isEmpty()) {
                int reflexLevel = EnchantmentHelper.getLevel(ModEnchantments.REFLEX, player.getMainHandStack());
                ParryAttributes attr = ((ParryItem) player.getMainHandStack().getItem()).frycparry_getParryAttributes();
                int blockDelay = attr.getBlockDelay() - reflexLevel;
                int parryTicks = blockDelay < 0 ? attr.getParryTicks() + Math.abs(blockDelay) : attr.getParryTicks();
                blockDelay = Math.max(blockDelay, 0);

                player.sendMessage(new LiteralText(player.getMainHandStack().getName().getString() + " parry attributes:"), false);
                player.sendMessage(new LiteralText("======   ======"), false);
                player.sendMessage(new LiteralText("Stable: " + !attr.shouldStopUsingItemAfterBlockOrParry()), false);
                player.sendMessage(new LiteralText("Parry ticks: " + parryTicks), false);
                player.sendMessage(new LiteralText("Block delay: " + blockDelay), false);
                player.sendMessage(new LiteralText("Explosion block delay: " + attr.getExplosionBlockDelay()), false);

                player.sendMessage(new LiteralText("Melee damage protection: " + (1.0F - attr.getMeleeDamageTakenAfterBlock()) * 100 + "%"), false);
                player.sendMessage(new LiteralText("Projectile damage protection: " + (1.0F - attr.getProjectileDamageTakenAfterBlock()) * 100 + "%"), false);
                player.sendMessage(new LiteralText("Explosion damage protection: " + (1.0F - attr.getExplosionDamageTakenAfterBlock()) * 100 + "%"), false);

                player.sendMessage(new LiteralText("Base cooldown: " + getCooldown(player, attr.getCooldownAfterInterruptingBlockAction()) / 20 + "s"), false);
                player.sendMessage(new LiteralText("Cooldown after parry: " + getCooldown(player, attr.getCooldownAfterParryAction()) / 20 + "s"), false);
                player.sendMessage(new LiteralText("Cooldown after attack: " + getCooldown(player, attr.getCooldownAfterAttack()) / 20 + "s"), false);

                player.sendMessage(new LiteralText("Parry knockback: " + attr.getKnockbackAfterParryAction()), false);
                player.sendMessage(new LiteralText("Parry effects:"), false);
                Iterator<Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>>> iterator = attr.getParryEffectsIterator();
                while (iterator.hasNext()) {
                    Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>> entry = iterator.next();
                    player.sendMessage(
                            new LiteralText(
                                    "  " + entry.getKey().getName().getString() +
                                            ": Duration - " + entry.getValue().getA() +
                                            " Amplifier - " + entry.getValue().getB() +
                                            " Chance -  " + entry.getValue().getC() +
                                            " Enchantment modifier - " + entry.getValue().getD()
                            ).formatted(Formatting.AQUA), false
                    );
                }
            } else {
                source.sendFeedback(new LiteralText("Your main hand is empty!").formatted(Formatting.RED), false);
            }
        } else {
            source.sendFeedback(new LiteralText("Unable to execute command").formatted(Formatting.RED), false);
        }

        return 0;
    }

    private static float getCooldown(PlayerEntity player, float cooldown) {
        return cooldown < 0 ? (player.getAttackCooldownProgressPerTick() - 1) * Math.abs(cooldown) : cooldown;
    }
}