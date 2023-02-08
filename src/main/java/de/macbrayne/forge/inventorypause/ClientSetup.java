// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "inventorypause", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
	// Key mapping is lazily initialized so it doesn't exist until it is registered
	public static final Lazy<KeyMapping> COPY_CLASS_NAME = Lazy.of(() -> new KeyMapping(
			"key.inventorypause.addToList", // Localisation
			KeyConflictContext.GUI, // Only open in-game
			InputConstants.UNKNOWN, // No default mapping
			"key.categories.inventorypause.main" // Category localisation
	));
	public static final Lazy<KeyMapping> OPEN_SETTINGS = Lazy.of(() -> new KeyMapping(
			"key.inventorypause.openSettings", // Localisation
			KeyConflictContext.IN_GAME, // Only open in-game
			InputConstants.UNKNOWN, // No default mapping
			"key.categories.inventorypause.main" // Category localisation
	));

	// Event is on the mod event bus only on the physical client
	@SubscribeEvent
	public static void registerBindings(RegisterKeyMappingsEvent event) {
		event.register(OPEN_SETTINGS.get());
		event.register(COPY_CLASS_NAME.get());
	}
}
