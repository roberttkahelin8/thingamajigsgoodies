package net.rk.addon.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import net.rk.addon.datagen.TGTag;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PurifyingBow extends BowItem {
    public PurifyingBow(Properties properties) {
        super(properties.durability(3385).fireResistant()
                .rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_bow.desc")
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_bow.desc_xtra")
                .withStyle(ChatFormatting.DARK_PURPLE));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 74000;
    }

    // items that are ammo
    public static final Predicate<ItemStack> ANY_OF_VANILLA = (stck) -> stck.is(TGTag.PURIFYING_BOW_PROJECTILE_ITEMS);

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ANY_OF_VANILLA;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles(ItemStack stack) {
        return ANY_OF_VANILLA;
    }

    @Override
    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        float f = EnchantmentHelper.processProjectileSpread(level, weapon, shooter, 10.0F);
        float f1 = projectileItems.size() == 1 ? 0.0F : 2.0F * f / (float)(projectileItems.size() - 1);
        float f2 = (float)((projectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;

        for(int i = 0; i < projectileItems.size(); ++i) {
            ItemStack itemstack = (ItemStack)projectileItems.get(i);

            if (!itemstack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                f3 = -f3;
                Projectile projectile = this.createProjectile(level, shooter, weapon, itemstack, isCrit);
                this.shootProjectile(shooter, projectile, i, velocity, inaccuracy, f4, target);
                level.addFreshEntity(projectile);
                weapon.hurtAndBreak(this.getDurabilityUse(itemstack), shooter, LivingEntity.getSlotForHand(hand));

                if (weapon.isEmpty()) {
                    break;
                }
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            ItemStack itemstack = player.getProjectile(stack);
            if (!itemstack.isEmpty()) {
                int i = this.getUseDuration(stack, entityLiving) - timeLeft;
                i = EventHooks.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
                if (i < 0) {
                    return;
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = draw(stack, itemstack, player);
                    if (level instanceof ServerLevel) {
                        ServerLevel serverlevel = (ServerLevel)level;
                        if (!list.isEmpty()) {
                            this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list,
                                    f * 3.0F, 0.0F, true, null);
                        }
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }

    }

    protected static List<ItemStack> draw(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        if (ammo.isEmpty()) {
            return List.of();
        } else {
            Level var5 = shooter.level();
            int var10000;
            if (var5 instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)var5;
                var10000 = EnchantmentHelper.processProjectileCount(serverlevel, weapon, shooter, 5);
            } else {
                var10000 = 1;
            }

            int i = var10000;
            List<ItemStack> list = new ArrayList(i);
            ItemStack itemstack1 = ammo.copy();

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = BowItem.useAmmo(weapon, j == 0 ? ammo : itemstack1, shooter, true);
                if (!itemstack.isEmpty()) {
                    list.add(itemstack);
                }
            }
            return list;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();
        InteractionResultHolder<ItemStack> ret = EventHooks.onArrowNock(itemstack, level, player, hand, flag);
        RandomSource rs = level.getRandom();
        if(flag && rs.nextIntBetweenInclusive(1,100) <= 7){
            if(level instanceof ServerLevel){
                player.addEffect(new MobEffectInstance(
                        MobEffects.DAMAGE_BOOST,
                        120,
                        4,
                        true,
                        false,
                        false));
            }
        }
        if(!flag){
            return InteractionResultHolder.fail(itemstack);
        }
        else{
            player.startUsingItem(hand);
            return InteractionResultHolder.pass(itemstack);
        }
    }

    @Override
    public int getDefaultProjectileRange() {
        return 32;
    }
}
