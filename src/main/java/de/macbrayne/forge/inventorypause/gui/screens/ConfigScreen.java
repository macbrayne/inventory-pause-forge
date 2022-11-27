// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ConfigButtonRegistration;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.gui.components.ButtonInfo;
import de.macbrayne.forge.inventorypause.gui.components.TexturedToggleButton;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {
	private final Screen lastScreen;
	private final ModConfig config = InventoryPause.MOD_CONFIG;
	private final List<ButtonInfo> buttonInfos = new ArrayList<>();

	public ConfigScreen(Screen lastScreen) {
		super(Component.literal("Inventory Pause Temp Config"));
		this.lastScreen = lastScreen;
		buttonInfos.addAll(new ConfigButtonRegistration().run(config));
	}

	@Override
	protected void init() {
		super.init();
		int PADDING = 6;
		int y = this.height - 20 - PADDING;
		int fullButtonHeight = PADDING + 20 + PADDING;

		int l = this.height / 4 + 48;
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 20 - PADDING, 98, 20, Component.translatable("menu.inventorypause.further_options"), (p_96788_) -> {
			this.minecraft.setScreen(AutoConfig.getConfigScreen(ModConfig.class, this).get());
		}));
		this.addRenderableWidget(new Button(this.width / 2 + 2, this.height - 20 - PADDING, 98, 20, CommonComponents.GUI_DONE, (p_96786_) -> {
			this.minecraft.setScreen(lastScreen);
			AutoConfig.getConfigHolder(ModConfig.class).save();
		}));

		createImageGrid(buttonInfos);
	}

	public void createImageGrid(List<ButtonInfo> list) {
		int numberOfColumns = 8;
		int numberOfRows = list.size() / numberOfColumns + (list.size() % numberOfColumns > 0 ? 1 : 0);
		int buttonSize = 20;
		int spacer = 4;
		int totalSize = buttonSize + spacer;
		int x0 = this.width / 2 - 4 * totalSize, y0 = this.height / 2 - totalSize;
		for(int row = 0; row < numberOfRows; row++) {
			for(int column = 0; column < numberOfColumns; column++) {
				int currentObject = numberOfColumns * row + column;
				if(currentObject >= list.size()) {
					return;
				}
				ButtonInfo info = list.get(currentObject);
				this.addRenderableWidget(new TexturedToggleButton(x0 + column * totalSize, y0 + row * totalSize, buttonSize, buttonSize, Component.translatable("narrator.button.language"), this, info));
			}
		}
	}

	public void render(PoseStack poseStack, int p_96250_, int p_96251_, float p_96252_) {
		this.renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
		super.render(poseStack, p_96250_, p_96251_, p_96252_);
	}
}
