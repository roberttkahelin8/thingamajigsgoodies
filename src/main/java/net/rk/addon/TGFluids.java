package net.rk.addon.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.fluid.types.PurifyingFluid;
import net.rk.addon.fluid.types.PurifyingFluidType;
import net.rk.addon.fluid.types.SludgeFluid;
import net.rk.addon.fluid.types.SludgeFluidType;

public class TGFluids{
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES,
                    Thingamajigsgoodies.MODID);
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID,
                    Thingamajigsgoodies.MODID);


    public static final DeferredHolder<FluidType, FluidType> SLUDGE_FLUID_TYPE = FLUID_TYPES.register("sludge_fluid",
            SludgeFluidType::new);
    public static final DeferredHolder<Fluid, SludgeFluid> SLUDGE_FLUID_FLOWING = FLUIDS.register("sludge_flowing",
            SludgeFluid.Flowing::new);
    public static final DeferredHolder<Fluid, SludgeFluid> SLUDGE_FLUID_SOURCE = FLUIDS.register("sludge_source",
            SludgeFluid.Source::new);


    public static final DeferredHolder<FluidType, FluidType> PURIFYING_WATER_FLUID_TYPE = FLUID_TYPES.register("purifying_water_fluid",
            PurifyingFluidType::new);
    public static final DeferredHolder<Fluid, PurifyingFluid> PURIFYING_WATER_FLUID_FLOWING = FLUIDS.register("purifying_water_flowing",
            PurifyingFluid.Flowing::new);
    public static final DeferredHolder<Fluid, PurifyingFluid> PURIFYING_WATER_FLUID_SOURCE = FLUIDS.register("purifying_water_source",
            PurifyingFluid.Source::new);
}
