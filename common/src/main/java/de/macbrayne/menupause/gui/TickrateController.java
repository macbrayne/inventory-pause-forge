// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.gui;

public interface TickrateController {
    void menupause$setSlowMotion(boolean state);

    boolean menupause$isSlowMotion();

    void menupause$setOriginalTickRate(float tickRate);

    float menupause$getOriginalTickRate();
}
