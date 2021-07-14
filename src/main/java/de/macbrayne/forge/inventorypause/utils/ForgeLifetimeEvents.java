package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.compat.mod.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class ForgeLifetimeEvents {

    public static void enqueueIMC(@SuppressWarnings("unused") InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("waystones")) {
            new WaystonesConfigGen().register();
        }
        if (ModList.get().isLoaded("ironchest")) {
            new IronchestCompat().register();
        }
        if (ModList.get().isLoaded("appliedenergistics2")) {
            new AppliedEnergistics2ConfigGen().register();
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
    }
}
