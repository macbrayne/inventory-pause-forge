// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.compat;

import de.macbrayne.forge.inventorypause.utils.Reference;

public interface GenericCompat {
    ScreenDictionary SCREEN_DICTIONARY = Reference.getScreenDictionary();

    boolean getConfigKey();
}
