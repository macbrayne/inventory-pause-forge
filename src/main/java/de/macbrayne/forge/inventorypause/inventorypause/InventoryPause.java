package de.macbrayne.forge.inventorypause.inventorypause;

import de.macbrayne.forge.inventorypause.inventorypause.utils.ForgeConfigHelper;
import de.macbrayne.forge.inventorypause.inventorypause.utils.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        MOD_CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ForgeConfigHelper::registerConfig);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
