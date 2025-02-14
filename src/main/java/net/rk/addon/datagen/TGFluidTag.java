package net.rk.addon.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.fluid.TGFluids;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TGFluidTag extends FluidTagsProvider {
    public TGFluidTag(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Thingamajigsgoodies.MODID, existingFileHelper);
    }

    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TGTag.PURIFIED_WATER_FLUID_TAG).add(TGFluids.PURIFYING_WATER_FLUID_SOURCE.get(), TGFluids.PURIFYING_WATER_FLUID_FLOWING.get());
        this.tag(TGTag.SLUDGE_FLUID_TAG).add(TGFluids.SLUDGE_FLUID_SOURCE.get(), TGFluids.SLUDGE_FLUID_FLOWING.get());
    }
}
