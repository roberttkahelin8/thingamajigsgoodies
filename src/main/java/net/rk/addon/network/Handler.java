package net.rk.addon.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.rk.addon.Thingamajigsgoodies;
import net.rk.addon.network.packet.CombinerUpdatePacket;
import net.rk.addon.network.record.CombinerUpdatePayload;

public class Handler{
    public static void register(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar regex_reg = event.registrar(Thingamajigsgoodies.MODID);
        regex_reg.playToServer(CombinerUpdatePayload.TYPE,CombinerUpdatePayload.STREAM_CODEC, CombinerUpdatePacket.get()::handle);
    }
}
