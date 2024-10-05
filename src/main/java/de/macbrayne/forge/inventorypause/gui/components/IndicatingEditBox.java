// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.components;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.mojank.MutableTooltip;
import de.macbrayne.forge.inventorypause.gui.screens.ModCompatList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IndicatingEditBox extends EditBox {
    private final ImageWidget warning;
    private ClassStatus status;

    public IndicatingEditBox(Font font, int width, int height) {
        super(font, width, height, Component.empty());
        warning = ImageWidget.sprite(height - 10, height - 10, new ResourceLocation("notification/more"));
        warning.setTooltip(Tooltip.create(Component.empty()));
        this.setResponder(s -> {
            this.status = parseClass(s);
            if(status != ClassStatus.OK) {
                String translation = "menu.inventorypause.settings.modCompat.entry.tooltip." + switch (status) {
                    case NOT_A_SCREEN -> "not_a_screen";
                    case VANILLA -> "vanilla";
                    case INVALID -> "invalid";
                    default -> throw new IllegalStateException("Unexpected value: " + status);
                };
                ((MutableTooltip) warning.getTooltip()).inventorypause$updateMessage(Minecraft.getInstance(), Component.translatable(translation));
            } else {
                ((MutableTooltip) warning.getTooltip()).inventorypause$updateMessage(Minecraft.getInstance(), Component.empty());
            }
        });
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        if (this.isVisible() && status != ClassStatus.OK) {
            warning.setX(this.getX() + this.width - warning.getWidth() - 5);
            warning.setY(this.getY() + 5);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, 10);
            warning.render(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.pose().popPose();
        }
    }

    private static ClassStatus parseClass(String name) {
        if(!name.contains(".") || name.startsWith("java")) {
            return ClassStatus.INVALID;
        }
        try {
            Class<?> clazz = Class.forName(name, false, ModCompatList.class.getClassLoader());
            if(!Screen.class.isAssignableFrom(clazz)) {
                return ClassStatus.NOT_A_SCREEN;
            }
            if(InventoryPause.getScreenDictionary().handleScreen(clazz) != PauseMode.OFF) {
                return ClassStatus.VANILLA;
            }
            return ClassStatus.OK;
        } catch (ClassNotFoundException ignored) {
        }
        return ClassStatus.INVALID;
    }

    private enum ClassStatus {
        OK,
        NOT_A_SCREEN,
        VANILLA,
        INVALID;
    }
}
