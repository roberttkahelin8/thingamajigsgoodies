package net.rk.addon.datagen.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CombinerRecipeInput(ItemStack firstIS, ItemStack secondIS, ItemStack thirdIS, ItemStack fourthIS) implements RecipeInput {
    public CombinerRecipeInput(ItemStack firstIS, ItemStack secondIS, ItemStack thirdIS, ItemStack fourthIS){
        this.firstIS = firstIS;
        this.secondIS = secondIS;
        this.thirdIS = thirdIS;
        this.fourthIS = fourthIS;
    }

    @Override
    public ItemStack getItem(int slotID) {
        ItemStack hoho;
        switch (slotID){
            case 0 -> hoho = this.firstIS;
            case 1 -> hoho = this.secondIS;
            case 2 -> hoho = this.thirdIS;
            case 3 -> hoho = this.fourthIS;
            default -> throw new IllegalArgumentException("TGoodies Combiner Recipe does not contain slot " + slotID);
        }

        return hoho;
    }

    public int size() {
        return 4;
    }

    public boolean isEmpty() {
        return this.firstIS.isEmpty()
                && this.secondIS.isEmpty()
                && this.thirdIS.isEmpty()
                && this.fourthIS.isEmpty();
    }

    // getters
    public ItemStack firstItemStack() {
        return this.firstIS;
    }
    public ItemStack secondItemStack() {
        return this.secondIS;
    }
    public ItemStack thirdItemStack() {
        return this.thirdIS;
    }
    public ItemStack fourthItemStack() {
        return this.fourthIS;
    }
}