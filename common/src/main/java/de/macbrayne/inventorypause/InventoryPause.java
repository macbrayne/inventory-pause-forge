// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause;

import de.macbrayne.inventorypause.config.ConfigHelper;
import de.macbrayne.inventorypause.config.GuiEntries;
import de.macbrayne.inventorypause.config.ModConfig;

public class InventoryPause {
    public static final GuiEntries GUI_ENTRIES = GuiEntries.loadEntries();
    public static ModConfig MOD_CONFIG = loadModConfig();

    public static ModConfig loadModConfig() {
        ConfigHelper.ensureConfigDirExists();
        ConfigHelper.migrateConfigToJson();
        return ModConfig.load();
    }
}