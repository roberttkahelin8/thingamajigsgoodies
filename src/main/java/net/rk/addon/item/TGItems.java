package net.rk.addon.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.fluid.TGFluids;
import net.rk.addon.item.custom.*;
import net.rk.addon.item.tool.TGTier;

import java.util.List;

@SuppressWarnings("deprecated")
public class TGItems{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Thingamajigsgoodies.MODID);

    public static final DeferredHolder<Item, BucketItem> SLUDGE_FLUID_BUCKET = ITEMS.register("sludge_bucket",
            () -> new BucketItem(TGFluids.SLUDGE_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.sludge_bucket.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredHolder<Item, BucketItem> PURIFYING_FLUID_BUCKET = ITEMS.register("purifying_water_bucket",
            () -> new BucketItem(TGFluids.PURIFYING_WATER_FLUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_water_bucket.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredItem<Item> GOLDEN_APPLE_SHARD = ITEMS.register("golden_apple_shard",
            () -> new Item(new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> l, TooltipFlag tooltipFlag) {
                    l.add(Component.translatable("item.thingamajigsgoodies.golden_apple_shard.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredItem<Item> GLOBIZED_GOLDEN_APPLE_SHARD = ITEMS.register("globized_golden_apple_shard",
            () -> new Item(new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> l, TooltipFlag tooltipFlag) {
                    l.add(Component.translatable("item.thingamajigsgoodies.globized_golden_apple_shard.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredItem<Item> PURIFYING_NUGGET = ITEMS.register("purifying_nugget",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> l, TooltipFlag tooltipFlag) {
                    l.add(Component.translatable("item.thingamajigsgoodies.purifying_nugget.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredItem<Item> PURIFYING_INGOT = ITEMS.register("purifying_ingot",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> l, TooltipFlag tooltipFlag) {
                    l.add(Component.translatable("item.thingamajigsgoodies.purifying_ingot.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    public static final DeferredItem<Item> PURIFYING_GLOB = ITEMS.register("purifying_glob",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> l, TooltipFlag tooltipFlag) {
                    l.add(Component.translatable("item.thingamajigsgoodies.purifying_glob.desc")
                            .withStyle(ChatFormatting.GRAY));
                }
            });

    // balloon items
    public static final DeferredItem<Item> BALLOON_BLOCK_ITEM = ITEMS.register("balloon_block_item",
            () -> new BalloonItem(TGBlocks.BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> RED_BALLOON_BLOCK_ITEM = ITEMS.register("red_balloon_block_item",
            () -> new BalloonItem(TGBlocks.RED_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> ORANGE_BALLOON_BLOCK_ITEM = ITEMS.register("orange_balloon_block_item",
            () -> new BalloonItem(TGBlocks.ORANGE_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> YELLOW_BALLOON_BLOCK_ITEM = ITEMS.register("yellow_balloon_block_item",
            () -> new BalloonItem(TGBlocks.YELLOW_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> LIME_BALLOON_BLOCK_ITEM = ITEMS.register("lime_balloon_block_item",
            () -> new BalloonItem(TGBlocks.LIME_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> GREEN_BALLOON_BLOCK_ITEM = ITEMS.register("green_balloon_block_item",
            () -> new BalloonItem(TGBlocks.GREEN_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> LIGHT_BLUE_BALLOON_BLOCK_ITEM = ITEMS.register("light_blue_balloon_block_item",
            () -> new BalloonItem(TGBlocks.LIGHT_BLUE_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> BLUE_BALLOON_BLOCK_ITEM = ITEMS.register("blue_balloon_block_item",
            () -> new BalloonItem(TGBlocks.BLUE_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> CYAN_BALLOON_BLOCK_ITEM = ITEMS.register("cyan_balloon_block_item",
            () -> new BalloonItem(TGBlocks.CYAN_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> PURPLE_BALLOON_BLOCK_ITEM = ITEMS.register("purple_balloon_block_item",
            () -> new BalloonItem(TGBlocks.PURPLE_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> MAGENTA_BALLOON_BLOCK_ITEM = ITEMS.register("magenta_balloon_block_item",
            () -> new BalloonItem(TGBlocks.MAGENTA_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> PINK_BALLOON_BLOCK_ITEM = ITEMS.register("pink_balloon_block_item",
            () -> new BalloonItem(TGBlocks.PINK_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> BROWN_BALLOON_BLOCK_ITEM = ITEMS.register("brown_balloon_block_item",
            () -> new BalloonItem(TGBlocks.BROWN_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> LIGHT_GRAY_BALLOON_BLOCK_ITEM = ITEMS.register("light_gray_balloon_block_item",
            () -> new BalloonItem(TGBlocks.LIGHT_GRAY_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> GRAY_BALLOON_BLOCK_ITEM = ITEMS.register("gray_balloon_block_item",
            () -> new BalloonItem(TGBlocks.GRAY_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> BLACK_BALLOON_BLOCK_ITEM = ITEMS.register("black_balloon_block_item",
            () -> new BalloonItem(TGBlocks.BLACK_BALLOON_BLOCK.get(),new Item.Properties()));

    public static final DeferredItem<Item> TEAL_BALLOON_BLOCK_ITEM = ITEMS.register("teal_balloon_block_item",
            () -> new BalloonItem(TGBlocks.TEAL_BALLOON_BLOCK.get(),new Item.Properties()));

    // more poop junk stuff
    public static final DeferredItem<Item> POOP_HORN = ITEMS.register("poop_horn", () -> new PoopHorn((new Item.Properties())
            .stacksTo(1)));

    // weapons and tools
    public static final DeferredItem<Item> PURIFYING_SWORD = ITEMS.register("purifying_sword",
            () -> new PurifyingSword(TGTier.PURIFYING_TIER,
                    new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_PICKAXE = ITEMS.register("purifying_pickaxe",
            () -> new PurifyingPickaxe(TGTier.PURIFYING_TIER,
                    new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_AXE = ITEMS.register("purifying_axe",
            () -> new PurifyingAxe(TGTier.PURIFYING_TIER,
                    new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_SHOVEL = ITEMS.register("purifying_shovel",
            () -> new PurifyingShovel(TGTier.PURIFYING_TIER,
                    new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_HOE = ITEMS.register("purifying_hoe",
            () -> new PurifyingHoe(TGTier.PURIFYING_TIER,
                    new Item.Properties()));

    public static final DeferredItem<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new ScytheItem(Tiers.IRON,
                    new Item.Properties()));

    public static final DeferredItem<Item> DIAMOND_SCYTHE = ITEMS.register("diamond_scythe",
            () -> new ScytheItem(Tiers.DIAMOND,
                    new Item.Properties()));

    public static final DeferredItem<Item> NETHERITE_SCYTHE = ITEMS.register("netherite_scythe",
            () -> new ScytheItem(Tiers.NETHERITE,
                    new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_SCYTHE = ITEMS.register("purifying_scythe",
            () -> new ScytheItem(TGTier.PURIFYING_TIER,
                    new Item.Properties()){
                @Override
                public InteractionResult useOn(UseOnContext context) {
                    Level lvl = context.getLevel();
                    BlockPos bp = context.getClickedPos();
                    ItemStack is = context.getItemInHand();

                    if(lvl.getBlockState(bp).is(TGBlocks.PURIFYING_BLOCK)){
                        if(is.getDamageValue() <= 0){
                            is.setDamageValue(0);
                        }
                        else{
                            is.setDamageValue(is.getDamageValue() - 1);
                            float f1 = lvl.getRandom().nextFloat();
                            if(f1 < 0.95f){
                                f1 = 0.95f;
                            }
                            lvl.playSound(null,bp, SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.BLOCKS,0.75f,f1);
                        }
                    }
                    else{
                        return super.useOn(context); // allow normal functionality
                    }
                    return InteractionResult.PASS;
                }

                @Override
                public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
                    if(entity instanceof Player){
                        if(entity.getHealth() < entity.getMaxHealth()){
                            entity.addEffect(new MobEffectInstance(
                                    MobEffects.REGENERATION,
                                    32,
                                    5,
                                    true,
                                    false,
                                    false));
                        }
                    }
                    return super.onEntitySwing(stack, entity);
                }
            });

    public static final DeferredItem<Item> ENDER_BLOSSOM_SEEDS = ITEMS.register("ender_blossom_seeds",
            () -> new BlockItem(TGBlocks.ENDER_BLOSSOM_BLOCK.get(), new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_FISHING_ROD = ITEMS.register("purifying_fishing_rod",
            () -> new PurifyingFishingRod(new Item.Properties()
                    .fireResistant().rarity(Rarity.EPIC)
                    .durability(3065)
                    .stacksTo(1)));

    public static final DeferredItem<Item> FISHING_CATALYST = ITEMS.register("fishing_catalyst",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.fishing_catalyst.desc")
                            .withStyle(ChatFormatting.AQUA));
                }
            });

    public static final DeferredItem<Item> PURIFYING_SHEARS = ITEMS.register("purifying_shears",
            () -> new PurifyingShears(new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_BOW = ITEMS.register("purifying_bow",
            () -> new PurifyingBow(new Item.Properties()));

    public static final DeferredItem<Item> PURIFYING_CROSSBOW = ITEMS.register("purifying_crossbow",
            () -> new PurifyingCrossbow(new Item.Properties()));

    public static final DeferredItem<Item> CROSSBOW_CATALYST = ITEMS.register("crossbow_catalyst",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.crossbow_catalyst.desc")
                            .withStyle(ChatFormatting.AQUA));
                }
            });

    public static final DeferredItem<Item> BOW_CATALYST = ITEMS.register("bow_catalyst",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.bow_catalyst.desc")
                            .withStyle(ChatFormatting.AQUA));
                }
            });

    public static final DeferredItem<Item> SHEARS_CATALYST = ITEMS.register("shears_catalyst",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.shears_catalyst.desc")
                            .withStyle(ChatFormatting.AQUA));
                }
            });
}
