// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.inventorypause.mixin;

import de.macbrayne.inventorypause.common.ScreenUnpause;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenUnpause {
    @Unique private boolean inventorypause$forceUnpause = false;

    @Unique
    @Override
    public void inventorypause$invertForceUnpause() {
        this.inventorypause$forceUnpause = !this.inventorypause$forceUnpause;
    }

    @Unique
    @Override
    public boolean inventorypause$getForceUnpause() {
        return this.inventorypause$forceUnpause;
    }
}
