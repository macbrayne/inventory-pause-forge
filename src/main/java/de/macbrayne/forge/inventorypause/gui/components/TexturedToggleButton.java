// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.gui.GuiUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class TexturedToggleButton extends Button {
    private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation("textures/gui/container/villager2.png");
    private final ItemStack icon;

    private final Supplier<Boolean> stateSupplier;

    public TexturedToggleButton(int x, int y, int width, int height, Component narratedComponent, Screen screen, ButtonInfo info) {
        this(x, y, width, height, info.itemStack(), narratedComponent, GuiUtils.getTogglePress(info.stateSupplier(), info.stateConsumer()), GuiUtils.getTooltip(screen, info.tooltipComponent()), info.stateSupplier());
    }

    public TexturedToggleButton(int x, int y, int width, int height, ItemStack icon, Component tooltipComponent, Component narratedComponent, OnPress onPress, Screen screen, Supplier<Boolean> stateSupplier) {
        this(x, y, width, height, icon, narratedComponent, onPress, GuiUtils.getTooltip(screen, tooltipComponent), stateSupplier);
    }

    private TexturedToggleButton(int x, int y, int width, int height, ItemStack icon, Component narratedComponent, OnPress onPress, OnTooltip onTooltip, Supplier<Boolean> stateSupplier) {
        super(x, y, width, height, narratedComponent, onPress, onTooltip);
        this.icon = icon;
        this.stateSupplier = stateSupplier;
    }


    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float p_94285_) {
        this.setBlitOffset(0);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation("inventorypause", "textures/gui/config/widgets.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int imageOffset = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        this.blit(poseStack, this.x, this.y, 0, imageOffset * 20, this.width, this.height);

        GuiUtils.renderButtonItem(icon, this.x, this.y, this.width);

        if (this.isHovered) {
            this.renderToolTip(poseStack, mouseX, mouseY);
        }

    }


    @Override
    protected int getYImage(boolean isHovered) {
        int i = 0;
        if (stateSupplier.get()) {
            i += 2;
        }
        if (isHovered) {
            i += 1;
        }

        return i;
    }
}
