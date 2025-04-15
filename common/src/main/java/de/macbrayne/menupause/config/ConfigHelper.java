// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.MenuPause;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import de.macbrayne.menupause.config.old.ModConfigTOML;
import de.macbrayne.menupause.config.old.ModConfigV1;
import de.macbrayne.menupause.platform.Services;
import org.slf4j.Logger;

public class ConfigHelper {
    private static final Logger LOGGER = Constants.LOG;
    private static final TomlWriter writer = new TomlWriter();

    public static void serialize() {
        LOGGER.info("Writing config to file");
        Constants.SCREEN_DICTIONARY.setLastScreenDirty();
        try {
            writer.write(MenuPause.MOD_CONFIG, Services.PLATFORM.getConfigDir().resolve("inventorypause.toml").toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static ModConfigTOML deserialize() {
        LOGGER.debug("Trying to load config from file");
        Path path = Services.PLATFORM.getConfigDir().resolve("inventorypause.toml");
        if (Files.exists(path)) {
            try {
                Toml toml = new Toml().read(path.toFile());
                ModConfigTOML config = toml.to(ModConfigTOML.class);
                LOGGER.debug("Successfully loaded config from file");
                LOGGER.info("Current config version is V{}", config.CONFIG_VERSION_DO_NOT_TOUCH);
                return config;
            } catch (Exception ignored) {
                try {
                    ModConfigTOML migratedConfig = ModConfigV1.toV2(new Toml().read(path.toFile()).to(ModConfigV1.class));
                    LOGGER.warn("V1 config detected, migrating to V2 and moving old config to inventorypause.toml.old");
                    Files.copy(Services.PLATFORM.getConfigDir().resolve("inventorypause.toml"), Services.PLATFORM.getConfigDir().resolve("inventorypause.toml.old"), StandardCopyOption.REPLACE_EXISTING);
                    writer.write(migratedConfig, Services.PLATFORM.getConfigDir().resolve("inventorypause.toml").toFile());
                    LOGGER.warn("Migration complete");
                    return migratedConfig;
                } catch (Exception e) {
                    LOGGER.error("Failed to load config from file", e);
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public static void migrateConfigToJson() {
        ModConfigTOML config = deserialize();
        if (config == null) {
            return;
        }
        ModConfig migratedConfig = ModConfigTOML.toV3(config);
        LOGGER.info("Migrating config to JSON");
        migratedConfig.save();
        LOGGER.info("Moving old config to inventorypause.toml.old");
        try {
            Files.move(Services.PLATFORM.getConfigDir().resolve("inventorypause.toml"), Services.PLATFORM.getConfigDir().resolve("inventorypause.toml.old"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static <T> void save(Path path, T object, Codec<T> codec) {
        LOGGER.info("Writing to file {}", path.toAbsolutePath());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (var gsonWriter = Files.newBufferedWriter(path)) {
            var result = codec.encodeStart(JsonOps.INSTANCE, object).resultOrPartial(LOGGER::error);
            gson.toJson(result.orElseThrow(), gsonWriter);
        } catch (IOException e) {
            LOGGER.error("Failed to write to file {}", path.toAbsolutePath(), e);
        }
    }

    public static void ensureConfigDirExists() {
        if (!Files.exists(Services.PLATFORM.getConfigDir().resolve(Constants.MOD_ID))) {
            try {
                Files.createDirectories(Services.PLATFORM.getConfigDir().resolve(Constants.MOD_ID));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}