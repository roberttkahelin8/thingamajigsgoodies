package net.rk.addon.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.rk.addon.item.tool.TGTier;

import java.util.List;

public class ScytheItem extends TieredItem{
    private static final Direction[] ALL_DIRS = Direction.values();

    public ScytheItem(Tier tier, Properties properties){
        super(tier, properties.component(DataComponents.TOOL,tier.createToolProperties(BlockTags.CROPS)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.scythe.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        if(block instanceof TallGrassBlock || block instanceof FlowerBlock || block instanceof DeadBushBlock || block instanceof CropBlock || block instanceof DoublePlantBlock){
            return true;
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        Level lvl = entity.level();
        if(entity instanceof Player){
            BlockPos bp = new BlockPos(entity.getBlockX(),entity.getBlockY(),entity.getBlockZ());
            Block tempBlock = lvl.getBlockState(bp).getBlock();
            if(tempBlock instanceof TallGrassBlock || tempBlock instanceof FlowerBlock || tempBlock instanceof DeadBushBlock || tempBlock instanceof CropBlock || tempBlock instanceof DoublePlantBlock){
                return false;
            }
            else{
                tryDestroyBlocksInArea(lvl,bp,stack,entity);
            }
        }
        return false;
    }

    public void tryDestroyBlocksInArea(Level lvl, BlockPos bp, ItemStack stack, LivingEntity entity){
        if(this.removeBlocksOfTypeAOE(lvl,bp,stack,entity)){
            /*
            switch(this.getTier()){
                case Tiers.WOOD -> stack.hurtAndBreak(5,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                case Tiers.STONE, Tiers.GOLD -> stack.hurtAndBreak(4,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                case Tiers.IRON -> stack.hurtAndBreak(3,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                case Tiers.DIAMOND -> stack.hurtAndBreak(2,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                case Tiers.NETHERITE -> stack.hurtAndBreak(1,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                default -> {
                    if(this.getTier().equals(TGTier.PURIFYING_TIER)){
                        stack.hurtAndBreak(1,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                    }
                    else{
                        stack.hurtAndBreak(1,entity,LivingEntity.getSlotForHand(entity.getUsedItemHand()));
                    }
                }
            }
            */
            lvl.levelEvent(2013,bp,0);
        }
    }

    private boolean removeBlocksOfTypeAOE(Level level, BlockPos pos, ItemStack is, LivingEntity e){
        int depth = 6;
        int limit = 14;

        if(this.getTier().equals(Tiers.WOOD)){
            depth = 3;
            limit = 16;
        }
        else if(this.getTier().equals(Tiers.STONE) || this.getTier().equals(Tiers.GOLD)){
            depth = 5;
            limit = 32;
        }
        else if(this.getTier().equals(Tiers.IRON)){
            depth = 7;
            limit = 52;
        }
        else if (this.getTier().equals(Tiers.DIAMOND)){
            depth = 12;
            limit = 72;
        }
        else if (this.getTier().equals(Tiers.NETHERITE)){
            depth = 16;
            limit = 96;
        }
        else if(this.getTier().equals(TGTier.PURIFYING_TIER)){
            depth = 18;
            limit = 112;
        }
        else{
            depth = 18;
            limit = 112;
        }

        return BlockPos.breadthFirstTraversal(pos,depth,limit,(p2, p1) -> {
            Direction[] dirs2 = ALL_DIRS;
            int directionLength = dirs2.length;
            for(int foo1 = 0; foo1 < directionLength; ++foo1) {
                Direction direction = dirs2[foo1];
                p1.accept(p2.relative(direction));
            }
        },(blockPos1) -> {
            if(level.getBlockState(blockPos1).is(Blocks.AIR)){
                return true;
            }
            else{
                BlockState blockstate = level.getBlockState(blockPos1);
                Block block1 = blockstate.getBlock();
                if (block1 instanceof TallGrassBlock || block1 instanceof FlowerBlock || block1 instanceof DeadBushBlock || block1 instanceof CropBlock || block1 instanceof DoublePlantBlock) {
                    level.destroyBlock(blockPos1,true,null);
                    RandomSource rs = level.getRandom();
                    for(int i = 0; i < 3; ++i){
                        level.addParticle(ParticleTypes.TOTEM_OF_UNDYING, (double)blockPos1.getX() + 0.5, (double)blockPos1.getY() + 0.5, (double)blockPos1.getZ() + 0.5, (double)(rs.nextFloat() / 2.0F), 5.0E-5, (double)(rs.nextFloat() / 2.0F));
                    }
                    is.hurtAndBreak(1,e,LivingEntity.getSlotForHand(e.getUsedItemHand()));
                }
            }
            return true;
        }) > 1;
    }
}
