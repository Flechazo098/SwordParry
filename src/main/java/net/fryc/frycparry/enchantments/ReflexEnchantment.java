package net.fryc.frycparry.enchantments;

import net.fryc.frycparry.tag.ModItemTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ReflexEnchantment extends Enchantment {
    public ReflexEnchantment() {
        super(Enchantment.Rarity.COMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level - 1) * 8;
    }

    @Override
    public int getMaxPower(int level) {
        return 55 + (level - 1) * 8;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasure() {
        return super.isTreasure();
    }

    @Override
    public boolean isCursed() {
        return super.isCursed();
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return super.isAvailableForEnchantedBookOffer();
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return super.isAvailableForRandomSelection();
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isIn(ModItemTags.ENCHANTMENT_PARRY_TOOLS) || stack.isIn(ModItemTags.ENCHANTMENT_SHIELDS);
    }
}