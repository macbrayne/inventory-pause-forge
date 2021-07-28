package de.macbrayne.forge.inventorypause.compat.mod;

import com.buuz135.industrial.gui.conveyor.GuiConveyor;
import com.buuz135.industrial.gui.transporter.GuiTransporter;
import de.macbrayne.forge.inventorypause.annotation.RegisterClass;
import de.macbrayne.forge.inventorypause.annotation.RegisterCompat;

@RegisterCompat(modId = "industrialforegoing", configKey = "industrialForegoingCompat")
public class IndustrialForegoingConfig {
    // com.buuz135.industrial.gui.transporter
    @RegisterClass(GuiTransporter.class)
    boolean guiTransporter = true;

    // com.buuz135.industrial.gui.conveyor
    @RegisterClass(GuiConveyor.class)
    boolean guiConveyor = true;
}