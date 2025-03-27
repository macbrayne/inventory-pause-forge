// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.mixin;

import de.macbrayne.menupause.common.ScreenUnpause;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenUnpause {
    @Unique private boolean menupause$forceUnpause = false;

    @Unique
    @Override
    public void menupause$invertForceUnpause() {
        this.menupause$forceUnpause = !this.menupause$forceUnpause;
    }

    @Unique
    @Override
    public boolean menupause$getForceUnpause() {
        return this.menupause$forceUnpause;
    }
}
