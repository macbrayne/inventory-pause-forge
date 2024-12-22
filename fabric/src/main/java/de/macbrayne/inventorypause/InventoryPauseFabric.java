// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause;

import de.macbrayne.inventorypause.events.FabricEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class InventoryPauseFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        InventoryPause.init();
        FabricEvents.registerKeybindings();
        ClientTickEvents.END_CLIENT_TICK.register(FabricEvents::endClientTick);
        ScreenEvents.BEFORE_INIT.register(FabricEvents::screenInit);
    }
}
