// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.common;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PauseMode {
    OFF("false"), SLOWMO("slowmo"), ON("true");

    private final String serialisation;
    private static final Map<String, PauseMode> LOOKUP = Arrays.stream(values())
            .collect(Collectors.toMap(PauseMode::getSerialisation, Function.identity()));

    PauseMode(String serialisation) {
        this.serialisation = serialisation;
    }

    public String getSerialisation() {
        return serialisation;
    }

    public static PauseMode fromBoolean(boolean value) {
        return value ? ON : OFF;
    }

    public static PauseMode fromString(String value) {
        return LOOKUP.getOrDefault(value, OFF);
    }

    public boolean isActive() {
        return this != OFF;
    }

    public static PauseMode getNext(PauseMode current) {
        return switch (current) {
            case OFF -> SLOWMO;
            case SLOWMO -> ON;
            case ON -> OFF;
        };
    }
}