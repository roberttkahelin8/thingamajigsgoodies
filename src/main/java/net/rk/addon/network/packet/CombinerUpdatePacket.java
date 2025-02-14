package net.rk.addon.network.packet;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.rk.addon.network.record.CombinerUpdatePayload;

public class CombinerUpdatePacket{
    public static final CombinerUpdatePacket INSTANCE = new CombinerUpdatePacket();

    public static CombinerUpdatePacket get(){return INSTANCE;}

    public void handle(final CombinerUpdatePayload payload, final IPayloadContext context){

    }
}
