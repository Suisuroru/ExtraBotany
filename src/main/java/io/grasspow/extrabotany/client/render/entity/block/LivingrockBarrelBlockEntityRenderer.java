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
import io.grasspow.extrabotany.common.entity.block.LivingrockBarrelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.client.book.LiquidBlockVertexConsumer;

public class LivingrockBarrelBlockEntityRenderer implements BlockEntityRenderer<LivingrockBarrelBlockEntity> {

    public LivingrockBarrelBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(@Nullable LivingrockBarrelBlockEntity barrel, float f, PoseStack matrixStack, MultiBufferSource buffers, int light, int overlay) {
        if (barrel.getFluidAmount() > 0) {
            BlockState state = Fluids.WATER.defaultFluidState().createLegacyBlock();
            matrixStack.pushPose();
            float fillAmount = barrel.getFluidProportion();

            matrixStack.translate(0.06F, 0.1875F, 0.06F);
            matrixStack.scale(0.88F, fillAmount * 0.9F, 0.88F);

            BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
            blockRenderer.renderLiquid(
                    barrel.getBlockPos(),
                    barrel.getLevel(),
                    new LiquidBlockVertexConsumer(
                            buffers.getBuffer(
                                    ItemBlockRenderTypes.getRenderLayer(Fluids.WATER.defaultFluidState())),
                            matrixStack,
                            barrel.getBlockPos()),
                    state,
                    Fluids.WATER.defaultFluidState());
            matrixStack.popPose();
        }
    }

}
