// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.platform;

import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Path findResourceInOwningFile(String path) {
        return FabricLoader.getInstance().getModContainer(Constants.MOD_ID).get().findPath(path).get();
    }
}
