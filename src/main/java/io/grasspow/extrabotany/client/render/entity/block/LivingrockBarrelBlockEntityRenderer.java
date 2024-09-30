/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package io.grasspow.extrabotany.client.render.entity.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.grasspow.extrabotany.common.entity.block.LivingrockBarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.helper.VecHelper;

import java.util.Objects;

public class LivingrockBarrelBlockEntityRenderer implements BlockEntityRenderer<LivingrockBarrelBlockEntity> {

    // Overrides for when we call this renderer from a cart
    private final TextureAtlasSprite waterSprite;

    public LivingrockBarrelBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.waterSprite = Objects.requireNonNull(
                Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                        .apply(new ResourceLocation("block/water"))
        );
    }

    //todo:render barrel‘s fluid with amount
    @Override
    public void render(@Nullable LivingrockBarrelBlockEntity barrel, float f, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        int insideUVStart = 1;
        int insideUVEnd = 16 - insideUVStart;
        float poolBottom = 1F / 16F + 0.001F;
        float poolTop = 15F / 16F;
        int current = barrel == null ? 0 : barrel.getAmount();
        int max = barrel == null ? 0 : barrel.getMax();
        float level = (float) current / (float) max;
        if (level > 0) {
            ms.pushPose();
            ms.translate(0, Mth.clampedMap(level, 0, 1, poolBottom, poolTop), 0);
            ms.mulPose(VecHelper.rotateX(90F));
            VertexConsumer buffer = buffers.getBuffer(RenderType.waterMask());
            RenderHelper.renderIconCropped(
                    ms, buffer,
                    insideUVStart, insideUVStart, insideUVEnd, insideUVEnd,
                    this.waterSprite, 0xFFFFFF, 1, light);
            ms.popPose();
        }
    }

}
