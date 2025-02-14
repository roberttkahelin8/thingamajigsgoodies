package net.rk.addon.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.rk.addon.block.TGBlocks;

import java.util.List;

public class PurifyingSword extends SwordItem {
    public PurifyingSword(Tier tier, Properties properties) {
        super(tier, properties.rarity(Rarity.EPIC).fireResistant().attributes(
                SwordItem.createAttributes(Tiers.NETHERITE, 4, -0.75F)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_sword.desc")
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public AABB getSweepHitBox(ItemStack stack, Player player, Entity target){
        return new AABB(-5,-5,-5,5,5,5);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level lvl = context.getLevel();
        BlockPos bp = context.getClickedPos();
        ItemStack is = context.getItemInHand();

        if(lvl.getBlockState(bp).is(TGBlocks.PURIFYING_BLOCK)){
            if(is.getDamageValue() <= 0){
                is.setDamageValue(0);
            }
            else{
                is.setDamageValue(is.getDamageValue() - 1);
                float f1 = lvl.getRandom().nextFloat();
                if(f1 < 0.95f){
                    f1 = 0.95f;
                }
                lvl.playSound(null,bp,SoundEvents.ILLUSIONER_CAST_SPELL,SoundSource.BLOCKS,0.75f,f1);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_SHIELD_ACTIONS.contains(itemAbility);
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 82000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if(entity instanceof Player){
            if(entity.getHealth() < entity.getMaxHealth()){
                entity.addEffect(new MobEffectInstance(
                        MobEffects.REGENERATION,
                        32,
                        5,
                        true,
                        false,
                        false));
            }
        }
        return super.onEntitySwing(stack, entity);
    }
}
