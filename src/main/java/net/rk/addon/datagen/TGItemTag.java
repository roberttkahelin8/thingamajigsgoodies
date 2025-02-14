package net.rk.addon.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.item.TGItems;
import net.rk.thingamajigs.Thingamajigs;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TGItemTag extends ItemTagsProvider{
    public TGItemTag(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Thingamajigs.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider prvdr) {
        this.tag(TGTag.BALLOON_ITEMS)
                .add(TGItems.BALLOON_BLOCK_ITEM.get())
                .add(TGItems.LIGHT_GRAY_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.GRAY_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.BLACK_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.BROWN_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.RED_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.ORANGE_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.YELLOW_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.LIME_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.GREEN_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.CYAN_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.LIGHT_BLUE_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.BLUE_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.PURPLE_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.MAGENTA_BALLOON_BLOCK_ITEM.get())
                .add(TGItems.PINK_BALLOON_BLOCK_ITEM.get())
        ;

        this.tag(Tags.Items.INGOTS)
                .add(TGItems.PURIFYING_INGOT.asItem())
        ;

        this.tag(Tags.Items.NUGGETS)
                .add(TGItems.PURIFYING_NUGGET.asItem())
        ;

        this.tag(Tags.Items.BOOKSHELVES)
                .add(TGBlocks.PURIFYING_BOOKSHELF.asItem())
        ;

        this.tag(TGTag.SCYTHES)
                .add(TGItems.IRON_SCYTHE.asItem())
                .add(TGItems.DIAMOND_SCYTHE.asItem())
                .add(TGItems.NETHERITE_SCYTHE.asItem())
                .add(TGItems.PURIFYING_SCYTHE.asItem())
        ;

        this.tag(ItemTags.SWORDS)
                .add(TGItems.PURIFYING_SWORD.asItem())
        ;
        this.tag(ItemTags.PICKAXES)
                .add(TGItems.PURIFYING_PICKAXE.asItem())
        ;
        this.tag(ItemTags.AXES)
                .add(TGItems.PURIFYING_AXE.asItem())
        ;
        this.tag(ItemTags.SHOVELS)
                .add(TGItems.PURIFYING_SHOVEL.asItem())
        ;
        this.tag(ItemTags.HOES)
                .add(TGItems.PURIFYING_HOE.asItem())
        ;

        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .add(TGItems.PURIFYING_PICKAXE.asItem())
        ;

        this.tag(ItemTags.DURABILITY_ENCHANTABLE)
                .addTag(TGTag.SCYTHES)
        ;
        this.tag(ItemTags.MINING_LOOT_ENCHANTABLE)
                .addTag(TGTag.SCYTHES)
        ;
        this.tag(ItemTags.MINING_ENCHANTABLE)
                .addTag(TGTag.SCYTHES)
        ;

        this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .add(TGItems.ENDER_BLOSSOM_SEEDS.asItem())
        ;

        this.tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(TGItems.PURIFYING_INGOT.asItem())
                .add(TGItems.PURIFYING_GLOB.asItem())
        ;

        this.tag(ItemTags.CHICKEN_FOOD)
                .add(TGItems.ENDER_BLOSSOM_SEEDS.asItem())
        ;

        this.tag(ItemTags.IGNORED_BY_PIGLIN_BABIES)
                .add(TGItems.GOLDEN_APPLE_SHARD.asItem())
                .add(TGItems.GLOBIZED_GOLDEN_APPLE_SHARD.asItem())
                .add(TGItems.PURIFYING_NUGGET.asItem())
                .add(TGItems.PURIFYING_INGOT.asItem())
                .add(TGItems.PURIFYING_GLOB.asItem())
                .add(TGBlocks.PURIFYING_BLOCK.asItem())
                .add(TGItems.ENDER_BLOSSOM_SEEDS.asItem())
        ;

        this.tag(TGTag.PURIFYING_BOW_PROJECTILE_ITEMS)
                .addTag(ItemTags.ARROWS)
                .add(Items.FIREWORK_ROCKET)
        ;

        this.tag(TGTag.PURIFYING_CROSSBOW_PROJECTILE_ITEMS)
                .addTag(ItemTags.ARROWS) // normal
                .add(Items.FIRE_CHARGE) // grants small fireball shots
                .add(Items.FIREWORK_ROCKET) // normal
                .add(Items.SNOWBALL) // grants SNOWBALL FIGHTS
                .add(Items.EGG) // grants free eggs
                .add(Items.SPLASH_POTION) // grants water splashes
                .add(Items.WIND_CHARGE) // grants wind charges
                .add(Items.SHULKER_SHELL) // shell grants shulker bullet
                .add(Items.OMINOUS_TRIAL_KEY) // key grants extra
        ;
    }
}
