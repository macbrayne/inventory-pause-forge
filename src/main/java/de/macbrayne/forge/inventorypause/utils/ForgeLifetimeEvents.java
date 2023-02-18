// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.utils;

import com.mojang.blaze3d.platform.InputConstants;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.compat.VanillaCompat;
import de.macbrayne.forge.inventorypause.gui.screens.ConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForgeLifetimeEvents {
    private static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    // Key mapping is lazily initialized, so it doesn't exist until it is registered
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
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreen(screen)));
        new VanillaCompat().register();
    }


    // Event is on the mod event bus only on the physical client
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        if(InventoryPause.MOD_CONFIG.settingsForModpacks.registerKeybinds) {
            LOGGER.info("Registering key mappings");
            event.register(OPEN_SETTINGS.get());
            event.register(COPY_CLASS_NAME.get());
        }
    }
}
