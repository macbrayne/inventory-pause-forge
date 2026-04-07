// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.gui.screens;

interface Saveable {
    void save();
    default boolean isDirty() {
        return false;
    }
    default void reset() {
    }
}
