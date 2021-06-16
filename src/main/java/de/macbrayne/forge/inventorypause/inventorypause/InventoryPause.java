package de.macbrayne.forge.inventorypause.inventorypause;

import de.macbrayne.forge.inventorypause.inventorypause.utils.ForgeConfigHelper;
import de.macbrayne.forge.inventorypause.inventorypause.utils.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        MOD_CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ForgeConfigHelper::registerConfig);
    }
}
