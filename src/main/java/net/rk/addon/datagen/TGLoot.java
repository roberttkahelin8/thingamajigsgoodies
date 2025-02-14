package net.rk.addon.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.block.custom.EnderBlossomBlock;
import net.rk.addon.item.TGItems;

import java.util.ArrayList;
import java.util.List;

public class TGLoot extends VanillaBlockLoot{
    public TGLoot(HolderLookup.Provider p){super(p);}

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.dropSelf(TGBlocks.BYPRODUCT.get());
        this.dropSelf(TGBlocks.CONVERTER.get());
        this.dropSelf(TGBlocks.PURIFYING_BLOCK.get());
        this.dropSelf(TGBlocks.PURIFYING_BOOKSHELF.get());
        this.dropSelf(TGBlocks.TWO_STATE_BLOCK.get());

        this.dropOther(TGBlocks.BROWN_BALLOON_BLOCK.get(),TGItems.BROWN_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.BLACK_BALLOON_BLOCK.get(),TGItems.BLACK_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.GRAY_BALLOON_BLOCK.get(),TGItems.GRAY_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.LIGHT_GRAY_BALLOON_BLOCK.get(),TGItems.LIGHT_GRAY_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.BALLOON_BLOCK.get(),TGItems.BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.RED_BALLOON_BLOCK.get(),TGItems.RED_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.ORANGE_BALLOON_BLOCK.get(),TGItems.ORANGE_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.YELLOW_BALLOON_BLOCK.get(),TGItems.YELLOW_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.LIME_BALLOON_BLOCK.get(),TGItems.LIME_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.GREEN_BALLOON_BLOCK.get(),TGItems.GREEN_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.CYAN_BALLOON_BLOCK.get(),TGItems.CYAN_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.BLUE_BALLOON_BLOCK.get(),TGItems.BLUE_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.PURPLE_BALLOON_BLOCK.get(),TGItems.PURPLE_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.MAGENTA_BALLOON_BLOCK.get(),TGItems.MAGENTA_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.PINK_BALLOON_BLOCK.get(),TGItems.PINK_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.LIGHT_BLUE_BALLOON_BLOCK.get(),TGItems.LIGHT_BLUE_BALLOON_BLOCK_ITEM.asItem());
        this.dropOther(TGBlocks.TEAL_BALLOON_BLOCK.get(),TGItems.TEAL_BALLOON_BLOCK_ITEM.asItem());

        this.dropSelf(TGBlocks.SPRING.get());

        this.dropSelf(TGBlocks.COMBINER.get());

        LootItemCondition.Builder conditionalEnderCrop =
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EnderBlossomBlock.AGE,3));
        this.add(TGBlocks.ENDER_BLOSSOM_BLOCK.get(),
                this.createEnderCropDrops(TGBlocks.ENDER_BLOSSOM_BLOCK.get(),
                        Items.ENDER_PEARL,
                        TGItems.ENDER_BLOSSOM_SEEDS.asItem(),
                        conditionalEnderCrop));

        this.dropSelf(TGBlocks.SPOOKY_STONE.get());
        this.dropSelf(TGBlocks.BLUEBERRY_STONE.get());
        this.dropSelf(TGBlocks.NETHERISH_STONE.get());
        this.dropSelf(TGBlocks.VOLCANIC_STONE.get());
        this.dropSelf(TGBlocks.CHARGED_VOLCANIC_STONE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        List<Block> knownBlocks = new ArrayList<>();
        knownBlocks.addAll(TGBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList());
        return knownBlocks;
    }

    protected LootTable.Builder createEnderCropDrops(Block cropBlock, Item grownCropItem, Item seedsItem, LootItemCondition.Builder dropGrownCropCondition) {
        return this.applyExplosionDecay(cropBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(grownCropItem)
                                .when(dropGrownCropCondition)
                                .otherwise(LootItem.lootTableItem(seedsItem))))
                .withPool(LootPool.lootPool()
                        .when(dropGrownCropCondition)
                        .add(LootItem.lootTableItem(seedsItem))));
    }
}
