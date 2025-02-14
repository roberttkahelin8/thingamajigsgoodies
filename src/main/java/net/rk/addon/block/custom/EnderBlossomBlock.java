package net.rk.addon.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.rk.addon.item.TGItems;

import java.util.List;

@SuppressWarnings("deprecated")
public class EnderBlossomBlock extends CropBlock implements BonemealableBlock {
    public static final MapCodec<EnderBlossomBlock> CODEC = simpleCodec(EnderBlossomBlock::new);

    @Override
    public MapCodec<? extends EnderBlossomBlock> codec() {
        return CODEC;
    }

    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age",0,MAX_AGE);

    public EnderBlossomBlock(Properties properties) {
        super(properties.strength(1.15f,20f).noOcclusion()
                .sound(SoundType.CANDLE).pushReaction(PushReaction.DESTROY).noCollission());
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE,0));
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge(){
        return MAX_AGE;
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.random, 1, 2);
    }

    @Override
    public int getAge(BlockState state) {
        return state.getValue(AGE);
    }

    @Override
    public BlockState getStateForAge(int age) {
        return this.defaultBlockState().setValue(AGE,age);
    }

    // remove light limit as this crop is magical
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return; // prevent loading when unloaded nf check
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(state, level, pos);
            if(level.getBlockState(pos.below()).is(Blocks.END_STONE) || level.getBlockState(pos.below()).is(Blocks.OBSIDIAN) || level.getBlockState(pos.below()).is(Blocks.BEDROCK)){
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos, state, random.nextInt((int)(4.0f / f) + 1) == 0)) {
                    level.setBlock(pos, this.getStateForAge(i + 1), 2);
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
            else{
                if (net.neoforged.neoforge.common.CommonHooks.canCropGrow(level, pos, state, random.nextInt((int)(125.0f / f) + 1) == 0)) {
                    level.setBlock(pos, this.getStateForAge(i + 1), 2);
                    net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }
    }

    // remove horrid light limit
    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        net.neoforged.neoforge.common.util.TriState soilDecision = level.getBlockState(pos.below()).canSustainPlant(level, pos.below(), net.minecraft.core.Direction.UP, state);
        if (!soilDecision.isDefault()) return soilDecision.isTrue();
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Ravager && net.neoforged.neoforge.event.EventHooks.canEntityGrief(level, entity)) {
            ((Ravager) entity).addEffect(new MobEffectInstance(
                    MobEffects.LEVITATION,
                    72,
                    5,
                    true,
                    false,
                    false));
        }

        if(!level.isClientSide && entity instanceof LivingEntity){
            if(level.getBlockState(pos.below()).is(Blocks.END_STONE) || level.getBlockState(pos.below()).is(Blocks.OBSIDIAN) || level.getBlockState(pos.below()).is(Blocks.BEDROCK)){
                return;
            }
            // sweet berry bush basis, changed to be slower and different damage type
            entity.makeStuckInBlock(state, new Vec3(0.80001, 0.80, 0.80001));
            if (!entity.isInvulnerable() && state.getValue(AGE) > 0 && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                double d0 = Math.abs(entity.getX() - entity.xOld);
                double d1 = Math.abs(entity.getZ() - entity.zOld);
                if (d0 >= 0.003000001 || d1 >= 0.003000001) {
                    entity.hurt(level.damageSources().cactus(),1);
                }
            }
        }
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return TGItems.ENDER_BLOSSOM_SEEDS.asItem();
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return TGItems.ENDER_BLOSSOM_SEEDS.toStack();
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return TGItems.ENDER_BLOSSOM_SEEDS.toStack();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos){
        boolean okState = false;
        boolean obsOk = state.is(Blocks.OBSIDIAN);
        boolean bedrkOk = state.is(Blocks.BEDROCK);
        boolean endstnOk = state.is(Blocks.END_STONE);

        if(state.getBlock() instanceof net.minecraft.world.level.block.FarmBlock || obsOk || bedrkOk || endstnOk){
            okState = true;
        }
        return okState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(EnderBlossomBlock.AGE);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.ender_blossom_seeds.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
