// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui;

import de.macbrayne.forge.inventorypause.common.PauseMode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GuiUtils {
    public static void renderButtonItem(GuiGraphics guiGraphics, ItemStack itemStack, int x, int y, int buttonSize) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate( x + buttonSize / 2f, y + buttonSize / 2f, 1);
        guiGraphics.pose().scale(14.0f / 16.0f, 14.0f / 16.0f, 1);
        guiGraphics.renderItem(itemStack,  -8, -8);
        guiGraphics.pose().popPose();
    }

    public static Button.OnPress getTogglePress(Supplier<PauseMode> supplier, Consumer<PauseMode> consumer) {
        return button -> consumer.accept(PauseMode.getNext(supplier.get()));
    }
}
