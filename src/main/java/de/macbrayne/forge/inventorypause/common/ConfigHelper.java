// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import de.macbrayne.forge.inventorypause.InventoryPause;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import de.macbrayne.forge.inventorypause.common.old.ModConfigV1;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        if (Files.exists(path)) {
            try {
                Toml toml = new Toml().read(path.toFile());
                ModConfig config = toml.to(ModConfig.class);
                LOGGER.debug("Successfully loaded config from file");
                LOGGER.info("Current config version is V{}", config.CONFIG_VERSION_DO_NOT_TOUCH);
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

    public static <T> Optional<T> attemptLoad(Path path, Codec<T> codec) {
        Gson gson = new GsonBuilder().create();
        if (Files.exists(path)) {
            try {
                var gsonReader = Files.newBufferedReader(path);
                JsonElement element = gson.fromJson(gsonReader, JsonElement.class);
                var result = codec.decode(JsonOps.INSTANCE, element).resultOrPartial(LOGGER::error);
                if (result.isPresent()) {
                    return Optional.of(result.get().getFirst());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    public static<T> void save(Path path, T object, Codec<T> codec) {
        LOGGER.info("Writing to file {}", path);
        InventoryPause.getScreenDictionary().setLastScreenDirty();
        try {
            var gsonWriter = Files.newBufferedWriter(path);
            var result = codec.encodeStart(JsonOps.INSTANCE, object).resultOrPartial(LOGGER::error);
            if (result.isPresent()) {
                gsonWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(result.get()));
                gsonWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void ensureConfigDirExists() {
        if(!Files.exists(FMLPaths.CONFIGDIR.get().resolve("inventorypause"))) {
            try {
                Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve("inventorypause"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}