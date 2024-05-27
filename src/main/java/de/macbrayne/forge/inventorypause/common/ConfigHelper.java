// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import de.macbrayne.forge.inventorypause.InventoryPause;
import de.macbrayne.forge.inventorypause.common.old.ModConfigV1;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ConfigHelper {
    private static final Logger LOGGER = LogManager.getLogger(InventoryPause.MOD_ID);
    private static final TomlWriter writer = new TomlWriter();
    public static void serialize() {
        LOGGER.info("Writing config to file");
        InventoryPause.getScreenDictionary().setLastScreenDirty();
        try {
            writer.write(InventoryPause.MOD_CONFIG, FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml").toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ModConfig deserialize() {
        LOGGER.debug("Trying to load config from file");
        Path path = FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml");
        if(Files.exists(path)) {
            try {
                ModConfig config = new Toml().read(path.toFile()).to(ModConfig.class);
                LOGGER.debug("Successfully loaded config from file");
                return config;
            } catch (Exception ignored) {
                try {
                    ModConfig migratedConfig = ModConfigV1.toV2(new Toml().read(path.toFile()).to(ModConfigV1.class));
                    LOGGER.warn("V1 config detected, migrating to V2 and moving old config to inventorypause.toml.old");
                    Files.copy(FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml"), FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml.old"), StandardCopyOption.COPY_ATTRIBUTES);
                    writer.write(migratedConfig, FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml").toFile());
                    LOGGER.warn("Migration complete");
                    return migratedConfig;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            LOGGER.warn("No config file found, creating new one");
            ModConfig config = new ModConfig();
            try {
                writer.write(config, FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml").toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return config;
        }
    }
}
