package net.rk.addon.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.rk.addon.menu.CombinerMenu;

public class CombinerScreen extends AbstractContainerScreen<CombinerMenu> {
    public CombinerScreen(CombinerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    private static final ResourceLocation BG_TEXTURE =
            ResourceLocation.parse("thingamajigsgoodies:textures/gui/modifierbg.png");

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        guiGraphics.blit(BG_TEXTURE,
                this.leftPos,this.topPos,0,0,
                this.imageWidth,this.imageHeight,this.imageWidth,this.imageHeight);
        RenderSystem.disableBlend();
    }
}
