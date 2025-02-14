package net.rk.addon.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.rk.thingamajigs.item.TItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TwoStateToggledBlock extends Block{
    // how about endless state blocks on one tick delays for red-stone con-traps?
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED; // locked state for more custom stuff
    public static final BooleanProperty GLOWING = BooleanProperty.create("glowing");

    public TwoStateToggledBlock(Properties p) {
        super(p.strength(1f,2f));
        this.registerDefaultState(defaultBlockState()
                .setValue(POWERED,false).setValue(GLOWING,false).setValue(LOCKED,false));
    }

    // nothing fancy here
    @SuppressWarnings("deprecated")
    @Override
    public boolean hasAnalogOutputSignal(BlockState bs) {
        return true;
    }

    // modified from original source to be faster (output for comparators)
    @SuppressWarnings("deprecated")
    @Override
    public int getAnalogOutputSignal(BlockState bs, Level l, BlockPos bp) {
        return l.getBlockState(bp).getValue(GLOWING) ? 15 : 0;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> cl, TooltipFlag tooltipFlag) {
        cl.add(Component.translatable("block.thingamajigs.thingamajig_state_block.desc")
                .withStyle(ChatFormatting.GRAY));
        cl.add(Component.translatable("block.thingamajigs.thingamajig_state_block.desc_two")
                .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GREEN));
    }

    // custom lock behavior for special use cases
    @SuppressWarnings("deprecated")
    @Override
    protected ItemInteractionResult useItemOn(ItemStack mis, BlockState bs, Level l, BlockPos bp, Player p, InteractionHand h, BlockHitResult bhr) {
        mis = p.getMainHandItem();
        ItemStack ois = p.getOffhandItem();

        if(mis.is(TItems.KEY.get())){
            bs = bs.cycle(LOCKED);
            l.setBlock(bp,bs,10);
            l.playSound(null,bp, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundSource.BLOCKS,0.55F,1.0F);
            p.swing(p.getUsedItemHand());
            return ItemInteractionResult.CONSUME;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    // reduced weight of code by simplifying it down to a better size
    @SuppressWarnings("deprecated")
    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        boolean bl = sl.hasNeighborSignal(bp); // get the neighboring block signal (the thing that powers this block)
        if (bl != bs.getValue(POWERED)) {
            BlockState bs2 = bs;
            if (!bs.getValue(POWERED)) {
                // this is a simplified (no redundancies with casts or boxing) version of the new bulb behavior that everyone liked
                if(!bs.getValue(LOCKED)){
                    sl.playSound(null, bp, (bs2 = bs2.cycle(GLOWING)).getValue(GLOWING) ? SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON : SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF, SoundSource.BLOCKS);
                }
            }
            if(!bs.getValue(LOCKED)){
                sl.setBlock(bp, bs2.setValue(POWERED, bl), 3);
            }
        }
    }

    @SuppressWarnings("deprecated")
    @Override
    public void onPlace(BlockState bs1, Level l, BlockPos bp, BlockState bs2, boolean b1) {
        l.scheduleTick(bp,this,1); // removed useless code from src
    }

    // is the source powered?
    @SuppressWarnings("deprecated")
    @Override
    public void neighborChanged(BlockState bs, Level l, BlockPos bp1, Block b1, BlockPos bp2, boolean b111) {
        if(l.hasNeighborSignal(bp2) != bs.getValue(POWERED)){
            l.scheduleTick(bp1,this,1);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(POWERED,GLOWING,LOCKED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext bpc) {
        return this.defaultBlockState().setValue(LOCKED,false);
    }
}
