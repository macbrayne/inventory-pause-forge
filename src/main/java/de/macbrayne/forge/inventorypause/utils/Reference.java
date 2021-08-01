package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.compat.ScreenDictionary;

public class Reference {
    private static final ScreenDictionary SCREEN_DICTIONARY = new ScreenDictionary();

    public static ScreenDictionary getScreenDictionary() {
        return SCREEN_DICTIONARY;
    }
}
