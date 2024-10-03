package io.grasspow.extrabotany.client.render.entity.block;

import com.mojang.blaze3d.vertex.PoseStack;
import io.grasspow.extrabotany.common.entity.block.PowerFrameBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class PowerFrameBlockEntityRenderer implements BlockEntityRenderer<PowerFrameBlockEntity> {

    public PowerFrameBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(PowerFrameBlockEntity powerFrame, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int combinedLightIn, int combinedOverlayIn) {
        var stack = powerFrame.getItem();
        if (!stack.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.translate(0.5F, 0.4F, 0.5F);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, combinedLightIn, combinedOverlayIn, matrixStack, iRenderTypeBuffer, powerFrame.getLevel(), 0);
            matrixStack.popPose();
        }
    }
}
