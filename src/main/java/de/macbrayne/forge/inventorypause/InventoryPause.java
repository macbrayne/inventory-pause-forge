package de.macbrayne.forge.inventorypause;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.utils.ForgeConfigHelper;
import de.macbrayne.forge.inventorypause.utils.ForgeLifetimeEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        MOD_CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ForgeLifetimeEvents::enqueueIMC);
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ForgeConfigHelper::registerConfig);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
