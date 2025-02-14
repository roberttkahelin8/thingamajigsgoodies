package net.rk.addon.network.record;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.rk.addon.Thingamajigsgoodies;

public record CombinerUpdatePayload(
        BlockPos bp
) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<CombinerUpdatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Thingamajigsgoodies.MODID,"combiner_update"));

    public static final StreamCodec<FriendlyByteBuf, CombinerUpdatePayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, CombinerUpdatePayload::bp,
            CombinerUpdatePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
