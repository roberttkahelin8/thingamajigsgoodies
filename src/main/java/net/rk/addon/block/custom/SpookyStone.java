package net.rk.addon.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SpookyStone extends Block{
    public SpookyStone(Properties p) {
        super(p.strength(1.25F,25F).sound(SoundType.DEEPSLATE));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("block.thingamajigs.spooky_stone.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void stepOn(Level lvl, BlockPos bp, BlockState bs, Entity ent1) {
        if(ent1 instanceof Zombie ||
                ent1 instanceof Blaze || ent1 instanceof Hoglin || ent1 instanceof Zoglin || ent1 instanceof Piglin ||
                ent1 instanceof Skeleton || ent1 instanceof Stray || ent1 instanceof WitherSkeleton ||
                ent1 instanceof Creeper ||
                ent1 instanceof Raider ||
                ent1 instanceof Endermite || ent1 instanceof Silverfish || ent1 instanceof Breeze){
            ((Mob) ent1).addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED,
                    7,
                    3,
                    true,
                    false,
                    false)
            );
            ((Mob) ent1).addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_BOOST,
                    7,
                    1,
                    true,
                    false,
                    false)
            );
        }
    }
}
