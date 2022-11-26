// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.gui.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class TexturedToggleButton extends Button {
    private final ResourceLocation resourceLocation;

    private final Supplier<Boolean> stateSupplier;

    public TexturedToggleButton(int x, int y, int width, int height, Component narratedComponent, Screen screen, ButtonInfo info) {
        this(x, y, width, height, info.iconLocation(), narratedComponent, GuiUtils.getTogglePress(info.stateSupplier(), info.stateConsumer()), GuiUtils.getTooltip(screen, info.tooltipComponent()), info.stateSupplier());
    }

    public TexturedToggleButton(int x, int y, int width, int height, ResourceLocation resourceLocation, Component tooltipComponent, Component narratedComponent, OnPress onPress, Screen screen, Supplier<Boolean> stateSupplier) {
        this(x, y, width, height, resourceLocation, narratedComponent, onPress, GuiUtils.getTooltip(screen, tooltipComponent), stateSupplier);
    }

    private TexturedToggleButton(int x, int y, int width, int height, ResourceLocation resourceLocation, Component narratedComponent, OnPress onPress, OnTooltip onTooltip, Supplier<Boolean> stateSupplier) {
        super(x, y, width, height, narratedComponent, onPress, onTooltip);
        this.resourceLocation = resourceLocation;
        this.stateSupplier = stateSupplier;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float p_94285_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int imageOffset = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        this.blit(poseStack, this.x, this.y, 0, 46 + imageOffset * 20, this.width / 2, this.height);
        this.blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + imageOffset * 20, this.width / 2, this.height);


        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(this.resourceLocation);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        blit(poseStack, this.x + 2, this.y + 2, 0, this.width - 4, this.height - 4, sprite);
        // blit(poseStack, this.x + 2, this.y + 2, 0, 0, this.width - 4, this.height - 4, this.textureWidth - 4, this.textureHeight - 4);
        if (this.isHovered) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }

    }

}
