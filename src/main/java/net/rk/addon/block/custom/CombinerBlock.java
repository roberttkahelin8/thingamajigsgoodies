package net.rk.addon.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.BlockHitResult;
import net.rk.addon.menu.CombinerMenu;

import java.util.List;

public class CombinerBlock extends Block {
    public static final MapCodec<CombinerBlock> COMBINER_CODEC = simpleCodec(CombinerBlock::new);

    private static final Component CONTAINER_TITLE = Component.translatable("container.combiner");

    public CombinerBlock(Properties p) {
        super(p.strength(1f,10f).noOcclusion().instrument(NoteBlockInstrument.BIT));
    }

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }

    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((invid, p_52230_, p_52231_) -> {
            return new CombinerMenu(invid, p_52230_, ContainerLevelAccess.create(level, pos));
        }, CONTAINER_TITLE);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigsgoodies.combiner.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
