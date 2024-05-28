// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

public enum PauseMode {
    OFF("false"), SLOWMO("slowmo"), ON("true");

    private final String serialisation;

    PauseMode(String serialisation) {
        this.serialisation = serialisation;
    }

    public String getSerialisation() {
        return serialisation;
    }

    public static PauseMode fromBoolean(boolean value) {
        return value ? ON : OFF;
    }

    public static PauseMode getNext(PauseMode current) {
        return switch (current) {
            case OFF -> SLOWMO;
            case SLOWMO -> ON;
            case ON -> OFF;
        };
    }
}