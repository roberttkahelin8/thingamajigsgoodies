package net.rk.addon;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.addon.block.TGBlocks;
import net.rk.addon.datagen.custom.CombinerRecipe;
import net.rk.addon.fluid.TGFluids;
import net.rk.addon.item.TGItemProperties;
import net.rk.addon.item.TGItems;
import net.rk.addon.menu.TGMenu;
import net.rk.addon.screen.CombinerScreen;
import net.rk.thingamajigs.Thingamajigs;
import net.rk.thingamajigs.xtras.TColors;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.slf4j.Logger;

import java.util.function.Supplier;

@Mod(Thingamajigsgoodies.MODID)
public class Thingamajigsgoodies{
    public static final String MODID = "thingamajigsgoodies";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CMT_TG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TG_TAB = CMT_TG.register(
            "tg_main_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.thingamajigsgoodies"))
                    .withTabsBefore(Thingamajigs.MAIN_CTAB.getKey())
                    .icon(() -> TGItems.GOLDEN_APPLE_SHARD.get().getDefaultInstance())
                    .backgroundTexture(ResourceLocation.fromNamespaceAndPath("thingamajigs","textures/gui/thingamajigsitems.png"))
                    .build()
    );

    // recipe, recipe serializer and recipe type registrars
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE,MODID);

    public static final Supplier<RecipeType<CombinerRecipe>> COMBINING_RECIPE_TYPE = RECIPE_TYPES.register("combining", () ->
            RecipeType.simple(ResourceLocation.fromNamespaceAndPath(MODID, "combining")));

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER,Thingamajigsgoodies.MODID);

    public static final Supplier<CombinerRecipe.Serializer> COMBINING_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register("combining_recipe_serializer",CombinerRecipe.Serializer::new);



    private static final boolean DEBUG_MODE = false;

    public static boolean creating = false;
    public static boolean werok = false;

    public Thingamajigsgoodies(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        if(ModList.get().isLoaded("create")){
            creating = true;
        }
        if(ModList.get().isLoaded("thingamajigs")){
            werok = true;
            LOGGER.info("Detected base required mod exists.");
        }

        TGBlocks.BLOCKS.register(modEventBus);
        TGItems.ITEMS.register(modEventBus);
        TGFluids.FLUID_TYPES.register(modEventBus);
        TGFluids.FLUIDS.register(modEventBus);
        //TGBlockEntity.register(modEventBus);
        TGMenu.register(modEventBus);

        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);

        CMT_TG.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        if(FMLLoader.getDist() == Dist.CLIENT){
            modEventBus.addListener(TGClientEvents::setupMenuTypes);
            modEventBus.addListener(TGClientEvents::clientSetup);
            modEventBus.addListener(TGClientEvents::clientExtensions);
            modEventBus.addListener(TGClientEvents::blockColorSetup);
            modEventBus.addListener(TGClientEvents::itemColorSetup);
        }
        else{
            LOGGER.info("TGoodies running in server mode");
        }

        //modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == TG_TAB.getKey()){
            // joke or instrument items
            event.accept(TGItems.POOP_HORN.asItem());
            //
            event.accept(TGItems.ENDER_BLOSSOM_SEEDS.asItem());
            event.accept(TGBlocks.SPOOKY_STONE.asItem());
            event.accept(TGBlocks.BLUEBERRY_STONE.asItem());
            event.accept(TGBlocks.NETHERISH_STONE.asItem());
            event.accept(TGBlocks.VOLCANIC_STONE.asItem());
            event.accept(TGBlocks.CHARGED_VOLCANIC_STONE.asItem());
            // crafting items and others
            event.accept(TGItems.GOLDEN_APPLE_SHARD.asItem());
            event.accept(TGItems.GLOBIZED_GOLDEN_APPLE_SHARD.asItem());
            event.accept(TGItems.PURIFYING_NUGGET.asItem());
            event.accept(TGItems.PURIFYING_INGOT.asItem());
            event.accept(TGItems.PURIFYING_GLOB.asItem());
            event.accept(TGBlocks.PURIFYING_BLOCK.asItem());
            //
            event.accept(TGItems.BOW_CATALYST.asItem());
            event.accept(TGItems.CROSSBOW_CATALYST.asItem());
            event.accept(TGItems.FISHING_CATALYST.asItem());
            event.accept(TGItems.SHEARS_CATALYST.asItem());
            //
            event.accept(TGItems.PURIFYING_SWORD.asItem());
            event.accept(TGItems.PURIFYING_PICKAXE.asItem());
            event.accept(TGItems.PURIFYING_AXE.asItem());
            event.accept(TGItems.PURIFYING_SHOVEL.asItem());
            event.accept(TGItems.PURIFYING_HOE.asItem());
            event.accept(TGItems.PURIFYING_BOW.asItem());
            event.accept(TGItems.PURIFYING_CROSSBOW.asItem());
            event.accept(TGItems.PURIFYING_FISHING_ROD.asItem());
            event.accept(TGItems.PURIFYING_SHEARS.asItem());
            // scythes
            event.accept(TGItems.IRON_SCYTHE.asItem());
            event.accept(TGItems.DIAMOND_SCYTHE.asItem());
            event.accept(TGItems.NETHERITE_SCYTHE.asItem());
            event.accept(TGItems.PURIFYING_SCYTHE.asItem());
            // buckets
            event.accept(TGItems.SLUDGE_FLUID_BUCKET.get().asItem());
            event.accept(TGItems.PURIFYING_FLUID_BUCKET.get().asItem());
            // functional machines and such
            event.accept(TGBlocks.CONVERTER.get().asItem());
            event.accept(TGBlocks.COMBINER.asItem());
            event.accept(TGBlocks.MOVING_PROBABLE_BLOCK.asItem());
            // blocks
            event.accept(TGBlocks.BYPRODUCT.asItem());
            event.accept(TGBlocks.PURIFYING_BOOKSHELF.asItem());
            // redstone components
            event.accept(TGBlocks.TWO_STATE_BLOCK.asItem());
            event.accept(TGBlocks.SPRING.get().asItem());
            // balloon block items
            event.accept(TGItems.BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.LIGHT_GRAY_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.GRAY_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.BLACK_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.BROWN_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.RED_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.ORANGE_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.YELLOW_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.LIME_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.GREEN_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.TEAL_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.CYAN_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.LIGHT_BLUE_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.BLUE_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.PURPLE_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.MAGENTA_BALLOON_BLOCK_ITEM.get());
            event.accept(TGItems.PINK_BALLOON_BLOCK_ITEM.get());
        }
    }

    /*
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
    }*/

    // client stuff class
    public static class TGClientEvents {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            TGItemProperties.addCustomProperties();
        }

        public static void setupMenuTypes(RegisterMenuScreensEvent event){
            event.register(TGMenu.COMBINER_MENU.get(),CombinerScreen::new);
        }

        @SubscribeEvent
        private static void blockColorSetup(RegisterColorHandlersEvent.Block event){
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getWhite(),
                    TGBlocks.BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getLightGray(),
                    TGBlocks.LIGHT_GRAY_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getGray(),
                    TGBlocks.GRAY_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getBlack(),
                    TGBlocks.BLACK_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getBrown(),
                    TGBlocks.BROWN_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(1),
                    TGBlocks.RED_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(2),
                    TGBlocks.ORANGE_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(3),
                    TGBlocks.YELLOW_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(4),
                    TGBlocks.LIME_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(5),
                    TGBlocks.GREEN_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(6),
                    TGBlocks.CYAN_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(7),
                    TGBlocks.LIGHT_BLUE_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(8),
                    TGBlocks.BLUE_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(9),
                    TGBlocks.PURPLE_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(10),
                    TGBlocks.MAGENTA_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getColorFromList(11),
                    TGBlocks.PINK_BALLOON_BLOCK.get()
            );
            event.register((blockState,tintGetter,blockPos,i) ->
                            TColors.getTeal(),
                    TGBlocks.TEAL_BALLOON_BLOCK.get()
            );
        }

        @SubscribeEvent
        private static void itemColorSetup(RegisterColorHandlersEvent.Item event){
            event.register((itemStack,i) -> TColors.getLightGray(),
                    TGItems.LIGHT_GRAY_BALLOON_BLOCK_ITEM.asItem()
            );
            event.register((itemStack,i) -> TColors.getGray(),
                    TGItems.GRAY_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getBlack(),
                    TGItems.BLACK_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getBrown(),
                    TGItems.BROWN_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(1),
                    TGItems.RED_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(2),
                    TGItems.ORANGE_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(3),
                    TGItems.YELLOW_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(4),
                    TGItems.LIME_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(5),
                    TGItems.GREEN_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(6),
                    TGItems.CYAN_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(7),
                    TGItems.LIGHT_BLUE_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(8),
                    TGItems.BLUE_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(9),
                    TGItems.PURPLE_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(10),
                    TGItems.MAGENTA_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getColorFromList(11),
                    TGItems.PINK_BALLOON_BLOCK_ITEM.get().asItem()
            );
            event.register((itemStack,i) -> TColors.getTeal(),
                    TGItems.TEAL_BALLOON_BLOCK_ITEM.asItem()
            );
        }


        @SubscribeEvent
        public static void clientExtensions(RegisterClientExtensionsEvent event){
            if(DEBUG_MODE){
                LOGGER.info("TGoodies c-extensions are being setup.");
                LOGGER.info("TGoodies is doing fltype reg setup.");
            }

            final ResourceLocation OVERLAY = ResourceLocation.parse("thingamajigsgoodies:textures/misc/overlay.png");

            event.registerFluidType(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return ResourceLocation.parse("thingamajigsgoodies:block/sludge_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return ResourceLocation.parse("thingamajigsgoodies:block/sludge_flow");
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return OVERLAY;
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return ResourceLocation.parse("thingamajigsgoodies:textures/misc/sludge_underwater.png");
                }

                @Override
                public int getTintColor() {
                    return 0xFFFFFFFF;
                }

                @Override
                public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                    return 0xFFFFFFFF;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    return new Vector3f((130f / 255f),(85f / 255f),(15f / 255f)); // sludge fog color
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    RenderSystem.setShaderFogStart(1F);
                    RenderSystem.setShaderFogEnd(10F); // fog starts at this distance
                }
            },TGFluids.SLUDGE_FLUID_TYPE.get());

            event.registerFluidType(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return ResourceLocation.parse("thingamajigsgoodies:block/purifying_water_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return ResourceLocation.parse("thingamajigsgoodies:block/purifying_water_flow");
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return OVERLAY;
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return ResourceLocation.parse("thingamajigsgoodies:textures/misc/purifying_water_underwater.png");
                }

                @Override
                public int getTintColor() {
                    return 0xDCFFFFFF;
                }

                @Override
                public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                    return 0xDCFFFFFF;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                    float RED = 35f / 255f;
                    float GREEN = 230f / 255f;
                    float BLUE = 254f / 255f;
                    Vector3f fogColor = new Vector3f(RED,GREEN,BLUE);
                    return fogColor; // purifying water fog color
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    RenderSystem.setShaderFogStart(1F);
                    RenderSystem.setShaderFogEnd(20F); // fog starts at this distance
                }
            },TGFluids.PURIFYING_WATER_FLUID_TYPE.get());

        }
    }
}
