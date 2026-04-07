// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.gui.screens;

import de.macbrayne.menupause.MenuPause;
import de.macbrayne.menupause.config.ModConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ConfigScreen extends Screen {
    private final Screen lastScreen;
    private final HeaderAndFooterLayout layout;
    public ConfigList modCompatList;

    public ConfigScreen(Screen lastScreen) {
        super(Component.translatable("menu.menupause.settings.title"));
        this.lastScreen = lastScreen;
        layout = new HeaderAndFooterLayout(this);
    }

    @Override
    protected void init() {
        super.init();
        modCompatList = new ConfigList(this, minecraft);
        layout.addToContents(modCompatList);
        LinearLayout linear = LinearLayout.horizontal().spacing(8);
        linear.addChild(new Button.Builder(CommonComponents.GUI_CANCEL, (p_96788_) -> {
            MenuPause.MOD_CONFIG = ModConfig.load();
            resetChanges();
            onClose();
        }).build());
        linear.addChild(new Button.Builder(CommonComponents.GUI_DONE, (p_96786_) -> {
            saveChanges();
            MenuPause.MOD_CONFIG.save();
            onClose();
        }).build());

        layout.addToFooter(linear);
        this.repositionElements();

        this.layout.visitWidgets(widget -> {
            AbstractWidget abstractwidget = this.addRenderableWidget(widget);
        });
    }

    public void saveChanges() {
        for (ConfigList.Entry item : modCompatList.children()) {
            if (item instanceof Saveable saveable) {
                saveable.save();
            }
        }
    }

    public void resetChanges() {
        for (ConfigList.Entry item : modCompatList.children()) {
            if (item instanceof Saveable saveable) {
                saveable.reset();
            }
        }
    }

    @Override
    protected void repositionElements() {
        this.modCompatList.setSize(this.width, this.layout.getContentHeight());
        this.layout.arrangeElements();
    }


    @Override
    public void onClose() {
        boolean isDirty = false;
        for (ConfigList.Entry item : modCompatList.children()) {
            if (item instanceof Saveable saveable) {
                if (saveable.isDirty()) {
                    isDirty = true;
                    break;
                }
            }
        }
        ModConfig diskVersion = ModConfig.load();

        if (isDirty || !diskVersion.equals(MenuPause.MOD_CONFIG)) {
            this.minecraft.setScreen(new ConfirmScreen(userAccepted -> {
                if (userAccepted) {
                    saveChanges();
                    MenuPause.MOD_CONFIG.save();
                } else {
                    MenuPause.MOD_CONFIG = diskVersion;
                }
                this.minecraft.setScreen(lastScreen);
            }, Component.translatable("menu.menupause.settings.confirmation.title"), Component.translatable("menu.menupause.settings.confirmation.description")));
        } else {
            this.minecraft.setScreen(lastScreen);
        }
    }

    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float tickDelta) {
        super.render(guiGraphics, mouseX, mouseY, tickDelta);
        this.layout.visitWidgets((widget) -> widget.render(guiGraphics, mouseX, mouseY, tickDelta));
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
    }
}
