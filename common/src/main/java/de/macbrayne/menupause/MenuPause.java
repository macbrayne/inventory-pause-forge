// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause;

import de.macbrayne.menupause.config.ConfigHelper;
import de.macbrayne.menupause.config.GuiEntries;
import de.macbrayne.menupause.config.ModConfig;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class MenuPause {
    public static final GuiEntries GUI_ENTRIES = GuiEntries.loadEntries();
    public static ModConfig MOD_CONFIG = loadModConfig();

    public static void init() {
        MOD_CONFIG.states.registerScreens();
    }

    public static ModConfig loadModConfig() {
        ConfigHelper.ensureConfigDirExists();
        ConfigHelper.migrateConfigToJson();
        return ModConfig.load();
    }
}