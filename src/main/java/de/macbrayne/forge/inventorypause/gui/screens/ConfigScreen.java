// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.gui.ConfigButtonRegistration;
import de.macbrayne.forge.inventorypause.common.ConfigHelper;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.gui.components.ButtonInfo;
import de.macbrayne.forge.inventorypause.gui.components.TexturedToggleButton;
import de.macbrayne.forge.inventorypause.gui.components.ToggleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {
	private static final int PADDING = 6;
	private final Screen lastScreen;
	private final ModConfig config = InventoryPause.MOD_CONFIG;
	private final List<ButtonInfo> buttonInfos = new ArrayList<>();
	private static final int buttonSize = 20;
	private static final int buttonPadding = 4;
	private static final int totalSize = buttonSize + buttonPadding;
	private static final int numberOfColumns = 9;
	private static int xText, yText;


	public ConfigScreen(Screen lastScreen) {
		super(Component.translatable("menu.inventorypause.settings.title"));
		this.lastScreen = lastScreen;
		buttonInfos.addAll(new ConfigButtonRegistration().run(config));
	}

	@Override
	protected void init() {
		super.init();
		int numberOfRows = buttonInfos.size() / numberOfColumns + (buttonInfos.size() % numberOfColumns > 0 ? 1 : 0);
		int width = numberOfColumns * totalSize;
		int y = 30;

		y = createGeneralButtons(this.width / 2 - 120, y, 240, 20);

		xText = this.width / 2 - 120;
		yText = y;
		y += PADDING + Minecraft.getInstance().font.lineHeight;

		y = createNonTexturedButtons(this.width / 2 - 120, y, 240, 20);
		int x0 = this.width / 2 - width / 2, y0 = y;
		y = createImageGrid(x0, y0, numberOfColumns, numberOfRows, buttonInfos);

		if(!InventoryPause.MOD_CONFIG.settingsForModpacks.hideModCompatButton) {
			this.addRenderableWidget(new Button.Builder(Component.translatable("menu.inventorypause.settings.mod_compat_options"), button -> this.minecraft.setScreen(new ModCompatScreen(this)))
					.pos(this.width / 2 - 120, this.height - 20 - PADDING - totalSize)
					.size(240, buttonSize)
					.tooltip(Tooltip.create(Component.translatable("menu.inventorypause.settings.mod_compat_options.tooltip"))).build());
		}

		createSaveAndQuit(this.width / 2 - 120, this.height - 20 - PADDING, 240, 20);
	}

	public int createImageGrid(int x0, int y0, int numberOfColumns, int numberOfRows, List<ButtonInfo> list) {
		for (int i = 0, buttonInfosSize = list.size(); i < buttonInfosSize; i++) {
			ButtonInfo info = list.get(i);
			int column = i % numberOfColumns;
			int row = i / numberOfColumns;
			this.addRenderableWidget(new TexturedToggleButton(x0 + column * totalSize, y0 + row * totalSize, buttonSize, buttonSize,
					Component.translatable("narrator.button.language"), info));
		}
		return y0 + (numberOfRows + 1) * totalSize;
	}

	public int createNonTexturedButtons(int x0, int y, int width, int height) {
		int buttonWidth = width / 2 - 2;
		this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.inventory"), (button) -> {
			config.abilities.pauseInventory = !config.abilities.pauseInventory;
		}, Tooltip.create(Component.empty()), () -> config.abilities.pauseInventory));
		this.addRenderableWidget(new ToggleButton(x0 + width / 2 + 2, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.creativeInventory"), (button) -> {
			config.abilities.pauseCreativeInventory = !config.abilities.pauseCreativeInventory;
		}, Tooltip.create(Component.empty()), () -> config.abilities.pauseCreativeInventory));
		y += totalSize;
		this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.death"), (button) -> {
			config.abilities.pauseDeath = !config.abilities.pauseDeath;
		}, Tooltip.create(Component.empty()), () -> config.abilities.pauseDeath));
		this.addRenderableWidget(new ToggleButton(x0 + width / 2 + 2, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.gameModeSwitcher"), (button) -> {
			config.abilities.pauseGameModeSwitcher = !config.abilities.pauseGameModeSwitcher;
		}, Tooltip.create(Component.empty()), () -> config.abilities.pauseGameModeSwitcher));
		y += totalSize;
		return y;
	}

	public int createGeneralButtons(int x0, int y, int width, int height) {
		int buttonWidth = width / 2 - 2;
		this.addRenderableWidget(new ToggleButton(x0, y, width, height,
				Component.translatable("menu.inventorypause.settings.enabled"),  button -> config.enabled = !config.enabled, Tooltip.create(Component.translatable("menu.inventorypause.settings.enabled.tooltip")), () -> config.enabled));
		y += totalSize;
		this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height,
				Component.translatable("menu.inventorypause.settings.disableSaving"),  button -> config.disableSaving = !config.disableSaving, Tooltip.create(Component.translatable("menu.inventorypause.settings.disableSaving.tooltip")), () -> !config.disableSaving));
		this.addRenderableWidget(new ToggleButton(x0 + width / 2 + 2, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.pauseSounds"), (button) -> {
			config.pauseSounds = !config.pauseSounds;
		}, Tooltip.create(Component.translatable("menu.inventorypause.settings.pauseSounds.tooltip")), () -> config.pauseSounds));
		y += totalSize;
		return y;
	}

	public void createSaveAndQuit(int x0, int y, int width, int height) {
		int buttonWidth = width / 2 - 2;
		this.addRenderableWidget(new Button.Builder(CommonComponents.GUI_CANCEL, (p_96788_) -> {
			this.minecraft.setScreen(lastScreen);
			InventoryPause.MOD_CONFIG = ConfigHelper.deserialize();
		}).pos(x0, y).size(buttonWidth, height).build());
		this.addRenderableWidget(new Button.Builder(CommonComponents.GUI_DONE, (p_96786_) -> {
			this.minecraft.setScreen(lastScreen);
			ConfigHelper.serialize();
		}).pos(x0 + width / 2 + 2, y).size(buttonWidth,height).build());
	}

	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);

		guiGraphics.drawString(this.font, Component.translatable("menu.inventorypause.settings.title.pause"), xText, yText, 16777215);
		super.render(guiGraphics, mouseX, mouseY, partialTick);
	}
}
