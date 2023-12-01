// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui;

import de.macbrayne.forge.inventorypause.InventoryPause;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.client.resources.metadata.gui.GuiMetadataSection;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

import java.util.Set;

public class CustomGuiSpriteManager extends TextureAtlasHolder {
    private static final Set<MetadataSectionSerializer<?>> METADATA_SECTIONS = Set.of(AnimationMetadataSection.SERIALIZER, GuiMetadataSection.TYPE);

    public CustomGuiSpriteManager(TextureManager textureManager) {
        super(textureManager, new ResourceLocation(InventoryPause.MOD_ID, "textures/atlas/gui.png"), new ResourceLocation(InventoryPause.MOD_ID, "gui"), METADATA_SECTIONS);
    }

    @Override
    public TextureAtlasSprite getSprite(ResourceLocation p_296464_) {
        return super.getSprite(p_296464_);
    }

    public GuiSpriteScaling getSpriteScaling(TextureAtlasSprite sprite) {
        return this.getMetadata(sprite).scaling();
    }

    private GuiMetadataSection getMetadata(TextureAtlasSprite sprite) {
        return sprite.contents().metadata().getSection(GuiMetadataSection.TYPE).orElse(GuiMetadataSection.DEFAULT);
    }
}
