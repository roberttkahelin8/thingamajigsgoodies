package net.rk.addon.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class Utilities{
    public static Holder<Enchantment> getEnchantmentHolderFromKeyStatic(Level lvl, ResourceKey<Enchantment> enchantmentResourceKey){
        return lvl.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantmentResourceKey);
    }

    public Holder<Enchantment> getEnchantmentHolderFromKey(Level lvl, ResourceKey<Enchantment> enchantmentResourceKey){
        return lvl.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(enchantmentResourceKey);
    }
}
