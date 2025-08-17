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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import oshi.util.tuples.Quartet;

import java.util.Iterator;
import java.util.Map;

public class ParryAttributesCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(
                CommandManager.literal("parryAttributes")
                        .requires(src -> !dedicated || src.hasPermissionLevel(2))
                        .executes(ctx -> {
                            var src = ctx.getSource();
                            var player = src.getPlayer();
                            return execute(src, player);
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

                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.title",
                        player.getMainHandStack().getName()), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.separator"), false);

                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.stable",
                        !attr.shouldStopUsingItemAfterBlockOrParry()), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.parry_ticks",
                        parryTicks), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.block_delay",
                        blockDelay), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.explosion_block_delay",
                        attr.getExplosionBlockDelay()), false);

                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.melee_protection",
                        (1.0F - attr.getMeleeDamageTakenAfterBlock()) * 100), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.projectile_protection",
                        (1.0F - attr.getProjectileDamageTakenAfterBlock()) * 100), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.explosion_protection",
                        (1.0F - attr.getExplosionDamageTakenAfterBlock()) * 100), false);

                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.base_cooldown",
                        getCooldown(player, attr.getCooldownAfterInterruptingBlockAction()) / 20), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.cooldown_after_parry",
                        getCooldown(player, attr.getCooldownAfterParryAction()) / 20), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.cooldown_after_attack",
                        getCooldown(player, attr.getCooldownAfterAttack()) / 20), false);

                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.knockback",
                        attr.getKnockbackAfterParryAction()), false);
                source.sendFeedback(new TranslatableText("command.frycparry.parry_attributes.effects"), false);

                Iterator<Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>>> iterator =
                        attr.getParryEffectsIterator();
                while (iterator.hasNext()) {
                    Map.Entry<StatusEffect, Quartet<Integer, Integer, Float, Float>> entry = iterator.next();
                    source.sendFeedback(
                            new TranslatableText("command.frycparry.parry_attributes.effect_entry",
                                    entry.getKey().getName(),
                                    entry.getValue().getA(),
                                    entry.getValue().getB(),
                                    entry.getValue().getC(),
                                    entry.getValue().getD()
                            ).formatted(Formatting.AQUA),
                            false
                    );
                }
            } else {
                source.sendFeedback(new TranslatableText("command.frycparry.error.empty_hand")
                        .formatted(Formatting.RED), false);
            }
        } else {
            source.sendFeedback(new TranslatableText("command.frycparry.error.no_target")
                    .formatted(Formatting.RED), false);
        }

        return 0;
    }

    private static float getCooldown(PlayerEntity player, float cooldown) {
        return cooldown < 0 ? (player.getAttackCooldownProgressPerTick() - 1) * Math.abs(cooldown) : cooldown;
    }
}
