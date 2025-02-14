package net.rk.addon.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import java.util.List;

public class NetherishStone extends Block{
    public NetherishStone(Properties p) {
        super(p.strength(1F,20F).sound(SoundType.NETHERRACK));
    }

    @Override
    public float getSpeedFactor() {
        return 0.35F;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigs.netherish_stone.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
