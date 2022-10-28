package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.common.ModConfig;
import de.macbrayne.forge.inventorypause.compat.VanillaCompat;
import de.macbrayne.forge.inventorypause.compat.mod.custom.*;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class ForgeLifetimeEvents {
    // Key mapping is lazily initialized so it doesn't exist until it is registered
    public static final Lazy<KeyBinding> COPY_CLASS_NAME = Lazy.of(() -> new KeyBinding(
            "key.inventorypause.copyClassName", // Localisation
            GLFW.GLFW_KEY_UNKNOWN, // No default mapping
            "key.categories.inventorypause.main" // Category localisation
    ));
    public static final Lazy<KeyBinding> OPEN_SETTINGS = Lazy.of(() -> new KeyBinding(
            "key.inventorypause.openSettings", // Localisation
            KeyConflictContext.IN_GAME, // Only open in-game
            InputMappings.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_UNKNOWN, // No default mapping
            "key.categories.inventorypause.main" // Category localisation
    ));

    public static void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get());
        new VanillaCompat().register();

        if (ModList.get().isLoaded("ironchest")) {
            new IronchestCompat().register();
        }
        if (ModList.get().isLoaded("twilightforest")) {
            new TheTwilightForestCompat().register();
        }
        if (ModList.get().isLoaded("botania")) {
            new BotaniaCompat().register();
        }
        if (ModList.get().isLoaded("curios")) {
            new CuriosCompat().register();
        }
        if (ModList.get().isLoaded("titanium")) {
            new TitaniumCompat().register();
        }
        if (ModList.get().isLoaded("aquaculture")) {
            new Aquaculture2Compat().register();
        }

        ClientRegistry.registerKeyBinding(OPEN_SETTINGS.get());
        ClientRegistry.registerKeyBinding(COPY_CLASS_NAME.get());
    }
}
