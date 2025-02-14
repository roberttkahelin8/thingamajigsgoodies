package net.rk.addon.fluid.types;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.rk.thingamajigs.xtras.TSoundEvent;

public class SludgeFluidType extends FluidType{
    public SludgeFluidType() {
        super(Properties.create()
                .descriptionId("block.thingamajigs.sludge")
                .fallDistanceModifier(0F)
                .canExtinguish(true)
                .supportsBoating(true)
                .canHydrate(false)
                .canConvertToSource(false)
                .density(200)
                .viscosity(1800)
                .temperature(120)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .sound(SoundActions.FLUID_VAPORIZE, TSoundEvent.POOP_BREAK.get())
                .rarity(Rarity.COMMON));
    }

    @Override
    public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
        return false;
    }
}
