// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;

public class Reference {
    private static final ScreenDictionary SCREEN_DICTIONARY = new ScreenDictionary();
    public static final String MOD_ID = "inventorypause";

    public static ScreenDictionary getScreenDictionary() {
        return SCREEN_DICTIONARY;
    }
}
