package net.rk.addon.datagen;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.datagen.custom.CombinerRecipeBuilder;
import net.rk.addon.item.TGItems;
import net.rk.thingamajigs.block.TBlocks;
import net.rk.thingamajigs.item.TItems;

import java.util.concurrent.CompletableFuture;

public class TGRecipe extends RecipeProvider{
    public TGRecipe(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput rc) {
        // buckets
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TGItems.PURIFYING_FLUID_BUCKET.get().asItem(),1)
                .requires(Items.ENCHANTED_GOLDEN_APPLE)
                .requires(TItems.THINGAMAJIG)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.GLISTERING_MELON_SLICE)
                .requires(Items.BUCKET)
                .unlockedBy("has_thingy",has(Items.ENCHANTED_GOLDEN_APPLE))
                .save(rc);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TGItems.SLUDGE_FLUID_BUCKET.get().asItem(),1)
                .requires(Items.SLIME_BALL)
                .requires(Items.ROTTEN_FLESH)
                .requires(TBlocks.POOP.asItem())
                .unlockedBy("has_thingy",has(TBlocks.POOP.asItem()))
                .save(rc);

        //
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TGBlocks.CONVERTER.asItem(),1)
                .define('b',TItems.BASE_COMPONENT)
                .define('r',Items.BREEZE_ROD)
                .define('s',Items.NETHER_STAR)
                .define('n',Items.NETHERITE_INGOT)
                .define('t',Items.CALIBRATED_SCULK_SENSOR)
                .pattern("tst")
                .pattern("nbn")
                .pattern("r r")
                .unlockedBy("has_thing", InventoryChangeTrigger.TriggerInstance.hasItems(TItems.BASE_COMPONENT))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGItems.GLOBIZED_GOLDEN_APPLE_SHARD,1)
                .requires(TGItems.GOLDEN_APPLE_SHARD)
                .requires(TItems.THINGAMAJIG_GLOB)
                .requires(TItems.THINGAMAJIG_GLOB)
                .requires(TItems.THINGAMAJIG_GLOB)
                .requires(TItems.THINGAMAJIG_GLOB)
                .unlockedBy("has_thingy",has(TItems.THINGAMAJIG_GLOB))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGItems.PURIFYING_NUGGET,2)
                .requires(TGItems.GLOBIZED_GOLDEN_APPLE_SHARD)
                .requires(TGItems.GLOBIZED_GOLDEN_APPLE_SHARD)
                .requires(Items.DIAMOND)
                .unlockedBy("has_thingy",has(Items.DIAMOND))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGItems.PURIFYING_INGOT,1)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .requires(TGItems.PURIFYING_NUGGET)
                .unlockedBy("has_thingy",has(TGItems.PURIFYING_NUGGET))
                .save(rc);

        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        RecipeCategory.MISC,
                        TGItems.PURIFYING_GLOB,
                        0.0f,
                        72)
                .unlockedBy("has_thingy",has(TGItems.PURIFYING_INGOT))
                .save(rc,getSmeltingRecipeName(TGItems.PURIFYING_GLOB) + "_smelting1");

        SimpleCookingRecipeBuilder.blasting(
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        RecipeCategory.MISC,
                        TGItems.PURIFYING_GLOB,
                        0.0f,
                        52)
                .unlockedBy("has_thingy",has(TGItems.PURIFYING_INGOT))
                .save(rc,getBlastingRecipeName(TGItems.PURIFYING_GLOB) + "_blasting1");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGBlocks.PURIFYING_BLOCK.asItem(),1)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .requires(TGItems.PURIFYING_GLOB)
                .unlockedBy("has_thingy",has(TGItems.PURIFYING_GLOB))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TGBlocks.PURIFYING_BOOKSHELF.asItem(),1)
                .define('w',ItemTags.PLANKS)
                .define('i',TGItems.PURIFYING_INGOT)
                .pattern("www")
                .pattern("iii")
                .pattern("www")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        // balloon blocks
        balloon(TGItems.BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.WHITE_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build()))
                .save(rc);
        balloon(TGItems.LIGHT_GRAY_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.LIGHT_GRAY_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.GRAY_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.GRAY_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.BLACK_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.BLACK_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.BROWN_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.BROWN_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.RED_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.RED_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.ORANGE_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.ORANGE_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.YELLOW_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.YELLOW_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.LIME_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.LIME_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.GREEN_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.GREEN_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.CYAN_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.CYAN_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.LIGHT_BLUE_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.LIGHT_BLUE_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.BLUE_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.BLUE_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.PURPLE_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.PURPLE_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.MAGENTA_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.MAGENTA_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);
        balloon(TGItems.PINK_BALLOON_BLOCK_ITEM.get(),Ingredient.of(Items.PINK_DYE),1)
                .unlockedBy("has_rubber",inventoryTrigger(ItemPredicate.Builder.item().of(TItems.RUBBER.get()).build())).save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGItems.TEAL_BALLOON_BLOCK_ITEM,1)
                .requires(TGTag.BALLOON_ITEMS)
                .requires(Items.CYAN_DYE)
                .requires(Items.WHITE_DYE)
                .requires(Items.LIGHT_BLUE_DYE)
                .unlockedBy("has_thingy",has(TGTag.BALLOON_ITEMS))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TGBlocks.SPRING.asItem(),1)
                .define('c',TItems.MISC_COMPONENT)
                .define('b',Items.IRON_BARS)
                .define('s',Items.SLIME_BALL)
                .define('i',Items.IRON_INGOT)
                .pattern("iii")
                .pattern("bsb")
                .pattern("ici")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TItems.MISC_COMPONENT))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TGBlocks.TWO_STATE_BLOCK.asItem(),1)
                .define('t',TItems.THINGAMAJIG)
                .define('s',Items.BLACKSTONE)
                .define('r',Items.REDSTONE)
                .define('c',Items.COMPARATOR)
                .pattern("rtr")
                .pattern("scs")
                .pattern("rsr")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TItems.THINGAMAJIG))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TGBlocks.MOVING_PROBABLE_BLOCK.asItem(),1)
                .define('t',TItems.THINGAMAJIG_GLOB)
                .define('b',Items.CHEST)
                .define('e',Items.GOLD_INGOT)
                .pattern("tet")
                .pattern("ebe")
                .pattern("tet")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TItems.THINGAMAJIG_GLOB))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TGBlocks.COMBINER.asItem(),1)
                .define('c',TGBlocks.CONVERTER.asItem())
                .define('t',Items.CRAFTER)
                .define('y',TItems.TECHNOLOGY_COMPONENT)
                .pattern("tyt")
                .pattern("ycy")
                .pattern("tyt")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGBlocks.CONVERTER.asItem()))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGItems.POOP_HORN,1)
                .requires(TBlocks.POOP.asItem())
                .requires(Items.GOAT_HORN)
                .unlockedBy("has_thingy",has(Items.GOAT_HORN))
                .save(rc);

        // combiner recipes

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.NETHERITE_SWORD),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(TItems.HEALTH_COMPONENT),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_SWORD.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.NETHERITE_PICKAXE),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(Items.TNT),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_PICKAXE.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.NETHERITE_AXE),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(Items.ANVIL),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_AXE.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.NETHERITE_SHOVEL),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(TBlocks.DIRT_CULVERT.asItem()),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_SHOVEL.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.NETHERITE_HOE),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(Items.SUNFLOWER),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_HOE.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TGItems.IRON_SCYTHE,1)
                .define('i',Items.IRON_INGOT)
                .define('n',Items.IRON_NUGGET)
                .define('s',Items.STICK)
                .pattern("iin")
                .pattern("is ")
                .pattern(" s ")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TGItems.DIAMOND_SCYTHE,1)
                .define('d',Items.DIAMOND)
                .define('n',Items.IRON_NUGGET)
                .define('s',Items.STICK)
                .pattern("ddn")
                .pattern("ds ")
                .pattern(" s ")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(rc);

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                Ingredient.of(TGItems.DIAMOND_SCYTHE.asItem()),
                        Ingredient.of(Items.NETHERITE_INGOT),RecipeCategory.TOOLS,TGItems.NETHERITE_SCYTHE.asItem())
                .unlocks("has_thingy",InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(rc,TGItems.NETHERITE_SCYTHE.getId());

        CombinerRecipeBuilder.combining(
                        Ingredient.of(TGItems.NETHERITE_SCYTHE),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(Items.WHEAT_SEEDS),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_SCYTHE.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGItems.ENDER_BLOSSOM_SEEDS,1)
                .requires(Items.ENDER_PEARL)
                .requires(Tags.Items.SEEDS)
                .unlockedBy("has_thingy",has(Items.ENDER_PEARL))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGBlocks.BLUEBERRY_STONE.asItem(),1)
                .requires(Items.BLUE_DYE)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.STONE)
                .unlockedBy("has_thingy",has(Items.STONE))
                .save(rc);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TGBlocks.CHARGED_VOLCANIC_STONE.asItem(),1)
                .define('c',Items.BLAZE_POWDER)
                .define('m',Items.FIRE_CHARGE)
                .define('v',TGBlocks.VOLCANIC_STONE.asItem())
                .pattern("cmc")
                .pattern("mvm")
                .pattern("cmc")
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGBlocks.VOLCANIC_STONE.asItem()))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGBlocks.NETHERISH_STONE.asItem(),1)
                .requires(Items.RED_DYE)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.NETHERRACK)
                .unlockedBy("has_thingy",has(Items.NETHERRACK))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGBlocks.SPOOKY_STONE.asItem(),1)
                .requires(Items.PURPLE_DYE)
                .requires(Items.STRING)
                .requires(Items.STONE)
                .unlockedBy("has_thingy",has(Items.STONE))
                .save(rc);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TGBlocks.VOLCANIC_STONE.asItem(),1)
                .requires(Items.ORANGE_DYE)
                .requires(Items.FIRE_CHARGE)
                .requires(Items.MAGMA_BLOCK)
                .unlockedBy("has_thingy",has(Items.MAGMA_BLOCK))
                .save(rc);


        // more combiner recipes
        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.TROPICAL_FISH),
                        Ingredient.of(Items.CONDUIT),
                        Ingredient.of(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE),
                        Ingredient.of(TItems.THINGAMAJIG_GLOB),
                        TGItems.FISHING_CATALYST.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.FLETCHING_TABLE),
                        Ingredient.of(Items.DISPENSER),
                        Ingredient.of(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE),
                        Ingredient.of(TItems.THINGAMAJIG_GLOB),
                        TGItems.CROSSBOW_CATALYST.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.FLETCHING_TABLE),
                        Ingredient.of(Items.DISPENSER),
                        Ingredient.of(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE),
                        Ingredient.of(TItems.THINGAMAJIG_GLOB),
                        TGItems.BOW_CATALYST.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(ItemTags.WOOL),
                        Ingredient.of(Items.WARPED_NYLIUM),
                        Ingredient.of(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE),
                        Ingredient.of(TItems.THINGAMAJIG_GLOB),
                        TGItems.SHEARS_CATALYST.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.FISHING_ROD),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(TGItems.FISHING_CATALYST),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_FISHING_ROD.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.CROSSBOW),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(TGItems.CROSSBOW_CATALYST),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_CROSSBOW.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.BOW),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(TGItems.BOW_CATALYST),
                        Ingredient.of(Items.BREEZE_ROD),
                        TGItems.PURIFYING_BOW.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);

        CombinerRecipeBuilder.combining(
                        Ingredient.of(Items.SHEARS),
                        Ingredient.of(TGItems.PURIFYING_INGOT),
                        Ingredient.of(TGItems.SHEARS_CATALYST),
                        Ingredient.of(Items.BLADE_POTTERY_SHERD),
                        TGItems.PURIFYING_SHEARS.asItem()
                )
                .unlockedBy("has_thingy", InventoryChangeTrigger.TriggerInstance.hasItems(TGItems.PURIFYING_INGOT))
                .save(rc);
    }

    public static RecipeBuilder balloon(ItemLike balloon, Ingredient dye, int manyBalloons) {
        return ShapedRecipeBuilder.shaped(
                        RecipeCategory.MISC, balloon, manyBalloons)
                .define('#',TItems.RUBBER.get())
                .define('d',dye)
                .pattern("###")
                .pattern("#d#")
                .pattern("###");
    }
}

