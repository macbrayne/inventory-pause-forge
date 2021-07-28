package de.macbrayne.forge.inventorypause.compat.mod;

import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;
import moze_intel.projecte.gameObjs.gui.*;

@RegisterCompat(configKey = "projectECompat")
public class ProjectEConfig {
    @RegisterClass(AbstractCollectorScreen.MK1.class)
    boolean abstractCollectorScreenMK1 = true;
    @RegisterClass(AbstractCollectorScreen.MK2.class)
    boolean abstractCollectorScreenMK2 = true;
    @RegisterClass(AbstractCollectorScreen.MK3.class)
    boolean abstractCollectorScreenMK3 = true;
    @RegisterClass(AbstractCondenserScreen.MK1.class)
    boolean abstractCondenserScreenMK1 = true;
    @RegisterClass(AbstractCondenserScreen.MK2.class)
    boolean abstractCondenserScreenMK2 = true;
    @RegisterClass(AlchBagScreen.class)
    boolean alchBagScreen = true;
    @RegisterClass(AlchChestScreen.class)
    boolean alchChestScreen = true;
    @RegisterClass(GUIDMFurnace.class)
    boolean GUIDMFurnace = true;
    @RegisterClass(GUIEternalDensity.class)
    boolean GUIEternalDensity = true;
    @RegisterClass(GUIMercurialEye.class)
    boolean GUIMercurialEye = true;
    @RegisterClass(GUIRelay.GUIRelayMK1.class)
    boolean GUIRelayMK1 = true;
    @RegisterClass(GUIRelay.GUIRelayMK2.class)
    boolean GUIRelayMK2 = true;
    @RegisterClass(GUIRelay.GUIRelayMK3.class)
    boolean GUIRelayMK3 = true;
    @RegisterClass(GUIRMFurnace.class)
    boolean GUIRMFurnace = true;
    @RegisterClass(GUITransmutation.class)
    boolean GUITransmutation = true;
}
