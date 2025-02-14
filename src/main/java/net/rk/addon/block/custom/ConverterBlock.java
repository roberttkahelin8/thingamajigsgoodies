package net.rk.addon.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.item.TGItems;
import net.rk.thingamajigs.item.TItems;

import java.util.List;

public class ConverterBlock extends Block{
    public ConverterBlock(Properties p) {
        super(p.strength(1f,2f)
                .sound(SoundType.NETHERITE_BLOCK)
                .mapColor(MapColor.METAL).noOcclusion()
                .requiresCorrectToolForDrops());
    }

    @Override
    public void onPlace(BlockState bs1, Level lvl, BlockPos bp, BlockState bs2, boolean b1) {
        lvl.scheduleTick(bp,this,5, TickPriority.LOW);
    }

    @Override
    public void tick(BlockState bs, ServerLevel sl, BlockPos bp, RandomSource rs) {
        float rf = (float)sl.getRandom().nextFloat() * 0.95f;
        if(rf <= 0.8){
            rf = (float)Math.round(rf);
        }
        boolean alreadyCheckedCobble = false;

        // Mod dependent recipes
        // cobble to netherrack recipe
        if(Thingamajigsgoodies.creating){
            if(sl.getBlockState(bp.above()).is(Blocks.COBBLESTONE) && sl.getBlockState(bp.below()).is(Blocks.LAVA)){
                sl.setBlock(bp.above(),Blocks.NETHERRACK.defaultBlockState(), 3);
                sl.playLocalSound(bp, SoundEvents.NETHERRACK_PLACE, SoundSource.BLOCKS,1.0f,rf,false);
                alreadyCheckedCobble = true;
            }
        }

        // sludge and purifying water recipes
        if(!alreadyCheckedCobble){
            if(sl.getBlockState(bp.above()).is(TGBlocks.SLUDGE_FLUID_BLOCK.get())){
                sl.setBlock(bp.above(),TGBlocks.BYPRODUCT.get().defaultBlockState(),3);
                sl.playLocalSound(bp,SoundEvents.BOAT_PADDLE_LAND,SoundSource.BLOCKS,sl.getRandom().nextFloat() * 0.95f,1.0f,false);
            }
            else if(sl.getBlockState(bp.below()).is(TGBlocks.PURIFYING_BLOCK.get())){
                if(!sl.getBlockState(bp.above()).is(TGBlocks.PURIFYING_FLUID_BLOCK.get()) && sl.getBlockState(bp.above()).is(Blocks.AIR)){
                    sl.setBlock(bp.above(),TGBlocks.PURIFYING_FLUID_BLOCK.get().defaultBlockState(), 3);
                    sl.playLocalSound(bp,SoundEvents.AMETHYST_BLOCK_CHIME,SoundSource.BLOCKS,1.0f,rf,false);
                }
            }
        }
        sl.scheduleTick(bp,this,32,TickPriority.LOW);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> l, TooltipFlag tooltipFlag) {
        l.add(Component.translatable("block.sludge_converter.desc")
                .withStyle(ChatFormatting.GRAY));
        l.add(Component.translatable("block.sludge_converter.desc_two")
                .withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        l.add(Component.translatable("block.sludge_converter.desc_three")
                .withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        l.add(Component.translatable("block.thingamajigsgoodies.converter.tooldesc")
                .withStyle(ChatFormatting.LIGHT_PURPLE));
    }

    // updated
    @Override
    protected InteractionResult useWithoutItem(BlockState bs, Level l, BlockPos bp, Player p, BlockHitResult hitResult) {
        InteractionHand h = p.getUsedItemHand();
        ItemStack ih = p.getItemInHand(h);
        Item sludge = TGItems.SLUDGE_FLUID_BUCKET.get();
        Item pure = TGItems.PURIFYING_FLUID_BUCKET.get();
        Item water = Items.WATER_BUCKET;
        Item lava = Items.LAVA_BUCKET;
        Item eggChange = Items.WHEAT;

        float f1 = (float)l.getRandom().nextFloat() * 0.95f;
        if(f1 <= 0.8){
            f1 = (float)Math.round(f1);
        }

        // conversion products
        ItemEntity byp = new ItemEntity(l,bp.getX() + 0.5f,bp.getY() + 1.0f,bp.getZ() + 0.5f,new ItemStack(TGBlocks.BYPRODUCT.get().asItem()));

        ItemEntity gas = new ItemEntity(l,bp.getX() + 0.5f,bp.getY() + 1.0f,bp.getZ() + 0.5f,
                new ItemStack(TGItems.GOLDEN_APPLE_SHARD.get().asItem()));

        ItemEntity egg = new ItemEntity(l,bp.getX() + 0.5f,bp.getY() + 1.0f,bp.getZ() + 0.5f,
                new ItemStack(Items.EGG.asItem()));

        float powerItemPunch = 0.125f;

        if(ih.is(sludge)){
            byp.setDeltaMovement(0.0f,powerItemPunch,0.0f);
            l.addFreshEntity(byp);
            p.setItemInHand(h,new ItemStack(Items.BUCKET));

            for(int i = 0; i < 7; i++){
                double d0 = (double)bp.getX() + l.getRandom().nextDouble();
                double d1 = (double)bp.getY() + l.getRandom().nextDouble();
                double d2 = (double)bp.getZ() + l.getRandom().nextDouble();

                l.addParticle(ParticleTypes.COMPOSTER, d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
                l.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
            }

            l.playLocalSound(bp,SoundEvents.BOAT_PADDLE_LAND,SoundSource.BLOCKS,1.0f,f1,false);

            p.swing(h);
            return InteractionResult.CONSUME;
        }
        else if(ih.is(pure)){
            gas.setDeltaMovement(0.0f,powerItemPunch,0.0f);
            l.addFreshEntity(gas);
            p.setItemInHand(h,new ItemStack(Items.BUCKET));

            for(int i = 0; i < 7; i++){
                double d0 = (double)bp.getX() + l.getRandom().nextDouble();
                double d1 = (double)bp.getY() + l.getRandom().nextDouble();
                double d2 = (double)bp.getZ() + l.getRandom().nextDouble();

                l.addParticle(ParticleTypes.GLOW, d0, d1+ 0.5D, d2, 0.0D, 0.0D, 0.0D);
                l.addParticle(ParticleTypes.TOTEM_OF_UNDYING, d0, d1+ 0.5D, d2, 0.0D, 0.0D, 0.0D);
            }

            l.playLocalSound(bp,SoundEvents.BOAT_PADDLE_LAND,SoundSource.BLOCKS,1.0f,1f,false);
            p.swing(h);
            return InteractionResult.CONSUME;
        }
        else if(ih.is(eggChange)){
            if(Thingamajigsgoodies.creating){
                if(l.getBlockState(bp.below()).is(TGBlocks.PURIFYING_FLUID_BLOCK.get())){
                    egg.setDeltaMovement(0.0f,powerItemPunch,0.0f);
                    l.addFreshEntity(egg);
                    p.getItemInHand(h).shrink(1);

                    for(int i = 0; i < 7; i++){
                        double d0 = (double)bp.getX() + l.getRandom().nextDouble();
                        double d1 = (double)bp.getY() + l.getRandom().nextDouble();
                        double d2 = (double)bp.getZ() + l.getRandom().nextDouble();

                        l.addParticle(ParticleTypes.EGG_CRACK, d0, d1+ 0.5D, d2, 0.0D, 0.0D, 0.0D);
                    }

                    l.playLocalSound(bp,SoundEvents.CHICKEN_EGG,SoundSource.BLOCKS,1.0f,1f,false);
                    p.swing(h);
                    return InteractionResult.CONSUME;
                }
            }
        }
        else if(ih.is(water)){
            p.displayClientMessage(Component.translatable("block.sludge_converter.nyi"),true);
            l.playLocalSound(bp,SoundEvents.BOAT_PADDLE_LAND,SoundSource.BLOCKS,1.0f,f1,false);
            p.swing(h);
            return InteractionResult.CONSUME;
        }
        else if(ih.is(lava)){
            p.displayClientMessage(Component.translatable("block.sludge_converter.nyi"),true);
            l.playLocalSound(bp,SoundEvents.BOAT_PADDLE_LAND,SoundSource.BLOCKS,1.0f,f1,false);
            p.swing(h);
            return InteractionResult.CONSUME;
        }
        else if(ih.is(Items.POWDER_SNOW_BUCKET)){
            p.displayClientMessage(Component.translatable("block.sludge_converter.not_valid_solid"),true);
            return InteractionResult.CONSUME;
        }
        else if(ih.is(TItems.WATER_SOURCE.get())){
            p.displayClientMessage(Component.translatable("message.block.what")
                    .withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD),true);
            return InteractionResult.CONSUME;
        }
        else if(ih.getItem() instanceof BucketItem && ih.getItem() != Items.BUCKET){
            p.displayClientMessage(Component.translatable("block.sludge_converter.not_valid"),true);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

}
