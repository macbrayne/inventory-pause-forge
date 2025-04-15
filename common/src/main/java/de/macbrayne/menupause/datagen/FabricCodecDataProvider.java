/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.macbrayne.menupause.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class FabricCodecDataProvider<T> implements DataProvider {
    private final PackOutput.PathProvider pathResolver;
    private final CompletableFuture<HolderLookup.Provider> registriesFuture;
    private final Codec<T> codec;

    protected FabricCodecDataProvider(PackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture, PackOutput.Target outputType, String directoryName, Codec<T> codec) {
        this.pathResolver = dataOutput.createPathProvider(outputType, directoryName);
        this.registriesFuture = Objects.requireNonNull(registriesFuture);
        this.codec = codec;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        return this.registriesFuture.thenCompose(lookup -> {
            Map<ResourceLocation, JsonElement> entries = new HashMap<>();
            RegistryOps<JsonElement> ops = lookup.createSerializationContext(JsonOps.INSTANCE);

            BiConsumer<ResourceLocation, T> provider = (id, value) -> {
                JsonElement json = this.convert(id, value, ops);
                JsonElement existingJson = entries.put(id, json);

                if (existingJson != null) {
                    throw new IllegalArgumentException("Duplicate entry " + id);
                }
            };

            this.configure(provider, lookup);
            return this.write(writer, entries);
        });
    }

    /**
     * Implement this method to register entries to generate using a {@link HolderLookup.Provider}.
     *
     * @param provider A consumer that accepts an {@link ResourceLocation} and a value to register.
     * @param lookup   A lookup for registries.
     */
    protected abstract void configure(BiConsumer<ResourceLocation, T> provider, HolderLookup.Provider lookup);

    private JsonElement convert(ResourceLocation id, T value, DynamicOps<JsonElement> ops) {
        DataResult<JsonElement> dataResult = this.codec.encodeStart(ops, value);
        return dataResult
                .mapError(message -> "Invalid entry %s: %s".formatted(id, message))
                .getOrThrow();
    }

    private CompletableFuture<?> write(CachedOutput writer, Map<ResourceLocation, JsonElement> entries) {
        return CompletableFuture.allOf(entries.entrySet().stream().map(entry -> {
            Path path = this.pathResolver.json(entry.getKey());
            return DataProvider.saveStable(writer, entry.getValue(), path);
        }).toArray(CompletableFuture[]::new));
    }
}