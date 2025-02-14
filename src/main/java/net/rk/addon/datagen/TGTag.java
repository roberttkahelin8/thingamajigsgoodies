package net.rk.addon.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("deprecated")
public class TGTag{
    public static final TagKey<Fluid> PURIFIED_WATER_FLUID_TAG = thingamajigsFluidTag("purified_water_fluid");
    public static final TagKey<Fluid> SLUDGE_FLUID_TAG = thingamajigsFluidTag("sludge_fluid");

    public static final TagKey<Block> BALLOON_BLOCKS = thingamajigsBlockTag("balloon_blocks");
    public static final TagKey<Block> CAN_POP_BALLOONS = thingamajigsBlockTag("can_pop_balloons");

    public static final TagKey<Block> CAN_MELT = thingamajigsGoodiesBlockTag("can_melt");

    public static final TagKey<Block> PURIFYING_CAN_SHEAR = thingamajigsGoodiesBlockTag("purifying_can_shear");

    public static final TagKey<Item> BALLOON_ITEMS = thingamajigsItemTag("balloon_items");

    public static final TagKey<Item> SCYTHES = commonItemTag("scythes");

    public static final TagKey<Item> PURIFYING_BOW_PROJECTILE_ITEMS = thingamajigsGoodiesItemTag("purifying_bow_projectile_items");
    public static final TagKey<Item> PURIFYING_CROSSBOW_PROJECTILE_ITEMS = thingamajigsGoodiesItemTag("purifying_crossbow_projectile_items");

    //public static final TagKey<Item> ITEMT = thingamajigsItemTag("itemt");

    private static TagKey<Fluid> thingamajigsFluidTag(String name){
        return FluidTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigs", name));
    }

    private static TagKey<Block> thingamajigsBlockTag(String name){
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigs", name));
    }

    private static TagKey<Item> thingamajigsItemTag(String name){
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigs", name));
    }

    private static TagKey<Block> thingamajigsGoodiesBlockTag(String name){
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigsgoodies", name));
    }

    private static TagKey<Item> thingamajigsGoodiesItemTag(String name){
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("thingamajigsgoodies", name));
    }

    // default tag registry

    private static TagKey<Block> mcBlockTag(String name){
        return BlockTags.create(ResourceLocation.withDefaultNamespace(name));
    }

    private static TagKey<Item> mcItemTag(String name){
        return ItemTags.create(ResourceLocation.withDefaultNamespace(name));
    }

    private static TagKey<Fluid> mcFluidTag(String name){
        return FluidTags.create(ResourceLocation.withDefaultNamespace(name));
    }

    // common tag registry

    private static TagKey<Fluid> commonFluidTag(String name){
        return FluidTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }

    private static TagKey<Block> commonBlockTag(String name){
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }

    private static TagKey<Item> commonItemTag(String name){
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }
}

