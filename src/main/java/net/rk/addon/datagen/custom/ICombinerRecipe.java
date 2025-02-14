package net.rk.addon.datagen.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.TGBlocks;

public interface ICombinerRecipe extends Recipe<CombinerRecipeInput> {
    default RecipeType<?> getType() {
        return Thingamajigsgoodies.COMBINING_RECIPE_TYPE.get();
    }

    default boolean canCraftInDimensions(int width, int height) {
        return width >= 4 && height >= 1;
    }

    default ItemStack getToastSymbol() {
        return new ItemStack(TGBlocks.COMBINER.asItem());
    }

    // ingredients
    boolean isFirstIngredient(ItemStack is1);
    boolean isSecondIngredient(ItemStack is2);
    boolean isThirdIngredient(ItemStack is3);
    boolean isFourthIngredient(ItemStack is4);
}
