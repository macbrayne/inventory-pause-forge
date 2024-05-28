// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import de.macbrayne.forge.inventorypause.gui.components.ToggleButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ModCompatScreen extends Screen {
    private static final int PADDING = 6;
    private final Screen lastScreen;
    private final ModConfig config = InventoryPause.MOD_CONFIG;
    public ModCompatList modCompatList;
    private static final int buttonSize = 20;
    private static final int buttonPadding = 4;
    private static final int totalSize = buttonSize + buttonPadding;
    private static final int fullButtonHeight = PADDING + buttonSize + PADDING;
    protected ModCompatScreen(Screen lastScreen) {
        super(Component.translatable("menu.inventorypause.settings.mod_compat_options"));
        this.lastScreen = lastScreen;
    }
    @Override
    protected void init() {
        super.init();
        modCompatList = new ModCompatList(this, minecraft);
        addWidget(modCompatList);
        createSaveAndQuit(this.width / 2 - 120, this.height - 20 - PADDING, 240, 20);
        magicalSpecialHackyFocus(modCompatList);
    }

    public void createSaveAndQuit(int x0, int y, int width, int height) {
        int buttonWidth = width;
        int xDone = x0;
        if (!InventoryPause.MOD_CONFIG.settingsForModpacks.hideDebugButton) {
            buttonWidth = width / 2 - 2;
            xDone += width / 2 + 2;
            this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.modCompat.debug_mode"), p_93751_ -> {
                InventoryPause.MOD_CONFIG.debug = !InventoryPause.MOD_CONFIG.debug;
            }, Tooltip.create(Component.translatable("menu.inventorypause.settings.modCompat.debug_mode.tooltip")), () -> PauseMode.fromBoolean(InventoryPause.MOD_CONFIG.debug)));
        }
        this.addRenderableWidget(new Button.Builder(CommonComponents.GUI_DONE, (p_96786_) -> {
            onClose();
            this.modCompatList.saveChanges();
        }).pos (xDone, y).size(buttonWidth, height).build());
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        super.render(guiGraphics, mouseX, mouseY, tickDelta);
        this.modCompatList.render(guiGraphics, mouseX, mouseY, tickDelta);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(lastScreen);
    }
}
