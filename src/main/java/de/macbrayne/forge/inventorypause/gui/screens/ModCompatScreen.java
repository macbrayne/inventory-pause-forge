// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.gui.components.ToggleButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

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
        super(Component.literal("Mod Compat Screen"));
        this.lastScreen = lastScreen;
    }
    @Override
    protected void init() {
        super.init();
        modCompatList = new ModCompatList(this, minecraft);
        addWidget(modCompatList);
        createSaveAndQuit(this.width / 2 - 120, this.height - 20 - PADDING, 240, 20);
    }

    public void createSaveAndQuit(int x0, int y, int width, int height) {
        int buttonWidth = width / 2 - 2;
        this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.debug_mode"), p_93751_ -> {
            InventoryPause.MOD_CONFIG.debug = !InventoryPause.MOD_CONFIG.debug;
        }, () -> {
            return InventoryPause.MOD_CONFIG.debug;
        }));
        this.addRenderableWidget(new Button.Builder(CommonComponents.GUI_DONE, (p_96786_) -> {
            this.minecraft.setScreen(lastScreen);
            this.modCompatList.saveChanges();
            ConfigHelper.serialize();
        }).pos (x0 + width / 2 + 2, y).size(buttonWidth, height).build());
    }

    public void render(PoseStack poseStack, int p_96250_, int p_96251_, float p_96252_) {
        this.renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        this.modCompatList.render(poseStack, p_96250_, p_96251_, p_96252_);
        super.render(poseStack, p_96250_, p_96251_, p_96252_);
    }

    @Override
    public void magicalSpecialHackyFocus(@Nullable GuiEventListener guiEventListener) {
        modCompatList.setFocused(guiEventListener);
    }
}
