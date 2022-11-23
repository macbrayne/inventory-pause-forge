package de.macbrayne.forge.inventorypause.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.gui.components.TexturedToggleButton;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfigScreen extends Screen {
	private final Screen lastScreen;
	private final ModConfig config = InventoryPause.MOD_CONFIG;
	public ConfigScreen(Screen lastScreen) {
		super(Component.literal("New Config"));
		this.lastScreen = lastScreen;
	}

	@Override
	protected void init() {
		super.init();

		int k = 24;
		int l = this.height / 4 + 48;
		this.addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_96791_) -> {
			InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory = !InventoryPause.MOD_CONFIG.abilities.pauseCreativeInventory;
		}, Component.translatable("narrator.button.language")));
		this.addRenderableWidget(new Button(this.width / 2 - 100, l + 72 + 12, 98, 20, Component.translatable("menu.options"), (p_96788_) -> {
			this.minecraft.setScreen(AutoConfig.getConfigScreen(ModConfig.class, this).get());
		}));
		this.addRenderableWidget(new Button(this.width / 2 + 2, l + 72 + 12, 98, 20, Component.translatable("menu.quit"), (p_96786_) -> {
			this.minecraft.setScreen(lastScreen);
		}));
		ButtonInfo furnaceButton = new ButtonInfo(new ResourceLocation("block/furnace_front_on"), Component.translatable("text.autoconfig.inventorypause.option.abilities.pauseFurnace"), button -> InventoryPause.MOD_CONFIG.abilities.pauseFurnace = !InventoryPause.MOD_CONFIG.abilities.pauseFurnace);
		ButtonInfo shulkerBoxButton = new ButtonInfo(new ResourceLocation("block/shulker_box"), Component.translatable("text.autoconfig.inventorypause.option.abilities.pauseShulkerBox"), button -> InventoryPause.MOD_CONFIG.abilities.pauseShulkerBox = !InventoryPause.MOD_CONFIG.abilities.pauseShulkerBox);
		ButtonInfo craftingTableButton = new ButtonInfo(new ResourceLocation("block/crafting_table_top"), Component.translatable("text.autoconfig.inventorypause.option.abilities.pauseCraftingTable"), button -> InventoryPause.MOD_CONFIG.abilities.pauseCraftingTable = !InventoryPause.MOD_CONFIG.abilities.pauseCraftingTable);
		ButtonInfo cartographyTableButton = new ButtonInfo(new ResourceLocation("block/cartography_table_side1"), Component.translatable("text.autoconfig.inventorypause.option.abilities.additionalGUIs.pauseCartographyTable"), button -> InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseCartographyTable = !InventoryPause.MOD_CONFIG.abilities.additionalGUIs.pauseCartographyTable);
		var buttons = new ArrayList<ButtonInfo>();
		buttons.add(furnaceButton);
		buttons.add(cartographyTableButton);
		buttons.add(shulkerBoxButton);
		buttons.addAll(Collections.nCopies(10, craftingTableButton));
		createImageGrid(buttons);
	}

	public void createImageGrid(List<ButtonInfo> list) {
		int numberOfColumns = 8;
		int numberOfRows = list.size() / numberOfColumns + (list.size() % numberOfColumns > 0 ? 1 : 0);
		System.out.println(list.size() + ", " + list.size() / numberOfColumns + ", " + (list.size() % numberOfColumns > 0 ? 1 : 0));
		int buttonSize = 20;
		int spacer = 4;
		int totalSize = buttonSize + spacer;
		int x0 = this.width / 2 - 4 * totalSize, y0 = this.height / 2 - totalSize;
		for(int row = 0; row < numberOfRows; row++) {
			for(int column = 0; column < numberOfColumns; column++) {
				// 0 0 0 1 1 1 2 2 2 3 3 3
				//       Spalte 0
				// Reihe 0 Inhalt
				// maxC = 3
				// numR = 4
				// numC = 3
				//
				int currentObject = numberOfColumns * row + column;
				System.out.println(currentObject);
				if(currentObject >= list.size()) {
					return;
				}
				ButtonInfo info = list.get(currentObject);
				this.addRenderableWidget(new TexturedToggleButton(x0 + column * totalSize, y0 + row * totalSize, buttonSize, buttonSize, info.iconLocation(), 20, 20, info.hoverTranslation, Component.translatable("narrator.button.language"), info.onPress, this));
			}
		}
	}

	public void render(PoseStack poseStack, int p_96250_, int p_96251_, float p_96252_) {
		this.renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
		super.render(poseStack, p_96250_, p_96251_, p_96252_);
	}



	record ButtonInfo(ResourceLocation iconLocation, Component hoverTranslation, Button.OnPress onPress) {
		ButtonInfo {

		}
	}
}
