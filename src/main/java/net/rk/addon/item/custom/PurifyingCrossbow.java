package net.rk.addon.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.rk.addon.datagen.TGTag;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class PurifyingCrossbow extends CrossbowItem {
    public PurifyingCrossbow(Properties p) {
        super(p.rarity(Rarity.EPIC).fireResistant().durability(3466)
                .component(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY)
                .stacksTo(1));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player){
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(
                Optional.of(Potions.LONG_POISON), Optional.of(143942),
                List.of(
                        new MobEffectInstance(MobEffects.WEAKNESS,100,3),
                        new MobEffectInstance(MobEffects.DIG_SLOWDOWN,100,3),
                        new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,100,3)
                )
        ));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_crossbow.desc")
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("item.thingamajigsgoodies.purifying_crossbow.desc_xtra")
                .withStyle(ChatFormatting.DARK_PURPLE));
    }

    public static final Predicate<ItemStack> ANY_OF_VANILLA_PLUS = (stck) -> stck.is(TGTag.PURIFYING_CROSSBOW_PROJECTILE_ITEMS);

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles(ItemStack stack) {
        return ANY_OF_VANILLA_PLUS;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ANY_OF_VANILLA_PLUS;
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        if (ammo.is(Items.FIREWORK_ROCKET)) {
            return new FireworkRocketEntity(level, ammo, shooter, shooter.getX(), shooter.getEyeY() - 0.15000000596046448, shooter.getZ(), true);
        }
        else if(ammo.is(Items.SNOWBALL)){
            Snowball sb = new Snowball(level,shooter);
            sb.setGlowingTag(true);
            return sb;
        }
        else if(ammo.is(Items.SPLASH_POTION)){
            ThrownPotion tp = new ThrownPotion(level,shooter){};
            return tp;
        }
        else if(ammo.is(Items.FIRE_CHARGE)){
            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(shooter.getLookAngle().y * 0.017453292F), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);

            SmallFireball fb = new SmallFireball(level,shooter,new Vec3(vec31.toVector3f().rotate(quaternionf)));
            fb.setPos(shooter.getX(),shooter.getEyeY() + 0.10000001,shooter.getZ());
            return fb;
        }
        else if(ammo.is(Items.EGG)){
            ThrownEgg te = new ThrownEgg(level,shooter);
            te.setRemainingFireTicks(200);
            return te;
        }
        else if(ammo.is(Items.WIND_CHARGE)){
            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(shooter.getLookAngle().y * 0.017453292F), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);

            WindCharge wc = new WindCharge(level,shooter.getX(),shooter.getY(),shooter.getZ(),new Vec3(vec31.toVector3f().rotate(quaternionf)));
            wc.setPos(shooter.getX(),shooter.getEyeY() + 0.10000001,shooter.getZ());
            return wc;
        }
        else if(ammo.is(Items.SHULKER_SHELL)){
            boolean successfullyFoundEntity = false;

            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(shooter.getLookAngle().y * 0.017453292F), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);
            ShulkerBullet wc = null;
            LivingEntity ent = null;

            if(level instanceof ServerLevel){
                AABB bounds = new AABB(shooter.getX() - 2.0D,shooter.getY() - 2.0D,shooter.getZ() - 2.0D,
                        shooter.getZ() + 2.0D, shooter.getY() + 2.0, shooter.getZ() + 2.0);
                ent = level.getNearestEntity(Monster.class,
                        TargetingConditions.DEFAULT,
                        null,
                        shooter.getX(),
                        shooter.getY(),
                        shooter.getZ(),
                        bounds.inflate(16.0D));
                if(ent instanceof Monster){
                    successfullyFoundEntity = true;
                }
            }
            //
            if(successfullyFoundEntity){
                wc = new ShulkerBullet(level,shooter,ent,Direction.Axis.Y);
                wc.setPos(shooter.getX(),shooter.getEyeY() + 0.10000001,shooter.getZ());
                return wc;
            }
            else{
                Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, true);
                if (projectile instanceof AbstractArrow) {
                    AbstractArrow abstractarrow = (AbstractArrow)projectile;
                    abstractarrow.setRemainingFireTicks(400);
                    abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
                }
                return projectile;
            }
        }
        else if(ammo.is(Items.OMINOUS_TRIAL_KEY)){
            ThrownTrident tt = new ThrownTrident(level,shooter,new ItemStack(Items.STICK));
            tt.setGlowingTag(true);
            tt.clientSideReturnTridentTickCount = level.getRandom().nextIntBetweenInclusive(80,120);
            return tt;
        }
        else{
            Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, true);
            if (projectile instanceof AbstractArrow) {
                AbstractArrow abstractarrow = (AbstractArrow)projectile;
                abstractarrow.setRemainingFireTicks(300);
                abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
            }
            return projectile;
        }
    }

    private static final ChargingSounds DEFAULT_SOUNDS  = new ChargingSounds(
            Optional.of(SoundEvents.CROSSBOW_LOADING_START),
            Optional.of(SoundEvents.CROSSBOW_LOADING_MIDDLE),
            Optional.of(SoundEvents.CROSSBOW_LOADING_END));

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        int i = this.getUseDuration(stack, entityLiving) - timeLeft;

        float f = (float)timeLeft / (float)getChargeDuration(stack, entityLiving);
        if (f > 1.0F) {
            f = 1.0F;
        }

        if (f >= 1.0F && !isCharged(stack) && tryLoadProjectiles(entityLiving, stack)) {
            ChargingSounds crossbowitem$chargingsounds = (ChargingSounds)EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.CROSSBOW_CHARGING_SOUNDS).orElse(DEFAULT_SOUNDS);
            crossbowitem$chargingsounds.end().ifPresent((p_352852_) -> {
                level.playSound((Player)null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), (SoundEvent)p_352852_.value(), entityLiving.getSoundSource(), 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
            });
        }

    }

    public static boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack) {
        List<ItemStack> list = draw(crossbowStack, shooter.getProjectile(crossbowStack), shooter);
        if (!list.isEmpty()) {
            crossbowStack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
            return true;
        }
        else {
            return false;
        }
    }


    protected static List<ItemStack> draw(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        if(ammo.isEmpty()) {
            return List.of();
        }
        else{
            Level var5 = shooter.level();
            int var10000;
            if (var5 instanceof ServerLevel) {
                ServerLevel serverlevel = (ServerLevel)var5;
                var10000 = EnchantmentHelper.processProjectileCount(serverlevel, weapon, shooter, 7);
            }
            else {
                var10000 = 1;
            }

            int i = var10000;
            List<ItemStack> list = new ArrayList(i);
            ItemStack itemstack1 = ammo.copy();

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = useAmmo(weapon, j == 0 ? ammo : itemstack1, shooter, true);
                if (!itemstack.isEmpty()) {
                    list.add(itemstack);
                }
            }

            return list;
        }
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        Vector3f vector3f;
        if (target != null) {
            double d0 = target.getX() - shooter.getX();
            double d1 = target.getZ() - shooter.getZ();
            double d2 = Math.sqrt(d0 * d0 + d1 * d1);
            double d3 = target.getY(0.3333333333333333) - projectile.getY() + d2 * 0.20000000298023224;
            vector3f = getProjectileShotVector(shooter, new Vec3(d0, d3, d1), angle);
        }
        else {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(angle * 0.017453292F), vec3.x, vec3.y, vec3.z);
            Vec3 vec31 = shooter.getViewVector(1.0F);
            vector3f = vec31.toVector3f().rotate(quaternionf);
        }

        projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), velocity, 0);
        //projectile.setRemainingFireTicks(300);
        float f = index == 0 ? 1.0F : 1.0F / (shooter.getRandom().nextFloat() * 0.5F + 1.8F) + (index & 1) == 1 ? 0.63F : 0.43F;
        shooter.level().playSound((Player)null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, shooter.getSoundSource(), 1.0F, f);
    }

    private static Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
        Vector3f vector3f = distance.toVector3f().normalize();
        Vector3f vector3f1 = (new Vector3f(vector3f)).cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double)vector3f1.lengthSquared() <= 1.0E-7) {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            vector3f1 = (new Vector3f(vector3f)).cross(vec3.toVector3f());
        }

        Vector3f vector3f2 = (new Vector3f(vector3f)).rotateAxis(1.5707964F, vector3f1.x, vector3f1.y, vector3f1.z);
        return (new Vector3f(vector3f)).rotateAxis(angle * 0.017453292F, vector3f2.x, vector3f2.y, vector3f2.z);
    }

    @Override
    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        float f = EnchantmentHelper.processProjectileSpread(level, weapon, shooter, 12.0F);
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
}
