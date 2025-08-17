package net.fryc.frycparry.enchantments;

import net.fryc.frycparry.FrycParry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEnchantments {

    public static final Enchantment REFLEX = new ReflexEnchantment();
    public static final Enchantment COUNTERATTACK = new CounterattackEnchantment();
    public static final Enchantment PARRY = new ParryEnchantment();

    public static void registerModEnchantments() {
        Registry.register(Registry.ENCHANTMENT, new Identifier(FrycParry.MOD_ID, "reflex"), REFLEX);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FrycParry.MOD_ID, "counterattack"), COUNTERATTACK);
        Registry.register(Registry.ENCHANTMENT, new Identifier(FrycParry.MOD_ID, "parry"), PARRY);
    }

    public static int getParryEnchantment(LivingEntity entity) {
        return EnchantmentHelper.getLevel(PARRY, entity.getMainHandStack()) + EnchantmentHelper.getLevel(PARRY, entity.getOffHandStack());
    }

    public static int getReflexEnchantment(LivingEntity entity) {
        return EnchantmentHelper.getLevel(REFLEX, entity.getMainHandStack()) + EnchantmentHelper.getLevel(REFLEX, entity.getOffHandStack());
    }

    public static int getCounterattackEnchantment(LivingEntity entity) {
        return EnchantmentHelper.getLevel(COUNTERATTACK, entity.getMainHandStack()) + EnchantmentHelper.getLevel(COUNTERATTACK, entity.getOffHandStack());
    }

    public static int getKnockbackEnchantment(LivingEntity entity) {
        return EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, entity.getMainHandStack()) + EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, entity.getOffHandStack());
    }
}