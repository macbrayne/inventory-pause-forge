package de.macbrayne.forge.inventorypause;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.utils.CompatTick;
import de.macbrayne.forge.inventorypause.utils.ForgeLifetimeEvents;
import de.macbrayne.forge.inventorypause.utils.Reference;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.network.FMLNetworkConstants;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("inventorypause")
public class InventoryPause {
    public static ModConfig MOD_CONFIG = new ModConfig();

    public InventoryPause() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        MOD_CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ForgeLifetimeEvents::clientSetup);
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            if (CompatTick.timeUntilCompatTick > 0 &&
                    --CompatTick.timeUntilCompatTick == 0) {
                CompatTick.timeUntilCompatTick = MOD_CONFIG.modCompat.timeBetweenCompatTicks;
            }
            while (ForgeLifetimeEvents.OPEN_SETTINGS.get().consumeClick()) {
                var configScreen = AutoConfig.getConfigScreen(ModConfig.class, Minecraft.getInstance().screen).get();
                Minecraft.getInstance().setScreen(configScreen);
            }
        }
    }
}
