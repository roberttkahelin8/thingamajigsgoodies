package net.rk.addon.datagen.custom;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CombinerRecipeBuilder implements RecipeBuilder{
    @Nullable
    private String group1;

    private final Ingredient firstIngredient1;
    private final Ingredient secondIngredient1;
    private final Ingredient thirdIngredient1;
    private final Ingredient fourthIngredient1;
    private final Item outputItem1;
    private final NonNullList<Ingredient> ings1 = NonNullList.create();
    private final Map<String, Criterion<?>> criterion1 = new LinkedHashMap<>();

    public CombinerRecipeBuilder(Ingredient f1, Ingredient f2, Ingredient f3, Ingredient f4, Item output1){
        this.firstIngredient1 = f1;
        this.secondIngredient1 = f2;
        this.thirdIngredient1 = f3;
        this.fourthIngredient1 = f4;
        this.outputItem1 = output1;
    }

    public static CombinerRecipeBuilder combining(Ingredient firstIng1, Ingredient secondIng2, Ingredient thirdIng3, Ingredient fourthIng4, Item output5) {
        return new CombinerRecipeBuilder(firstIng1, secondIng2, thirdIng3, fourthIng4, output5);
    }

    // many options for what this combiner recipe requires to be unlocked
    public CombinerRecipeBuilder requires(TagKey<Item> taggedItem) {
        return this.requires(Ingredient.of(taggedItem));
    }

    public CombinerRecipeBuilder requires(ItemLike itemLike) {
        return this.requires(itemLike, 1);
    }

    public CombinerRecipeBuilder requires(ItemLike itemLike, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.requires(Ingredient.of(itemLike));
        }
        return this;
    }

    public CombinerRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public CombinerRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            this.ings1.add(ingredient);
        }
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.criterion1.put(criterionName,criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        this.group1 = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.outputItem1;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        this.isYouDumb(resourceLocation);

        Advancement.Builder advancement$builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criterion1.forEach(advancement$builder::addCriterion);

        CombinerRecipe combiningrec1 = new CombinerRecipe(
                this.firstIngredient1,
                this.secondIngredient1,
                this.thirdIngredient1,
                this.fourthIngredient1,
                new ItemStack(this.outputItem1)
        );

        recipeOutput.accept(resourceLocation,
                combiningrec1,
                advancement$builder.build(resourceLocation
                        .withPrefix("recipes/" + RecipeCategory.MISC.getFolderName() + "/")));
    }

    private void isYouDumb(ResourceLocation rs1){
        if(this.criterion1.isEmpty()){
            throw new IllegalStateException("TGoodies cannot make a recipe as it is unobtainable without a criterion specified properly. Recipe Is: " + rs1);
        }
    }
}
