// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.macbrayne.menupause.common.PauseMode;
import de.macbrayne.menupause.platform.Services;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public sealed class GuiEntry<T> permits GuiEntry.Icon, GuiEntry.Text {
    private static final Codec<Class<?>> CLASS_CODEC = Codec.STRING.comapFlatMap(className -> {
        try {
            String mappedClassName = Services.PLATFORM.mappingsToDev(className);
            return DataResult.success(Class.forName(mappedClassName, false, GuiEntry.class.getClassLoader()));
        } catch (ClassNotFoundException e) {
            return DataResult.error(() -> "Could not find class " + className);
        }
    }, aClass -> Services.PLATFORM.mappingsFromDev(aClass.getName()));
    private final String configEntry;
    private final T content;
    private final PauseMode defaultState;
    private final Class<?> target;


    public GuiEntry(String configEntry, T icon, PauseMode defaultState, Class<?> target) {
        Objects.requireNonNull(configEntry, "configEntry");
        Objects.requireNonNull(icon, "icon");
        Objects.requireNonNull(target, "target");
        Objects.requireNonNull(defaultState, "defaultContent");
        this.configEntry = configEntry;
        this.content = icon;
        this.target = target;
        this.defaultState = defaultState;
    }

    public String configEntry() {
        return configEntry;
    }

    public T content() {
        return content;
    }

    public PauseMode defaultState() {
        return defaultState;
    }

    public Class<?> target() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuiEntry<?> guiEntry = (GuiEntry<?>) o;
        return Objects.equals(configEntry, guiEntry.configEntry) && Objects.equals(content, guiEntry.content) && Objects.equals(target, guiEntry.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configEntry, content, target);
    }

    public static final class Icon extends GuiEntry<ItemStack> {
        public static final Codec<Icon> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("config_entry").forGetter(Icon::configEntry),
                ItemStack.SINGLE_ITEM_CODEC.fieldOf("icon").forGetter(Icon::content),
                PauseMode.CODEC.optionalFieldOf("default_state", PauseMode.OFF).forGetter(Icon::defaultState),
                CLASS_CODEC.fieldOf("target").forGetter(Icon::target)
        ).apply(instance, Icon::new));

        public Icon(String configEntry, ItemStack icon, PauseMode defaultState, Class<?> target) {
            super(configEntry, icon, defaultState, target);
        }
    }

    public static final class Text extends GuiEntry<String> {
        public static final Codec<Text> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("config_entry").forGetter(Text::configEntry),
                Codec.STRING.fieldOf("content_translation_key").forGetter(Text::content),
                PauseMode.CODEC.optionalFieldOf("default_state", PauseMode.OFF).forGetter(Text::defaultState),
                CLASS_CODEC.fieldOf("target").forGetter(Text::target)
        ).apply(instance, Text::new));

        public Text(String configEntry, String contentTranslationKey, PauseMode defaultState, Class<?> target) {
            super(configEntry, contentTranslationKey, defaultState, target);
        }
    }
}
