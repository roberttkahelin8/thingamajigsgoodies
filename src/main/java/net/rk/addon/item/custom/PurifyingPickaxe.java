package net.rk.addon.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.rk.addon.block.TGBlocks;

import java.util.List;

public class PurifyingPickaxe extends PickaxeItem{
    public PurifyingPickaxe(Tier tier1, Properties p){
        super(tier1,p.rarity(Rarity.EPIC).fireResistant().attributes(
                PickaxeItem.createAttributes(Tiers.NETHERITE,6,-1.45f)
                .withModifierAdded(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE,
                        new AttributeModifier(ResourceLocation.
                                withDefaultNamespace("explosion_knockback_resistance"),
                                0.35D, AttributeModifier.Operation.ADD_VALUE)
                        ,EquipmentSlotGroup.ANY)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_pickaxe.desc")
                .withStyle(ChatFormatting.GRAY));
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
                lvl.playSound(null,bp, SoundEvents.ILLUSIONER_CAST_SPELL, SoundSource.BLOCKS,0.75f,f1);
            }
        }

        return InteractionResult.PASS;
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
