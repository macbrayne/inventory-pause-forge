// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.*;

public class ModConfig {
    public static final int VERSION = 3;
    public static final Codec<ModConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("CONFIG_VERSION_DO_NOT_TOUCH").forGetter(config -> config.configVersion),
            Codec.BOOL.fieldOf("disable_saving").forGetter(config -> config.disableSaving),
            Codec.BOOL.fieldOf("pause_sounds").forGetter(config -> config.pauseSounds),
            Codec.BOOL.fieldOf("debug").forGetter(config -> config.debugText.debug),
            DebugText.CODEC.fieldOf("debug_text").forGetter(config -> config.debugText),
            SettingsForModpacks.CODEC.fieldOf("settings_for_modpacks").forGetter(config -> config.settingsForModpacks),
            ModCompat.CODEC.fieldOf("mod_compat").forGetter(config -> config.modCompat),
            GuiStates.CODEC.fieldOf("states").forGetter(config -> config.states)
    ).apply(instance, ModConfig::new));

    public int configVersion = VERSION;
    public boolean disableSaving, pauseSounds;
    public final DebugText debugText;
    public final SettingsForModpacks settingsForModpacks;
    public final ModCompat modCompat;
    public final GuiStates states;

    public ModConfig(int configVersion, boolean disableSaving, boolean pauseSounds, boolean debug, DebugText debugText, SettingsForModpacks settingsForModpacks, ModCompat modCompat, GuiStates states) {
        this.configVersion = configVersion;
        this.disableSaving = disableSaving;
        this.pauseSounds = pauseSounds;
        this.debugText = debugText;
        this.settingsForModpacks = settingsForModpacks;
        this.modCompat = modCompat;
        this.states = states;
    }

    public static ModConfig getDefault() {
        return new ModConfig(VERSION, false, false, false, new DebugText(false, 4f, 4f, 3), new SettingsForModpacks(false, false, true),
                new ModCompat(new ArrayList<>(), new ArrayList<>(), 1),
                new GuiStates(new HashMap<>()));
    }

    public static class SettingsForModpacks {
        public static final Codec<SettingsForModpacks> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("hide_debug_button").forGetter(settingsForModpacks -> settingsForModpacks.hideDebugButton),
                Codec.BOOL.fieldOf("hide_mod_compat_button").forGetter(settingsForModpacks -> settingsForModpacks.hideModCompatButton),
                Codec.BOOL.fieldOf("register_keybinds").forGetter(settingsForModpacks -> settingsForModpacks.registerKeybinds)
        ).apply(instance, SettingsForModpacks::new));

        public boolean hideDebugButton, hideModCompatButton, registerKeybinds;

        public SettingsForModpacks(boolean hideDebugButton, boolean hideModCompatButton, boolean registerKeybinds) {
            this.hideDebugButton = hideDebugButton;
            this.hideModCompatButton = hideModCompatButton;
            this.registerKeybinds = registerKeybinds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SettingsForModpacks that = (SettingsForModpacks) o;
            return hideDebugButton == that.hideDebugButton && hideModCompatButton == that.hideModCompatButton && registerKeybinds == that.registerKeybinds;
        }
    }

    public static class DebugText {
        public static final Codec<DebugText> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.BOOL.fieldOf("debug").forGetter(debugText -> debugText.debug),
                Codec.FLOAT.fieldOf("x").forGetter(debugText -> debugText.x),
                Codec.FLOAT.fieldOf("y").forGetter(debugText -> debugText.y),
                Codec.INT.fieldOf("max_depth").forGetter(debugText -> debugText.maxDepth)
        ).apply(instance, DebugText::new));

        public boolean debug;
        public float x, y;
        public int maxDepth;

        public DebugText(boolean debug, float x, float y, int maxDepth) {
            this.x = x;
            this.y = y;
            this.maxDepth = maxDepth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DebugText debugText = (DebugText) o;
            return debug == debugText.debug && Float.compare(x, debugText.x) == 0 && Float.compare(y, debugText.y) == 0 && maxDepth == debugText.maxDepth;
        }
    }

    public static class ModCompat {
        public static final Codec<ModCompat> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.listOf().fieldOf("custom_classes_pause").forGetter(modCompat -> modCompat.customScreens),
                Codec.STRING.listOf().fieldOf("custom_classes_slowmo").forGetter(modCompat -> modCompat.compatScreens),
                Codec.INT.fieldOf("slow_motion_tick_speed").forGetter(modCompat -> modCompat.slowmoTickSpeed)
        ).apply(instance, ModCompat::new));

        public final List<String> customScreens, compatScreens;
        public int slowmoTickSpeed;

        public ModCompat(List<String> customScreens, List<String> compatScreens, int slowmoTickSpeed) {
            this.customScreens = new ArrayList<>(customScreens);
            this.compatScreens = new ArrayList<>(compatScreens);
            this.slowmoTickSpeed = slowmoTickSpeed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModCompat modCompat = (ModCompat) o;
            return slowmoTickSpeed == modCompat.slowmoTickSpeed && Objects.equals(customScreens, modCompat.customScreens) && Objects.equals(compatScreens, modCompat.compatScreens);
        }
    }

    public static ModConfig load() {
        Path path = FMLPaths.CONFIGDIR.get().resolve("inventorypause/inventorypause.json");
        return ConfigHelper.attemptLoad(path, ModConfig.CODEC).orElseGet(ModConfig::getDefault);
    }

    public void save() {
        Path path = FMLPaths.CONFIGDIR.get().resolve("inventorypause/inventorypause.json");
        ConfigHelper.save(path, this, ModConfig.CODEC);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModConfig modConfig = (ModConfig) o;
        return configVersion == modConfig.configVersion && disableSaving == modConfig.disableSaving && pauseSounds == modConfig.pauseSounds && Objects.equals(debugText, modConfig.debugText) && Objects.equals(settingsForModpacks, modConfig.settingsForModpacks) && Objects.equals(modCompat, modConfig.modCompat) && Objects.equals(states, modConfig.states);
    }
}
