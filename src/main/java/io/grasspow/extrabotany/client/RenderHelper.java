package io.grasspow.extrabotany.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.mixin.client.ItemRendererAccessor;

import java.util.List;

public class RenderHelper {
    public static void renderItemCustomColor(LivingEntity entity, ItemStack stack, int color, PoseStack ms, MultiBufferSource buffers, int light, int overlay, @Nullable BakedModel model) {
        ms.pushPose();
        if (model == null) {
            model = Minecraft.getInstance().getItemRenderer().getModel(stack, entity.level(), entity, entity.getId());
        }
        model.getTransforms().getTransform(ItemDisplayContext.NONE).apply(false, ms);
        ms.translate(-0.5D, -0.5D, -0.5D);

        if (!model.isCustomRenderer() && !stack.is(Items.TRIDENT)) {
            RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, true);
            VertexConsumer ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffers, rendertype, true, stack.hasFoil());
            renderBakedItemModel(model, stack, color, light, overlay, ms, ivertexbuilder);
        } else {
            throw new IllegalArgumentException("Custom renderer items not supported");
        }

        ms.popPose();
    }

    public static void renderItemCustomColor(LivingEntity entity, ItemStack stack, int color, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        renderItemCustomColor(entity, stack, color, ms, buffers, light, overlay, null);
    }

    // [VanillaCopy] ItemRenderer with custom color
    private static void renderBakedItemModel(BakedModel model, ItemStack stack, int color, int light, int overlay, PoseStack ms, VertexConsumer buffer) {
        var random = RandomSource.create();
        long i = 42L;

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderBakedItemQuads(ms, buffer, color, model.getQuads(null, direction, random), stack, light, overlay);
        }

        random.setSeed(42L);
        renderBakedItemQuads(ms, buffer, color, model.getQuads(null, null, random), stack, light, overlay);
    }

    // [VanillaCopy] ItemRenderer, with custom color + alpha support
    private static void renderBakedItemQuads(PoseStack ms, VertexConsumer buffer, int color, List<BakedQuad> quads, ItemStack stack, int light, int overlay) {
        float a = ((color >> 24) & 0xFF) / 255.0F;
        float r = (float) (color >> 16 & 0xFF) / 255.0F;
        float g = (float) (color >> 8 & 0xFF) / 255.0F;
        float b = (float) (color & 0xFF) / 255.0F;

        buffer = new DelegatedVertexConsumer(buffer) {
            @Override
            public VertexConsumer color(float red, float green, float blue, float alpha) {
                return super.color(r, g, b, a);
            }
        };
        ((ItemRendererAccessor) Minecraft.getInstance().getItemRenderer())
                .callRenderQuadList(ms, buffer, quads, stack, light, overlay);
    }

    static class DelegatedVertexConsumer implements VertexConsumer {
        private final VertexConsumer delegate;

        DelegatedVertexConsumer(VertexConsumer delegate) {
            this.delegate = delegate;
        }

        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            return delegate.vertex(x, y, z);
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha) {
            return delegate.color(red, green, blue, alpha);
        }

        @Override
        public VertexConsumer uv(float u, float v) {
            return delegate.uv(u, v);
        }

        @Override
        public VertexConsumer overlayCoords(int u, int v) {
            return delegate.overlayCoords(u, v);
        }

        @Override
        public VertexConsumer uv2(int u, int v) {
            return delegate.uv2(u, v);
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            return delegate.normal(x, y, z);
        }

        @Override
        public void endVertex() {
            delegate.endVertex();
        }

        @Override
        public void defaultColor(int red, int green, int blue, int alpha) {
            delegate.defaultColor(red, green, blue, alpha);
        }

        @Override
        public void unsetDefaultColor() {
            delegate.unsetDefaultColor();
        }
    }
}
