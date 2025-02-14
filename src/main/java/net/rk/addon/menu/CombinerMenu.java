package net.rk.addon.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.datagen.custom.CombinerRecipe;
import net.rk.addon.datagen.custom.CombinerRecipeInput;

import java.util.List;
import java.util.OptionalInt;

@SuppressWarnings("unsafe,unused,deprecated")
public class CombinerMenu extends ItemCombinerMenu{
    private final Level level;

    public static final int ITEM_SLOT = 0;
    public static final int FIRST_MODIFIER = 1;
    public static final int SECOND_MODIFIER = 2;
    public static final int THIRD_MODIFIER = 3;
    public static final int RESULT_ITEM = 4;

    public static final int FIRST_X = 27;
    public static final int FIRST_MODIFIER_X = 45;
    public static final int SECOND_MODIFIER_X = 63;
    public static final int THIRD_MODIFIER_X = 81;
    private static final int RESULT_ITEM_X = 135;

    public static final int Y_ALL = 37; // was 47

    public boolean craftIsValid = false;

    private RecipeHolder<CombinerRecipe> selRecs;
    private final List<RecipeHolder<CombinerRecipe>> recs;
    private BlockPos bp;

    public CombinerMenu(int id, Inventory p52230, ContainerLevelAccess containerLevelAccess) {
        super(TGMenu.COMBINER_MENU.get(),id, p52230,containerLevelAccess);
        this.level = p52230.player.level();
        this.recs = level.getRecipeManager().getAllRecipesFor(Thingamajigsgoodies.COMBINING_RECIPE_TYPE.get());
        this.bp = p52230.player.blockPosition();
    }

    public CombinerMenu(int id, Inventory inv) {
        this(id, inv, ContainerLevelAccess.NULL);
    }

    @Override
    protected boolean mayPickup(Player player, boolean b) {
        return this.selRecs != null && ((CombinerRecipe)this.selRecs.value()).matches(this.createRInput(), this.level);
    }

    private CombinerRecipeInput createRInput() {
        return new CombinerRecipeInput(
                this.inputSlots.getItem(0),
                this.inputSlots.getItem(1),
                this.inputSlots.getItem(2),
                this.inputSlots.getItem(3));
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        itemStack.onCraftedBy(level,player,itemStack.getCount());
        this.resultSlots.awardUsedRecipes(player,this.getSlotItems());
        this.shrinkSlot(0);
        this.shrinkSlot(1);
        this.shrinkSlot(2);
        this.shrinkSlot(3);
        player.displayClientMessage(Component.translatable("container.combiner.message",itemStack.getDisplayName()),true); // confirmation that craft was successful
        this.access.execute((level,k)->
                level.playSound(null,bp, SoundEvents.HEAVY_CORE_BREAK, SoundSource.BLOCKS,1.0f,1.0f));
    }

    private void shrinkSlot(int index) {
        ItemStack itemstack = this.inputSlots.getItem(index);
        if (!itemstack.isEmpty()) {
            itemstack.shrink(1);
            this.inputSlots.setItem(index, itemstack);
        }
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return blockState.is(TGBlocks.COMBINER);
    }

    @Override
    public void createResult() {
        CombinerRecipeInput combineInput = this.createRInput();
        List<RecipeHolder<CombinerRecipe>> list = this.level.getRecipeManager().getRecipesFor(Thingamajigsgoodies.COMBINING_RECIPE_TYPE.get(), combineInput, this.level);
        if (list.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            craftIsValid = false;
        }
        else {
            RecipeHolder<CombinerRecipe> recipeholder = list.getFirst();

            ItemStack itemstack = ((CombinerRecipe)recipeholder.value()).assemble(combineInput, this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.selRecs = recipeholder;
                this.resultSlots.setRecipeUsed(recipeholder);
                this.resultSlots.setItem(0, itemstack);
                craftIsValid = true;
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return super.quickMoveStack(player, index);
    }

    private OptionalInt findOneSlot(ItemStack stack) {
        return this.recs.stream().flatMapToInt(
                (holder) -> findOne(holder.value(), stack).stream())
                .filter((slotid) -> !this.getSlot(slotid).hasItem()).findFirst();
    }

    @Override
    public int getSlotToQuickMoveTo(ItemStack stack){return this.findOneSlot(stack).orElse(0);}

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot){
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public boolean canMoveIntoInputSlots(ItemStack stack){return this.findOneSlot(stack).isPresent();}


    @Override
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        if(!inventory.getItem(0).isEmpty() || !inventory.getItem(1).isEmpty() || !inventory.getItem(2).isEmpty() || !inventory.getItem(3).isEmpty()){
            float f = level.getRandom().nextFloat();
            if(f < 0.95f){
                f = 0.95f;
            }
            level.playSound(null,bp,SoundEvents.HEAVY_CORE_HIT,SoundSource.BLOCKS,0.9f,f);
        }
    }

    private OptionalInt findOne(CombinerRecipe rec1, ItemStack stack){
        if (rec1.isFirstIngredient(stack)) {
            return OptionalInt.of(0);
        }
        else if (rec1.isSecondIngredient(stack)) {
            return OptionalInt.of(1);
        }
        else if (rec1.isThirdIngredient(stack)) {
            return OptionalInt.of(2);
        }
        else{
            return rec1.isFourthIngredient(stack) ? OptionalInt.of(2) : OptionalInt.empty();
        }
    }

    private List<ItemStack> getSlotItems() {
        return List.of(this.inputSlots.getItem(0), this.inputSlots.getItem(1), this.inputSlots.getItem(2), this.inputSlots.getItem(3));
    }

    @Override
    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions(){
        return ItemCombinerMenuSlotDefinition.create()
                .withSlot(ITEM_SLOT,FIRST_X,Y_ALL,
                        (is1) ->
                                this.recs.stream().anyMatch((combrecholder1) ->
                                        combrecholder1.value().isFirstIngredient(is1)))
                .withSlot(FIRST_MODIFIER,FIRST_MODIFIER_X,Y_ALL,
                        (is2) ->
                                this.recs.stream().anyMatch((combrecholder1) ->
                                        combrecholder1.value().isSecondIngredient(is2)))
                .withSlot(SECOND_MODIFIER,SECOND_MODIFIER_X,Y_ALL,
                        (is3) ->
                                this.recs.stream().anyMatch((combrecholder1) ->
                                        combrecholder1.value().isThirdIngredient(is3)))
                .withSlot(THIRD_MODIFIER,THIRD_MODIFIER_X,Y_ALL,
                        (is4) ->
                                this.recs.stream().anyMatch((combrecholder1) ->
                                        combrecholder1.value().isFourthIngredient(is4)))
                .withResultSlot(RESULT_ITEM,RESULT_ITEM_X,Y_ALL)
                .build();
    }
}
