package net.rk.addon.datagen.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.rk.addon.Thingamajigsgoodies;

import java.util.stream.Stream;

public class CombinerRecipe implements ICombinerRecipe{
    final Ingredient firstIng;
    final Ingredient secondIng;
    final Ingredient thirdIng;
    final Ingredient fourthIng;

    final ItemStack outputStack;

    public CombinerRecipe(Ingredient first, Ingredient second, Ingredient third, Ingredient fourth, ItemStack output){
        this.firstIng = first;
        this.secondIng = second;
        this.thirdIng = third;
        this.fourthIng = fourth;
        this.outputStack = output;
    }

    @Override
    public RecipeType<?> getType() {
        return Thingamajigsgoodies.COMBINING_RECIPE_TYPE.get();
    }

    @Override
    public boolean matches(CombinerRecipeInput input, Level level) {
        return this.firstIng.test(input.firstItemStack())
                && this.secondIng.test(input.secondItemStack())
                && this.thirdIng.test(input.thirdItemStack())
                && this.fourthIng.test(input.fourthItemStack());
    }

    @Override
    public ItemStack assemble(CombinerRecipeInput input, HolderLookup.Provider provider) {
        ItemStack hoho1 = input.firstItemStack();
        ItemStack hoho2 = input.secondItemStack();
        ItemStack hoho3 = input.thirdItemStack();
        ItemStack hoho4 = input.fourthItemStack();

        if(hoho1.isEmpty()){
            return ItemStack.EMPTY;
        }
        else{
            if(hoho2.isEmpty()){
                return ItemStack.EMPTY;
            }
            else{
                if(hoho3.isEmpty()){
                    return ItemStack.EMPTY;
                }
                else{
                    if(hoho4.isEmpty()){
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        return getResultItem(provider);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.outputStack.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Thingamajigsgoodies.COMBINING_RECIPE_SERIALIZER.get();
    }

    @Override
    public boolean isFirstIngredient(ItemStack is1) {
        return this.firstIng.test(is1);
    }

    @Override
    public boolean isSecondIngredient(ItemStack is2) {
        return this.secondIng.test(is2);
    }

    @Override
    public boolean isThirdIngredient(ItemStack is3) {
        return this.thirdIng.test(is3);
    }

    @Override
    public boolean isFourthIngredient(ItemStack is4) {
        return this.fourthIng.test(is4);
    }

    @Override
    public boolean isIncomplete() {
        return Stream.of(this.firstIng, this.secondIng, this.thirdIng, this.fourthIng).anyMatch(Ingredient::hasNoItems);
    }

    // serials
    public static class Serializer implements RecipeSerializer<CombinerRecipe> {
        private static final MapCodec<CombinerRecipe> CODEC = RecordCodecBuilder.mapCodec(
                kindof1 -> kindof1.group(
                                Ingredient.CODEC.fieldOf("first").forGetter(p_301310_ -> p_301310_.firstIng),
                                Ingredient.CODEC.fieldOf("second").forGetter(p_300938_ -> p_300938_.secondIng),
                                Ingredient.CODEC.fieldOf("third").forGetter(p_301153_ -> p_301153_.thirdIng),
                                Ingredient.CODEC.fieldOf("fourth").forGetter(p_301153_ -> p_301153_.fourthIng),
                                ItemStack.STRICT_CODEC.fieldOf("output").forGetter(p_300935_ -> p_300935_.outputStack)
                        )
                        .apply(kindof1,CombinerRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, CombinerRecipe> STREAM_CODEC = StreamCodec.of(
                CombinerRecipe.Serializer::toNetwork, CombinerRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<CombinerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CombinerRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CombinerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            Ingredient i1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient i2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient i3 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient i4 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            ItemStack out = ItemStack.STREAM_CODEC.decode(buffer);
            return new CombinerRecipe(i1, i2, i3, i4, out);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CombinerRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.firstIng);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.secondIng);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.thirdIng);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.fourthIng);
            ItemStack.STREAM_CODEC.encode(buffer,recipe.outputStack);
        }
    }
}
