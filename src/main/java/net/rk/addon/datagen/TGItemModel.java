package net.rk.addon.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.TGBlocks;

public class TGItemModel extends ItemModelProvider{
    public TGItemModel(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Thingamajigsgoodies.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockAll(TGBlocks.SPOOKY_STONE.asItem(),"thingamajigsgoodies:block/stone/spooky_stone");
        blockAll(TGBlocks.BLUEBERRY_STONE.asItem(),"thingamajigsgoodies:block/stone/blueberry_stone");
        blockAll(TGBlocks.CHARGED_VOLCANIC_STONE.asItem(),"thingamajigsgoodies:block/stone/charged_volcanic_stone");
        blockAll(TGBlocks.VOLCANIC_STONE.asItem(),"thingamajigsgoodies:block/stone/volcanic_stone");
        blockAll(TGBlocks.NETHERISH_STONE.asItem(),"thingamajigsgoodies:block/stone/netherish_stone");
    }

    private ItemModelBuilder blockAll(Item item, String textureLocation){
        return withExistingParent(item.toString(),
                ResourceLocation.withDefaultNamespace("block/cube_all")).texture("all",
                ResourceLocation.parse(textureLocation));
    }
}
