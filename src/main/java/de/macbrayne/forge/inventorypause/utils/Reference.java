package de.macbrayne.forge.inventorypause.utils;

import de.macbrayne.forge.inventorypause.compat.ModScreenDictionary;
import de.macbrayne.forge.inventorypause.compat.VanillaScreenDictionary;

public class Reference {
    private static final ModScreenDictionary modScreenDictionary = new ModScreenDictionary();
    private static final VanillaScreenDictionary vanillaScreenDictionary = new VanillaScreenDictionary();

    public static ModScreenDictionary getModScreenDictionary() {
        return modScreenDictionary;
    }

    public static VanillaScreenDictionary getVanillaScreenDictionary() {
        return vanillaScreenDictionary;
    }
}
