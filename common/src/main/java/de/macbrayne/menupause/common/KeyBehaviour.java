// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.common;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public class KeyBehaviour {
    public enum ForceUnpause implements StringRepresentable {
        UNPAUSE("unpause"), SLOWMO("slowmo");

        public static final Codec<ForceUnpause> CODEC = StringRepresentable.fromEnum(ForceUnpause::values);
        private final String serialisation;

        ForceUnpause(String serialisation) {
            this.serialisation = serialisation;
        }

        @Override
        public @NotNull String getSerializedName() {
            return serialisation;
        }

        public ForceUnpause other() {
            return this == UNPAUSE ? SLOWMO : UNPAUSE;
        }

        public boolean isUnpause() {
            long window = Minecraft.getInstance().getWindow().getWindow();
            boolean isShiftPressed = InputConstants.isKeyDown(window, InputConstants.KEY_LSHIFT)
                    || InputConstants.isKeyDown(window, InputConstants.KEY_RSHIFT);
            return this == UNPAUSE && !isShiftPressed || this == SLOWMO && isShiftPressed;
        }
    }
}
