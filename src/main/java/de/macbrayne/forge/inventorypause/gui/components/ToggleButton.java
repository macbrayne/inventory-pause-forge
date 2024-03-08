// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ToggleButton extends Button {
    final @NotNull Supplier<Boolean> stateSupplier;

    public ToggleButton(int x, int y, int width, int height, Component text, OnPress onPress, Tooltip tooltip, @NotNull Supplier<Boolean> stateSupplier) {
        super(new Button.Builder(text, onPress).pos(x, y).size(width, height).tooltip(tooltip));
        this.stateSupplier = stateSupplier;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float p_94285_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation(InventoryPause.MOD_ID, "textures/gui/config/widgets.png"));
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int imageOffset = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        this.blit(poseStack, this.getX(), this.getY(), 0, imageOffset * 20, this.width / 2, this.height);
        this.blit(poseStack, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, imageOffset * 20, this.width / 2, this.height);

        renderContent(poseStack, mouseX, mouseY, p_94285_);
    }

    public void renderContent(PoseStack poseStack, int mouseX, int mouseY, float p_94285_) {
        int imageOffset = this.getYImage(this.isHoveredOrFocused());
        int j = getFGColor();
        Component yesComponent = Component.translatable("menu.inventorypause.toggle.on").withStyle(ChatFormatting.DARK_AQUA);
        Component noComponent = Component.translatable("menu.inventorypause.toggle.off").withStyle(ChatFormatting.RED);
        Component text = Component.translatable("menu.inventorypause.toggle", this.getMessage(), this.stateSupplier.get() ? yesComponent : noComponent);
        drawCenteredString(poseStack, Minecraft.getInstance().font, text, this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

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
