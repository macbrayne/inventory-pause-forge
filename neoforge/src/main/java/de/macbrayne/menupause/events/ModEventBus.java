// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.events;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.MenuPause;
import de.macbrayne.menupause.gui.screens.ConfigScreen;
import net.minecraft.client.KeyMapping;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.slf4j.Logger;

public class ModEventBus {
    private static final Logger LOGGER = Constants.LOG;
    // Key mapping is lazily initialized, so it doesn't exist until it is registered
    public static final Lazy<KeyMapping> PAUSE_GAME = Lazy.of(() -> new KeyMapping(
            "key.inventorypause.pauseGame", // Localisation
            KeyConflictContext.UNIVERSAL, // Only open in-game
            InputConstants.UNKNOWN, // No default mapping
            "key.categories.inventorypause.main" // Category localisation
    ));

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

    public static void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (mc, screen) -> new ConfigScreen(screen));
    }


    public static void registerBindings(RegisterKeyMappingsEvent event) {
        if (MenuPause.MOD_CONFIG.settingsForModpacks.registerKeybinds) {
            LOGGER.info("Registering key mappings");
            event.register(PAUSE_GAME.get());
            event.register(OPEN_SETTINGS.get());
            event.register(COPY_CLASS_NAME.get());
        }
    }
}
