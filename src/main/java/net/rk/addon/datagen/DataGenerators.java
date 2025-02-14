package net.rk.addon.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.rk.addon.Thingamajigsgoodies;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Thingamajigsgoodies.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(),
                new TGBlockModel(packOutput,event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(),
                new TGItemModel(packOutput,event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(TGLoot::new, LootContextParamSets.BLOCK)),lookupProvider));

        event.getGenerator().addProvider(event.includeServer(), new TGFluidTag(packOutput,
                lookupProvider,event.getExistingFileHelper()));

        TGBlockTag blockTags = new TGBlockTag(packOutput, lookupProvider, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);

        event.getGenerator().addProvider(event.includeServer(),
                new TGItemTag(packOutput, lookupProvider, blockTags.contentsGetter(),event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(),new TGRecipe(packOutput,lookupProvider));
    }
}
