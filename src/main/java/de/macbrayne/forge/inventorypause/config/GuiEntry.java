// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record GuiEntry(String configEntry, ItemStack icon, Class<?> target) {
    private static final Codec<Class<?>> CLASS_CODEC = Codec.STRING.comapFlatMap(className -> {
        try {
            return DataResult.success(Class.forName(className, false, GuiEntries.class.getClassLoader()));
        } catch (ClassNotFoundException e) {
            return DataResult.error(() -> "Could not find class " + className);
        }
    }, Class::getName);
    public static final Codec<GuiEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("config_entry").forGetter(GuiEntry::configEntry),
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("icon").forGetter(GuiEntry::icon),
            CLASS_CODEC.fieldOf("target").forGetter(GuiEntry::target)
    ).apply(instance, GuiEntry::new));

    public GuiEntry {
        Objects.requireNonNull(configEntry, "configEntry");
        Objects.requireNonNull(icon, "icon");
        Objects.requireNonNull(target, "target");
    }
}
