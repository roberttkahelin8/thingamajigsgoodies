package net.rk.addon.entity.blockentity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rk.addon.Thingamajigsgoodies;

public class TGBlockEntity{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, Thingamajigsgoodies.MODID);

    /*
    public static final Supplier<BlockEntityType<CombinerBE>> COMBINER_BE = BLOCK_ENTITIES.register(
            "combiner_be",() ->
                    BlockEntityType.Builder.of(CombinerBE::new, TGBlocks.COMBINER.get())
                            .build(null));*/

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
