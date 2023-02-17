// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHelper {
    private static final TomlWriter writer = new TomlWriter();
    public static void serialize() {
        try {
            writer.write(InventoryPause.MOD_CONFIG, FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml").toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ModConfig deserialize() {
        Path path = FMLPaths.CONFIGDIR.get().resolve("inventorypause.toml");
        if(Files.exists(path)) {
            try {
                return new Toml().read(path.toFile()).to(ModConfig.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ModConfig();
        }
    }
}
