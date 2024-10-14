package io.grasspow.extrabotany.client.render.entity.ego;

import com.mojang.blaze3d.vertex.PoseStack;
import io.grasspow.extrabotany.common.entity.ego.EGOLandmine;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;

import javax.annotation.Nonnull;

public class EGOLandmineRender extends EntityRenderer<EGOLandmine> {
    private static final double INITIAL_OFFSET = -1.0 / 16 + 0.005;
    // Global y offset so that overlapping landmines do not Z-fight
    public static double offY = INITIAL_OFFSET;

    public EGOLandmineRender(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    public static void onWorldRenderLast(RenderLevelStageEvent.Stage evt) {
        offY = INITIAL_OFFSET;
    }

    @Override
    public void render(EGOLandmine e, float entityYaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        super.render(e, entityYaw, partialTicks, ms, buffers, light);

        ms.pushPose();
        AABB aabb = e.getBoundingBox().move(e.position().scale(-1));

        float gs = (float) (Math.sin(ClientTickHandler.total() / 20) + 1) * 0.2F + 0.6F;
        int r = 0, g = 0, b = 0;
        switch (e.getLandmineType()) {
            case 0 -> b = 240;
            case 1 -> g = 240;
            case 2 -> r = 240;
        }
        r = (int) (r * gs);
        g = (int) (g * gs);
        b = (int) (b * gs);

        int color = r << 16 | g << 8 | b;

        int alpha = 32;
        if (e.tickCount < 8) {
            alpha *= Math.min((e.tickCount + partialTicks) / 8F, 1F);
        } else if (e.tickCount > 47) {
            alpha *= Math.min(1F - (e.tickCount - 47 + partialTicks) / 8F, 1F);
        }

        SpecialFlowerBlockEntityRenderer.renderRectangle(ms, buffers, aabb, false, color, (byte) alpha);
        offY += 0.001;
        ms.popPose();
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@NotNull EGOLandmine entity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
