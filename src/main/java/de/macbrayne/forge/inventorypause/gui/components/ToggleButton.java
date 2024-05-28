// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.TriStateSprites;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class ToggleButton extends Button {
    private static final Component TEXT_YES = CommonComponents.OPTION_ON.plainCopy().withStyle(ChatFormatting.DARK_GREEN);
    private static final Component TEXT_NO = CommonComponents.OPTION_OFF.plainCopy().withStyle(ChatFormatting.RED);
    private static final Component TEXT_SLOWMO = Component.translatable("menu.inventorypause.slowmo").withStyle(ChatFormatting.YELLOW);
    public final @NotNull Supplier<PauseMode> stateSupplier;
    private final Function<ToggleButton, Tooltip> tooltip;

    public ToggleButton(int x, int y, int width, int height, Component text, OnPress onPress, Tooltip tooltip, @NotNull Supplier<PauseMode> stateSupplier) {
        this(x, y, width, height, text, onPress, button -> tooltip, stateSupplier);
    }

    public ToggleButton(int x, int y, int width, int height, Component text, OnPress onPress, Function<ToggleButton, Tooltip> tooltip, @NotNull Supplier<PauseMode> stateSupplier) {
        super(new Button.Builder(text, onPress).pos(x, y).size(width, height));
        this.stateSupplier = stateSupplier;
        this.tooltip = tooltip;
        setTooltip(tooltip.apply(this));
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        guiGraphics.blitSprite(TriStateSprites.DEFAULT.get(this.active, this.isHoveredOrFocused(), stateSupplier.get()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
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

    public Tooltip appendTooltipTo(Component text) {
        String key = "menu.inventorypause.currentState." + stateSupplier.get().getSerialisation();
        Component addendum = switch (stateSupplier.get()) {
            case OFF -> Component.translatable(key, TEXT_NO);
            case SLOWMO -> Component.translatable(key, TEXT_SLOWMO);
            case ON -> Component.translatable(key, TEXT_YES);
        };
        if(text.getString().isEmpty()) {
            return Tooltip.create(text.plainCopy().append(addendum));
        }
        return Tooltip.create(text.plainCopy().append("\n\n").append(addendum));
    }
}
