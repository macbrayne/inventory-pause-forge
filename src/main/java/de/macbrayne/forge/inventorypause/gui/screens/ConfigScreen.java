// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ConfigButtonRegistration;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.gui.components.ButtonInfo;
import de.macbrayne.forge.inventorypause.gui.components.TexturedToggleButton;
import de.macbrayne.forge.inventorypause.gui.components.ToggleButton;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
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
	private static final int fullButtonHeight = PADDING + buttonSize + PADDING;
	private static final int numberOfColumns = 9;
	private static int xText, yText;


	public ConfigScreen(Screen lastScreen) {
		super(Component.translatable("text.autoconfig.inventorypause.title"));
		this.lastScreen = lastScreen;
		buttonInfos.addAll(new ConfigButtonRegistration().run(config));
	}

	@Override
	protected void init() {
		super.init();
		int numberOfRows = buttonInfos.size() / numberOfColumns + (buttonInfos.size() % numberOfColumns > 0 ? 1 : 0);
		int width = numberOfColumns * totalSize;
		int y = 30;
		xText = this.width / 2 - 120;
		yText = y;
		y += PADDING + Minecraft.getInstance().font.lineHeight;

		createNonTexturedButtons(this.width / 2 - 120, y, 240, 20);
		y += 2 * totalSize;
		int x0 = this.width / 2 - width / 2, y0 = y;
		int imageGridEndY = createImageGrid(x0, y0, width, numberOfColumns, numberOfRows, buttonInfos);
		System.out.println(y0 - 2 * PADDING);
		createSaveAndQuit(this.width / 2 - 120, this.height - 20 - PADDING, 240, 20);
	}

	public int createImageGrid(int x0, int y0, int imageGridWidth, int numberOfColumns, int numberOfRows, List<ButtonInfo> list) {
		for (int i = 0, buttonInfosSize = list.size(); i < buttonInfosSize; i++) {
			ButtonInfo info = list.get(i);
			int column = i % numberOfColumns;
			int row = i / numberOfColumns;
			this.addRenderableWidget(new TexturedToggleButton(x0 + column * totalSize, y0 + row * totalSize, buttonSize, buttonSize,
					Component.translatable("narrator.button.language"), this, info));
		}
		return y0 + (numberOfRows + 1) * totalSize;
	}

	public void createNonTexturedButtons(int x0, int y, int width, int height) {
		int buttonWidth = width / 2 - 2;
		this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.inventory"), (button) -> {
			InventoryPause.MOD_CONFIG.abilities.pauseInventory = !InventoryPause.MOD_CONFIG.abilities.pauseInventory;
		}, () -> InventoryPause.MOD_CONFIG.abilities.pauseInventory));
		this.addRenderableWidget(new ToggleButton(x0 + width / 2 + 2, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.creativeInventory"), (button) -> {
			InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory = !InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory;
		}, () -> InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory));
		y += totalSize;
		this.addRenderableWidget(new ToggleButton(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.death"), (button) -> {
			InventoryPause.MOD_CONFIG.abilities.pauseDeath = !InventoryPause.MOD_CONFIG.abilities.pauseDeath;
		}, () -> InventoryPause.MOD_CONFIG.abilities.pauseDeath));
		this.addRenderableWidget(new ToggleButton(x0 + width / 2 + 2, y, buttonWidth, height, Component.translatable("menu.inventorypause.settings.gameModeSwitcher"), (button) -> {
			InventoryPause.MOD_CONFIG.abilities.pauseGameModeSwitcher = !InventoryPause.MOD_CONFIG.abilities.pauseGameModeSwitcher;
		}, () -> InventoryPause.MOD_CONFIG.abilities.pauseGameModeSwitcher));

	}

	public void createSaveAndQuit(int x0, int y, int width, int height) {
		int buttonWidth = width / 2 - 2;
		this.addRenderableWidget(new Button(x0, y, buttonWidth, height, Component.translatable("menu.inventorypause.further_options"), (p_96788_) -> {
			this.minecraft.setScreen(AutoConfig.getConfigScreen(ModConfig.class, this).get());
		}));
		this.addRenderableWidget(new Button(x0 + width / 2 + 2, y, buttonWidth, height, CommonComponents.GUI_DONE, (p_96786_) -> {
			this.minecraft.setScreen(lastScreen);
			AutoConfig.getConfigHolder(ModConfig.class).save();
		}));
	}

	public void render(PoseStack poseStack, int p_96250_, int p_96251_, float p_96252_) {
		this.renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);

		drawString(poseStack, this.font, Component.literal("Pause..."), xText, yText, 16777215);
		super.render(poseStack, p_96250_, p_96251_, p_96252_);
	}
}
