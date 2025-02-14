package net.rk.addon.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class PurifyingBookshelf extends Block{
    public PurifyingBookshelf(BlockBehaviour.Properties p) {
        super(p.strength(1.5F).sound(SoundType.WOOD).mapColor(MapColor.WOOD));
    }

    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return 0.5F;
    }
}
