package net.rk.addon.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.addon.datagen.TGTag;

import java.util.List;

public class ChargedVolcanicStone extends Block{
    public ChargedVolcanicStone(Properties p) {
        super(p.strength(2F,100F).sound(SoundType.ANCIENT_DEBRIS));
    }

    @Override
    public void stepOn(Level lvl, BlockPos bp, BlockState bs, Entity ent1) {
        if(ent1 instanceof LivingEntity){
            ent1.hurt(lvl.damageSources().hotFloor(),10.0F);
            ((LivingEntity) ent1).addEffect(new MobEffectInstance(
                    MobEffects.BLINDNESS,
                    7,
                    0,
                    true,
                    false,
                    false)
            );
            ((LivingEntity) ent1).addEffect(new MobEffectInstance(
                    MobEffects.DIG_SLOWDOWN,
                    7,
                    5,
                    true,
                    false,
                    false)
            );
            ((LivingEntity) ent1).addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    7,
                    3,
                    true,
                    false,
                    false)
            );
            ent1.igniteForSeconds(5);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random){
        boolean meltedAnything = false;
        int lavaLevel = 5;
        int checkThisBlock = random.nextInt(0,5);

        if(level.getBlockState(pos.north()).is(TGTag.CAN_MELT) && checkThisBlock == 0){
            level.setBlock(pos.north(),Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL,lavaLevel),3);
            meltedAnything = true;
        }
        if(level.getBlockState(pos.south()).is(TGTag.CAN_MELT) && checkThisBlock == 1){
            level.setBlock(pos.south(),Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL,lavaLevel),3);
            meltedAnything = true;
        }
        if(level.getBlockState(pos.east()).is(TGTag.CAN_MELT) && checkThisBlock == 2){
            level.setBlock(pos.east(),Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL,lavaLevel),3);
            meltedAnything = true;
        }
        if(level.getBlockState(pos.west()).is(TGTag.CAN_MELT) && checkThisBlock == 3){
            level.setBlock(pos.west(),Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL,lavaLevel),3);
            meltedAnything = true;
        }
        if(level.getBlockState(pos.above()).is(TGTag.CAN_MELT) && checkThisBlock == 4){
            level.setBlock(pos.above(),Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL,lavaLevel),3);
            meltedAnything = true;
        }
        if(level.getBlockState(pos.below()).is(TGTag.CAN_MELT) && checkThisBlock == 5){
            level.setBlock(pos.below(),Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL,lavaLevel),3);
            meltedAnything = true;
        }


        if(meltedAnything){
            level.playSound(null,pos,
                    SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON,
                    SoundSource.BLOCKS,0.75f,1.0f);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigs.charged_volcanic_stone.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
