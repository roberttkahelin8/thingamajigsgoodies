package net.rk.addon.fluid.types;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.fluid.TGFluids;
import net.rk.addon.item.TGItems;

public abstract class PurifyingFluid extends BaseFlowingFluid{
    protected PurifyingFluid(Properties properties) {
        super(properties);
    }

    public static final Properties PROPERTIES = new Properties(
            TGFluids.PURIFYING_WATER_FLUID_TYPE,
            TGFluids.PURIFYING_WATER_FLUID_FLOWING,
            TGFluids.PURIFYING_WATER_FLUID_SOURCE
    ).bucket(TGItems.PURIFYING_FLUID_BUCKET).block(TGBlocks.PURIFYING_FLUID_BLOCK)
            .levelDecreasePerBlock(1)
            .slopeFindDistance(5);

    // poof and sfx for water
    @Override
    public void animateTick(Level level, BlockPos bp, FluidState flst, RandomSource rnds) {
        if (!flst.isSource() && !flst.getValue(FALLING)) {
            if (rnds.nextInt(64) == 0) {
                level.playLocalSound(
                        (double)bp.getX() + 0.5D,
                        (double)bp.getY() + 0.5D,
                        (double)bp.getZ() + 0.5D,
                        SoundEvents.WATER_AMBIENT,
                        SoundSource.BLOCKS,
                        rnds.nextFloat() * 0.25F + 0.75F,
                        rnds.nextFloat() + 0.5F, false);
            }
        }
        else if (rnds.nextInt(94) == 0) {
            level.addParticle(ParticleTypes.CLOUD,
                    (double)bp.getX() + rnds.nextDouble(),
                    (double)bp.getY() + rnds.nextDouble(),
                    (double)bp.getZ() + rnds.nextDouble(),
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public Fluid getFlowing() {
        return TGFluids.PURIFYING_WATER_FLUID_FLOWING.get();
    }

    @Override
    public Fluid getSource() {
        return TGFluids.PURIFYING_WATER_FLUID_SOURCE.get();
    }

    @Override
    public Item getBucket() {
        return TGItems.PURIFYING_FLUID_BUCKET.get();
    }

    @Override
    protected boolean canConvertToSource(Level pLevel) {
        return false;
    }

    public static class Flowing extends PurifyingFluid {
        public Flowing() {
            super(PROPERTIES);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> pBuilder) {
            super.createFluidStateDefinition(pBuilder);
            pBuilder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState pState) {
            return pState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState pState) {
            return false;
        }
    }

    public static class Source extends PurifyingFluid {
        public Source() {
            super(PROPERTIES);
        }

        @Override
        public int getAmount(FluidState pState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState pState) {
            return true;
        }
    }
}
