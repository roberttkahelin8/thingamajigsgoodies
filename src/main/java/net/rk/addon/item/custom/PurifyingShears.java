package net.rk.addon.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.IShearable;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.rk.addon.datagen.TGTag;

import java.util.List;

public class PurifyingShears extends Item{
    public PurifyingShears(Properties p){
        super(p.rarity(Rarity.EPIC).fireResistant().stacksTo(1).durability(3239)
                .component(DataComponents.TOOL,PurifyingShears.createToolProperties()));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_shears.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    public static Tool createToolProperties(){
        return new Tool(List.of(
                Tool.Rule.minesAndDrops(TGTag.PURIFYING_CAN_SHEAR, 15.0F),
                Tool.Rule.overrideSpeed(TGTag.PURIFYING_CAN_SHEAR, 15.0F)), 1.0F, 1);
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!level.isClientSide && !state.is(BlockTags.FIRE)) {
            RandomSource rs = entityLiving.level().getRandom();
            if(rs.nextIntBetweenInclusive(1,100) == 10){
                entityLiving.addEffect(new MobEffectInstance(
                        MobEffects.DIG_SPEED,
                        60,
                        2,
                        true,
                        false,
                        false));
            }
            stack.hurtAndBreak(1, entityLiving, EquipmentSlot.MAINHAND);
        }
        return state.is(TGTag.PURIFYING_CAN_SHEAR);
    }

    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof IShearable target) {
            if (entity.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
            else {
                BlockPos pos = entity.blockPosition();
                if (target.isShearable(player, stack, entity.level(), pos)) {
                    RandomSource rs = player.level().getRandom();
                    if(rs.nextIntBetweenInclusive(1,100) == 10){
                        player.addEffect(new MobEffectInstance(
                                MobEffects.LUCK,
                                60,
                                2,
                                true,
                                false,
                                false));
                    }
                    //
                    target.onSheared(player, stack, entity.level(), pos).forEach((drop) -> {
                        target.spawnShearedDrop(entity.level(), pos, drop);
                    });
                    entity.gameEvent(GameEvent.SHEAR, player);
                    stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                }

                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHEARS_ACTIONS.contains(itemAbility);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState blockstate1 = blockstate.getToolModifiedState(context, ItemAbilities.SHEARS_TRIM, false);
        if (blockstate1 != null) {
            Player player = context.getPlayer();
            ItemStack itemstack = context.getItemInHand();
            if (player instanceof ServerPlayer) {
                RandomSource rs = player.level().getRandom();
                if(rs.nextIntBetweenInclusive(1,100) == 10){
                    player.addEffect(new MobEffectInstance(
                            MobEffects.DAMAGE_RESISTANCE,
                            40,
                            4,
                            true,
                            false,
                            false));
                }
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }

            level.setBlockAndUpdate(blockpos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(context.getPlayer(), blockstate1));
            if (player != null) {
                itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.useOn(context);
        }
    }
}
