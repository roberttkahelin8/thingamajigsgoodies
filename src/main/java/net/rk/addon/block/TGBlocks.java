package net.rk.addon.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.custom.*;
import net.rk.addon.fluid.fluidblocks.PurifyingFluidBlock;
import net.rk.addon.fluid.fluidblocks.SludgeFluidBlock;
import net.rk.addon.item.TGItems;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("deprecated")
public class TGBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Thingamajigsgoodies.MODID);

    public static final DeferredHolder<Block,LiquidBlock> SLUDGE_FLUID_BLOCK = BLOCKS.register("sludge",
            SludgeFluidBlock::new);

    public static final DeferredHolder<Block,LiquidBlock> PURIFYING_FLUID_BLOCK = BLOCKS.register("purifying_water",
            PurifyingFluidBlock::new);

    public static final DeferredBlock<Block> PURIFYING_BLOCK = register("purifying_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tl, TooltipFlag tooltipFlag) {
                    tl.add(Component.translatable("block.thingamajigs.purifying_block.desc").withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredBlock<Block> BYPRODUCT = register("byproduct",
            () -> new ByproductBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL)));

    public static final DeferredBlock<Block> CONVERTER = register("converter",
            () -> new ConverterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final DeferredBlock<Block> TWO_STATE_BLOCK = register("thingamajig_state_cell",
            () -> new TwoStateToggledBlock(BlockBehaviour.Properties.of().sound(SoundType.NETHERITE_BLOCK).requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> PURIFYING_BOOKSHELF = register("purifying_bookshelf",
            () -> new PurifyingBookshelf(BlockBehaviour.Properties.ofFullCopy(Blocks.BOOKSHELF)){
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tc, TooltipFlag tooltipFlag) {
                    tc.add(Component.translatable("block.thingamajigsgoodies.purifying_bookshelf.desc")
                            .withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
                }
            });

    // balloon blocks
    public static final DeferredBlock<Block> BALLOON_BLOCK = registerBlockWithoutItem("balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> LIGHT_GRAY_BALLOON_BLOCK = registerBlockWithoutItem("light_gray_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> GRAY_BALLOON_BLOCK = registerBlockWithoutItem("gray_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BLACK_BALLOON_BLOCK = registerBlockWithoutItem("black_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BROWN_BALLOON_BLOCK = registerBlockWithoutItem("brown_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> RED_BALLOON_BLOCK = registerBlockWithoutItem("red_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> ORANGE_BALLOON_BLOCK = registerBlockWithoutItem("orange_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> YELLOW_BALLOON_BLOCK = registerBlockWithoutItem("yellow_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> LIME_BALLOON_BLOCK = registerBlockWithoutItem("lime_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> GREEN_BALLOON_BLOCK = registerBlockWithoutItem("green_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> CYAN_BALLOON_BLOCK = registerBlockWithoutItem("cyan_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> LIGHT_BLUE_BALLOON_BLOCK = registerBlockWithoutItem("light_blue_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> BLUE_BALLOON_BLOCK = registerBlockWithoutItem("blue_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PURPLE_BALLOON_BLOCK = registerBlockWithoutItem("purple_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> MAGENTA_BALLOON_BLOCK = registerBlockWithoutItem("magenta_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> PINK_BALLOON_BLOCK = registerBlockWithoutItem("pink_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<Block> TEAL_BALLOON_BLOCK = registerBlockWithoutItem("teal_balloon_block",
            () -> new BalloonBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> SPRING = register("spring",
            () -> new SpringBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> MOVING_PROBABLE_BLOCK = register("moving_probable_block",
            () -> new MovingProbableBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> COMBINER = register("combiner",
            () -> new CombinerBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> ENDER_BLOSSOM_BLOCK = registerBlockWithoutItem("ender_blossom_block",
            () -> new EnderBlossomBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> SPOOKY_STONE = register("spooky_stone",
            () -> new SpookyStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_PURPLE)));

    public static final DeferredBlock<Block> BLUEBERRY_STONE = register("blueberry_stone",
            () -> new BlueberryStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_BLUE)));

    public static final DeferredBlock<Block> NETHERISH_STONE = register("netherish_stone",
            () -> new NetherishStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.NETHER)));

    public static final DeferredBlock<Block> VOLCANIC_STONE = register("volcanic_stone",
            () -> new VolcanicStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_RED)));

    public static final DeferredBlock<Block> CHARGED_VOLCANIC_STONE = register("charged_volcanic_stone",
            () -> new ChargedVolcanicStone(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_RED)));




    private static DeferredBlock<Block> register(String name, Supplier<Block> block) {
        DeferredBlock<Block> blk = BLOCKS.register(name,block);
        DeferredItem<Item> itm = TGItems.ITEMS.register(name,() -> new BlockItem(blk.get(),new Item.Properties()));
        return blk;
    }

    private static DeferredBlock<Block> registerBlockWithoutItem(String name, Supplier<Block> block){
        return BLOCKS.register(name,block);
    }

    private static boolean always(BlockState bs, BlockGetter bg, BlockPos bp){return true;}
    private static boolean never(BlockState bs, BlockGetter bg, BlockPos bp){return false;}

    private static ToIntFunction<BlockState> customLitBlockEmission(int lv) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.LIT) ? lv : 0;
        };
    }

    private static ToIntFunction<BlockState> enabledLitBlockEmission(int i) {
        return (properties) -> {
            return properties.getValue(BlockStateProperties.ENABLED) ? i : 0;
        };
    }
}
