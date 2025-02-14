package net.rk.addon;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Thingamajigsgoodies.MODID, bus = EventBusSubscriber.Bus.MOD)
public class TGConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        //
    }
}
