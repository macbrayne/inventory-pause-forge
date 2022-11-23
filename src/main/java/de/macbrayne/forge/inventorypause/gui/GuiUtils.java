package de.macbrayne.forge.inventorypause.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class GuiUtils {
	public static Button.OnTooltip getTooltip(Screen screen, Component component) {
		return new Button.OnTooltip() {
			private final Component text = component;

			public void onTooltip(Button button, PoseStack poseStack, int p_169460_, int p_169461_) {
					screen.renderTooltip(poseStack, this.text, p_169460_, p_169461_);

			}

			public void narrateTooltip(Consumer<Component> narrator) {
				narrator.accept(this.text);
			}
		};
	}
}
