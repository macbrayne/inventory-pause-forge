// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class CommonClass {
    public static void init() {
        InventoryPause.MOD_CONFIG.states.registerScreens();
    }
}