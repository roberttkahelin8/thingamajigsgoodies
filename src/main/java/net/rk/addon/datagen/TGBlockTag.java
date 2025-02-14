package net.rk.addon.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.addon.block.TGBlocks;
import net.rk.thingamajigs.Thingamajigs;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TGBlockTag extends BlockTagsProvider{
    public TGBlockTag(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Thingamajigs.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider tc) {
        this.tag(TGTag.CAN_MELT)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)
                .addTag(BlockTags.LOGS_THAT_BURN)
                .add(Blocks.STONE_SLAB)
                .add(Blocks.STONE_STAIRS)
                .addTag(BlockTags.WOODEN_DOORS)
                .addTag(BlockTags.WOODEN_TRAPDOORS)
                .addTag(BlockTags.WOODEN_BUTTONS)
                .addTag(BlockTags.WOODEN_SLABS)
                .addTag(BlockTags.WOODEN_FENCES)
                .addTag(BlockTags.WOODEN_STAIRS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.FLOWER_POTS)
                .addTag(BlockTags.SMALL_FLOWERS)
                .addTag(BlockTags.TALL_FLOWERS)
                .addTag(BlockTags.LEAVES)
                .add(Blocks.MANGROVE_ROOTS)
                .add(Blocks.MUDDY_MANGROVE_ROOTS)
        ;

        this.tag(TGTag.CAN_POP_BALLOONS)
                .add(Blocks.LAVA)
                .add(Blocks.LAVA_CAULDRON)
                .add(Blocks.FIRE)
                .add(Blocks.SOUL_FIRE)
                .add(Blocks.CAMPFIRE)
                .add(Blocks.SOUL_CAMPFIRE)
        ;

        this.tag(TGTag.BALLOON_BLOCKS)
                .add(TGBlocks.BALLOON_BLOCK.get())
                .add(TGBlocks.LIGHT_GRAY_BALLOON_BLOCK.get())
                .add(TGBlocks.GRAY_BALLOON_BLOCK.get())
                .add(TGBlocks.BLACK_BALLOON_BLOCK.get())
                .add(TGBlocks.BROWN_BALLOON_BLOCK.get())
                .add(TGBlocks.RED_BALLOON_BLOCK.get())
                .add(TGBlocks.ORANGE_BALLOON_BLOCK.get())
                .add(TGBlocks.YELLOW_BALLOON_BLOCK.get())
                .add(TGBlocks.LIME_BALLOON_BLOCK.get())
                .add(TGBlocks.GREEN_BALLOON_BLOCK.get())
                .add(TGBlocks.TEAL_BALLOON_BLOCK.get())
                .add(TGBlocks.CYAN_BALLOON_BLOCK.get())
                .add(TGBlocks.LIGHT_BLUE_BALLOON_BLOCK.get())
                .add(TGBlocks.BLUE_BALLOON_BLOCK.get())
                .add(TGBlocks.PURPLE_BALLOON_BLOCK.get())
                .add(TGBlocks.MAGENTA_BALLOON_BLOCK.get())
                .add(TGBlocks.PINK_BALLOON_BLOCK.get())
        ;

        this.tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(TGBlocks.PURIFYING_BLOCK.get())
        ;

        this.tag(Tags.Blocks.BOOKSHELVES)
                .add(TGBlocks.PURIFYING_BOOKSHELF.get())
        ;
        this.tag(BlockTags.ENCHANTMENT_POWER_PROVIDER)
                .add(TGBlocks.PURIFYING_BOOKSHELF.get())
        ;
        this.tag(BlockTags.ENCHANTMENT_POWER_TRANSMITTER)
                .add(TGBlocks.PURIFYING_BOOKSHELF.get())
                .add(TGBlocks.PURIFYING_BLOCK.get())
                .addTag(TGTag.BALLOON_BLOCKS)
                .add(TGBlocks.CONVERTER.get())
                .add(TGBlocks.COMBINER.get())
                .add(TGBlocks.PURIFYING_FLUID_BLOCK.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS)
                .add(TGBlocks.SLUDGE_FLUID_BLOCK.get())
                .add(TGBlocks.BYPRODUCT.get())
        ;

        this.tag(BlockTags.ENDERMAN_HOLDABLE)
                .add(TGBlocks.BYPRODUCT.get())
        ;

        this.tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .add(TGBlocks.PURIFYING_BLOCK.get())
                .add(TGBlocks.CONVERTER.get())
                .add(TGBlocks.COMBINER.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.CROPS)
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
        ;
        this.tag(BlockTags.MAINTAINS_FARMLAND)
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
        ;

        this.tag(BlockTags.INFINIBURN_NETHER)
                .add(TGBlocks.NETHERISH_STONE.get())
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
        ;

        this.tag(BlockTags.INFINIBURN_OVERWORLD)
                .add(TGBlocks.VOLCANIC_STONE.get())
        ;

        this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .add(TGBlocks.SPOOKY_STONE.get())
        ;

        this.tag(BlockTags.WITHER_IMMUNE)
                .add(TGBlocks.NETHERISH_STONE.get())
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.DRAGON_IMMUNE)
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
        ;

        this.tag(BlockTags.DRAGON_TRANSPARENT)
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.IMPERMEABLE)
                .add(TGBlocks.SPOOKY_STONE.get())
                .add(TGBlocks.BLUEBERRY_STONE.get())
                .add(TGBlocks.NETHERISH_STONE.get())
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.INVALID_SPAWN_INSIDE)
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.MUSHROOM_GROW_BLOCK)
                .add(TGBlocks.SPOOKY_STONE.get())
        ;

        this.tag(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
                .add(TGBlocks.COMBINER.get())
                .add(TGBlocks.CONVERTER.get())
        ;

        this.tag(BlockTags.STRIDER_WARM_BLOCKS)
                .add(TGBlocks.PURIFYING_FLUID_BLOCK.get())
        ;

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(TGBlocks.TWO_STATE_BLOCK.get())
                .add(TGBlocks.BLUEBERRY_STONE.get())
                .add(TGBlocks.NETHERISH_STONE.get())
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
        ;

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(TGBlocks.COMBINER.get())
        ;

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(TGBlocks.PURIFYING_BLOCK.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
        ;

        this.tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
                .add(TGBlocks.PURIFYING_BLOCK.get())
        ;
        this.tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .add(TGBlocks.PURIFYING_BLOCK.get())
        ;
        this.tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .add(TGBlocks.PURIFYING_BLOCK.get())
        ;
        this.tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
                .add(TGBlocks.PURIFYING_BLOCK.get())
        ;


        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(TGBlocks.PURIFYING_BOOKSHELF.get())
        ;

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(TGBlocks.BYPRODUCT.get())
        ;

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TGBlocks.CONVERTER.get())
                .add(TGBlocks.PURIFYING_BLOCK.get())
                .add(TGBlocks.TWO_STATE_BLOCK.get())
                .add(TGBlocks.MOVING_PROBABLE_BLOCK.get())
                .add(TGBlocks.ENDER_BLOSSOM_BLOCK.get())
                .add(TGBlocks.SPOOKY_STONE.get())
                .add(TGBlocks.BLUEBERRY_STONE.get())
                .add(TGBlocks.NETHERISH_STONE.get())
                .add(TGBlocks.VOLCANIC_STONE.get())
                .add(TGBlocks.CHARGED_VOLCANIC_STONE.get())
        ;

        this.tag(TGTag.PURIFYING_CAN_SHEAR)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.WOOL)
                .addTag(BlockTags.WOOL_CARPETS)
                .add(Blocks.SHORT_GRASS)
                .add(Blocks.TALL_GRASS)
                .add(Blocks.SEAGRASS)
                .add(Blocks.TALL_SEAGRASS)
                .add(Blocks.COBWEB)
                .add(Blocks.FERN)
                .add(Blocks.LARGE_FERN)
                .add(Blocks.DEAD_BUSH)
                .add(Blocks.HANGING_ROOTS)
                .add(Blocks.VINE)
                .add(Blocks.TRIPWIRE)
                .add(Blocks.CAVE_VINES)
                .add(Blocks.TWISTING_VINES)
                .add(Blocks.WEEPING_VINES)
                .add(Blocks.CAVE_VINES_PLANT)
                .add(Blocks.TWISTING_VINES_PLANT)
                .add(Blocks.WEEPING_VINES_PLANT)
                .add(Blocks.GLOW_LICHEN)
                .add(Blocks.CAMPFIRE)
                .add(Blocks.SOUL_CAMPFIRE)
                .add(Blocks.KELP)
                .add(Blocks.KELP_PLANT)
                .addTag(BlockTags.FLOWERS)
        ;
    }

    @Override
    public String getName() {
        return "Thingamajigs Addon Goodies Block Tags";
    }
}
