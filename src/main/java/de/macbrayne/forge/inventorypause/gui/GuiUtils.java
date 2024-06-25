// SPDX-License-Identifier: EUPL-1.2

package de.macbrayne.forge.inventorypause.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.macbrayne.forge.inventorypause.common.PauseMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GuiUtils {
    static final float blitOffset = 0f;

    public static void renderButtonItem(ItemStack itemStack, int x, int y, int buttonSize) {
        renderButtonItem(itemStack, x, y, Minecraft.getInstance().getItemRenderer().getModel(itemStack, null, null, 0), buttonSize);
    }

    protected static void renderButtonItem(ItemStack itemStack, int x, int y, BakedModel bakedModel, int buttonSize) {
        //noinspection deprecation
        Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        //noinspection deprecation
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();

        posestack.translate(x, y, (double) (100.0F + blitOffset));
        posestack.translate(buttonSize / 2f, buttonSize / 2f, 0.0D); // Delta Vanilla code
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(14.0F, 14.0F, 14.0F); // Delta Vanilla code
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !bakedModel.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(itemStack, ItemDisplayContext.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, bakedModel);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }

    public static Button.OnPress getTogglePress(Supplier<PauseMode> supplier, Consumer<PauseMode> consumer) {
        return button -> consumer.accept(PauseMode.getNext(supplier.get()));
    }
}
