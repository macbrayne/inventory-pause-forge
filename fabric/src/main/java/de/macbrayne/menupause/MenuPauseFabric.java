// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause;

import de.macbrayne.menupause.events.FabricEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class MenuPauseFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // To prevent it from crashing during data generation as we try to load data that hasn't yet been generated.
        if (System.getProperty("fabric-api.datagen") != null) {
            return;
        }
        FabricEvents.registerKeybindings();
        ClientTickEvents.END_CLIENT_TICK.register(FabricEvents::endClientTick);
        ScreenEvents.BEFORE_INIT.register(FabricEvents::screenInit);
    }
}
