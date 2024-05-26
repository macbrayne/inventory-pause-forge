// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ToggleButton extends Button {
    private static final Component TEXT_YES = CommonComponents.OPTION_ON.plainCopy().withStyle(ChatFormatting.DARK_AQUA);
    private static final Component TEXT_NO = CommonComponents.OPTION_OFF.plainCopy().withStyle(ChatFormatting.RED);
    private static final Component TEXT_SLOWMO = CommonComponents.OPTION_ON.plainCopy().withStyle(ChatFormatting.YELLOW);
    private final @NotNull Supplier<PauseMode> stateSupplier;

    public ToggleButton(int x, int y, int width, int height, Component text, OnPress onPress, Tooltip tooltip, @NotNull Supplier<PauseMode> stateSupplier) {
        super(new Button.Builder(text, onPress).pos(x, y).size(width, height).tooltip(tooltip));
        this.stateSupplier = stateSupplier;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        var loc = new ResourceLocation(InventoryPause.MOD_ID, "textures/gui/config/widgets.png");
        int imageOffset = this.getYImage(this.isHoveredOrFocused());
        guiGraphics.blitNineSliced(loc, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, imageOffset * 20);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        renderContent(guiGraphics, mouseX, mouseY, tickDelta);
    }

    public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, getText(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    private Component getText() {
        return switch (this.stateSupplier.get()) {
            case OFF -> Component.translatable("menu.inventorypause.toggle", this.getMessage(), TEXT_NO);
            case SLOWMO -> Component.translatable("menu.inventorypause.toggle", this.getMessage(), TEXT_SLOWMO);
            case ON -> Component.translatable("menu.inventorypause.toggle", this.getMessage(), TEXT_YES);
        };
    }

    @Override
    protected MutableComponent createNarrationMessage() {
        return wrapDefaultNarrationMessage(getText());
    }

    protected int getYImage(boolean isHovered) {
        int i = 0;
        if (stateSupplier.get() == PauseMode.ON) {
            i += 2;
        }
        if (isHovered) {
            i += 1;
        }

        return i;
    }
}
