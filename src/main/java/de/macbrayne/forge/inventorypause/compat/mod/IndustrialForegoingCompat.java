package de.macbrayne.forge.inventorypause.compat.mod;

import com.buuz135.industrial.gui.conveyor.GuiConveyor;
import com.buuz135.industrial.gui.transporter.GuiTransporter;
import de.macbrayne.forge.inventorypause.annotation.RegisterClass;

public class IndustrialForegoingCompat implements GenericModCompat {
    @Override
    public boolean getConfigKey() {
        return config.modCompat.industrialForegoingCompat;
    }

    public static class IndustrialForegoingConfig {
        // com.buuz135.industrial.gui.transporter
        @RegisterClass(GuiTransporter.class)
        boolean guiTransporter = true;

        // com.buuz135.industrial.gui.conveyor
        @RegisterClass(GuiConveyor.class)
        boolean guiConveyor = true;
    }
}
