package net.rk.addon.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.TGBlocks;

public class TGBlockModel extends BlockModelProvider {
    public TGBlockModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Thingamajigsgoodies.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        allSidedBlock(TGBlocks.SPOOKY_STONE,"thingamajigsgoodies:block/stone/spooky_stone");
        allSidedBlock(TGBlocks.BLUEBERRY_STONE,"thingamajigsgoodies:block/stone/blueberry_stone");
        allSidedBlock(TGBlocks.NETHERISH_STONE,"thingamajigsgoodies:block/stone/netherish_stone");
        allSidedBlock(TGBlocks.VOLCANIC_STONE,"thingamajigsgoodies:block/stone/volcanic_stone");
        allSidedBlock(TGBlocks.CHARGED_VOLCANIC_STONE,"thingamajigsgoodies:block/stone/charged_volcanic_stone");
    }

    private BlockModelBuilder allSidedBlock(DeferredBlock<Block> block, String textureLocation){
        return withExistingParent(block.getId().getPath(),
                ResourceLocation.parse("minecraft:block/cube_all"))
                .texture("all", ResourceLocation.parse(textureLocation));
    }
}
