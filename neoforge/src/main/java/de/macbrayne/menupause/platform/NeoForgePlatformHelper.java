// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.platform;

import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Path findResourceInOwningFile(String path) {
        return ModList.get().getModContainerById(Constants.MOD_ID).orElseThrow().getModInfo().getOwningFile().getFile().findResource(path);
    }
}
