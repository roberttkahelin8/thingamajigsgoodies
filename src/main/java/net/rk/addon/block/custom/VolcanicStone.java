package net.rk.addon.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class VolcanicStone extends Block{
    public VolcanicStone(Properties p) {
        super(p.strength(2F,75F).sound(SoundType.BASALT));
    }

    @Override
    public void stepOn(Level lvl, BlockPos bp, BlockState bs, Entity ent1) {
        if(ent1 instanceof LivingEntity){
            ent1.hurt(lvl.damageSources().hotFloor(),3.0F);
            ((LivingEntity) ent1).addEffect(new MobEffectInstance(
                    MobEffects.WEAKNESS,
                    7,
                    0,
                    true,
                    false,
                    false)
            );
            ent1.igniteForSeconds(1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigs.volcanic_stone.desc"));
    }
}
