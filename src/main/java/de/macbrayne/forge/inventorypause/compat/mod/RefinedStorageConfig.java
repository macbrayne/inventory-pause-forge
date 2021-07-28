package de.macbrayne.forge.inventorypause.compat.mod;

import com.refinedmods.refinedstorage.screen.*;
import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;

@RegisterCompat(configKey = "refinedStorageCompat")
public class RefinedStorageConfig {
    @RegisterClass(ConstructorScreen.class)
    boolean constructorScreen = true;
    @RegisterClass(ControllerScreen.class)
    boolean controllerScreen = true;
    @RegisterClass(CrafterManagerScreen.class)
    boolean crafterManagerScreen = true;
    @RegisterClass(CrafterScreen.class)
    boolean crafterScreen = true;
    @RegisterClass(CraftingMonitorScreen.class)
    boolean craftingMonitorScreen = true;
    @RegisterClass(DestructorScreen.class)
    boolean destructorScreen = true;
    @RegisterClass(DetectorScreen.class)
    boolean detectorScreen = true;
    @RegisterClass(DiskDriveScreen.class)
    boolean diskDriveScreen = true;
    @RegisterClass(DiskManipulatorScreen.class)
    boolean diskManipulatorScreen = true;
    @RegisterClass(ExporterScreen.class)
    boolean exporterScreen = true;
    @RegisterClass(ExternalStorageScreen.class)
    boolean externalStorageScreen = true;
    @RegisterClass(FilterScreen.class)
    boolean filterScreen = true;
    @RegisterClass(FluidAmountScreen.class)
    boolean fluidAmountScreen = true;
    @RegisterClass(FluidInterfaceScreen.class)
    boolean fluidInterfaceScreen = true;
    @RegisterClass(FluidStorageBlockScreen.class)
    boolean fluidStorageBlockScreen = true;
    @RegisterClass(ImporterScreen.class)
    boolean importerScreen = true;
    @RegisterClass(InterfaceScreen.class)
    boolean interfaceScreen = true;
    @RegisterClass(ItemAmountScreen.class)
    boolean itemAmountScreen = true;
    @RegisterClass(NetworkTransmitterScreen.class)
    boolean networkTransmitterScreen = true;
    @RegisterClass(PriorityScreen.class)
    boolean priorityScreen = true;
    @RegisterClass(RelayScreen.class)
    boolean relayScreen = true;
    @RegisterClass(SecurityManagerScreen.class)
    boolean securityManagerScreen = true;
    @RegisterClass(StorageBlockScreen.class)
    boolean storageBlockScreen = true;
    @RegisterClass(StorageMonitorScreen.class)
    boolean storageMonitorScreen = true;
    @RegisterClass(WirelessTransmitterScreen.class)
    boolean wirelessTransmitterScreen = true;
}
