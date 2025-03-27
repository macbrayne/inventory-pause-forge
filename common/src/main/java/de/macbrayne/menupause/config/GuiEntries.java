// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.menupause.config;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.macbrayne.menupause.Constants;
import de.macbrayne.menupause.platform.Services;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class GuiEntries {
    public static final Codec<GuiEntry<?>> GUI_ENTRY_CODEC = Codec.either(GuiEntry.Icon.CODEC, GuiEntry.Text.CODEC).xmap(
            either -> either.map(Function.identity(), Function.identity()),
            entry -> entry instanceof GuiEntry.Icon icon ? Either.left(icon) : Either.right((GuiEntry.Text) entry)
    );
    public static final Codec<GuiEntries> ENTRIES_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GUI_ENTRY_CODEC.listOf().optionalFieldOf("buttons", new ArrayList<>()).forGetter(GuiEntries::entries)
    ).apply(instance, GuiEntries::new));
    public final List<GuiEntry<?>> entries = new ArrayList<>();

    public GuiEntries(List<GuiEntry<?>> entries) {
        this.entries.addAll(entries);
    }

    public GuiEntries() {
    }

    public List<GuiEntry<?>> entries() {
        return entries;
    }

    public static GuiEntries loadEntries() {
        return attemptLoadEntries(Services.PLATFORM.getConfigDir().resolve(Constants.MOD_ID + "/guientries.json")).orElseGet(
                () -> attemptLoadEntries(Services.PLATFORM.findResourceInOwningFile("data/menupause/guientries.json")).orElseThrow()
        );
    }

    private static Optional<GuiEntries> attemptLoadEntries(Path path) {
        return ConfigHelper.attemptLoad(path, ENTRIES_CODEC);
    }
}
