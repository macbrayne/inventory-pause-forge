package de.macbrayne.forge.inventorypause.utils;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForgeConfigHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get());
        LOGGER.debug("Registered Config Screen");
    }
}
