package net.rk.addon.fluid.fluidblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.rk.addon.fluid.TGFluids;

public class SludgeFluidBlock extends LiquidBlock {
    public int sludgeDamage = 1;

    public SludgeFluidBlock() {
        super(TGFluids.SLUDGE_FLUID_SOURCE.get(), Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .replaceable()
                .noCollission()
                .strength(100.0F)
                .pushReaction(PushReaction.DESTROY)
                .noLootTable()
                .liquid()
                .sound(SoundType.EMPTY)
        );
    }

    @Override
    public void entityInside(BlockState state, Level lvl, BlockPos bp, Entity ent1) {
        if(ent1 instanceof LivingEntity){
            if(!ent1.isInvulnerable()){
                ent1.hurt(lvl.damageSources().generic(), (float)this.sludgeDamage);
                ((LivingEntity) ent1).addEffect(new MobEffectInstance(
                        MobEffects.POISON,
                        70,
                        0,
                        true,
                        false,
                        false));
                ((LivingEntity) ent1).addEffect(new MobEffectInstance(
                        MobEffects.HUNGER,
                        70,
                        3,
                        true,
                        false,
                        false));
            }
        }
        super.entityInside(state,lvl,bp,ent1);
    }
}
