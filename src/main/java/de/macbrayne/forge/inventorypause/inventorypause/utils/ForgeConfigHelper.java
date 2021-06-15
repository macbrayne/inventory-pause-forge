package de.macbrayne.forge.inventorypause.inventorypause.utils;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

public class ForgeConfigHelper {
    public static void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get());
    }
}
