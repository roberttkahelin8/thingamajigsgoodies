package net.rk.addon.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

public class TGItemProperties{
    public static void addCustomProperties(){
        makeFishingRod(TGItems.PURIFYING_FISHING_ROD.get());
        makeBow(TGItems.PURIFYING_BOW.get());
        makeCrossbow(TGItems.PURIFYING_CROSSBOW.get());
    }

    public static void makeCrossbow(Item item){
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (p_351682_, p_351683_, p_351684_, p_351685_) -> {
            if (p_351684_ == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(p_351682_) ? 0.0F : (float)(p_351682_.getUseDuration(p_351684_) - p_351684_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_351682_, p_351684_);
            }
        });
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
            return p_174607_ != null && p_174607_.isUsingItem() && p_174607_.getUseItem() == p_174605_ && !CrossbowItem.isCharged(p_174605_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("charged"), (p_275891_, p_275892_, p_275893_, p_275894_) -> {
            return CrossbowItem.isCharged(p_275891_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("firework"), (p_329796_, p_329797_, p_329798_, p_329799_) -> {
            ChargedProjectiles chargedprojectiles = (ChargedProjectiles)p_329796_.get(DataComponents.CHARGED_PROJECTILES);
            return chargedprojectiles != null && chargedprojectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }

    public static void makeFishingRod(Item item){
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("cast"), (p_174585_, p_174586_, p_174587_, p_174588_) -> {
            if (p_174587_ == null) {
                return 0.0F;
            } else {
                boolean flag = p_174587_.getMainHandItem() == p_174585_;
                boolean flag1 = p_174587_.getOffhandItem() == p_174585_;
                if (p_174587_.getMainHandItem().getItem() instanceof FishingRodItem) {
                    flag1 = false;
                }

                return (flag || flag1) && p_174587_ instanceof Player && ((Player)p_174587_).fishing != null ? 1.0F : 0.0F;
            }
        });
    }

    public static void makeBow(Item item){
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"),
                (itemstack, level, livingentity, id) -> {
            if (livingentity == null) {
                return 0.0F;
            }
            else {
                return livingentity.getUseItem() != itemstack ? 0.0F :
                        (float)(itemstack.getUseDuration(livingentity) - livingentity.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, livingentity, id) -> {
            return livingentity != null && livingentity.isUsingItem() && livingentity.getUseItem() == stack ? 1.0F : 0.0F;
        });
    }
}
