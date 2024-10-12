package io.grasspow.extrabotany.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.grasspow.extrabotany.common.entity.projectile.BaseSwordProjectile;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.client.core.helper.RenderHelper;

@OnlyIn(Dist.CLIENT)
public class BaseProjectileRender extends EntityRenderer<BaseSwordProjectile> {

    public BaseProjectileRender(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(BaseSwordProjectile weapon, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLightIn) {
        Minecraft mc = Minecraft.getInstance();
        matrixStack.pushPose();
        float s = 1.2F;
        matrixStack.scale(s, s, s);
        matrixStack.mulPose(Axis.YP.rotationDegrees(weapon.getRotation() + 90F));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(weapon.getPitch()));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(-45));

        float alpha = 0.9F;
        BakedModel model = weapon.getIcon();
        int color = 0xFFFFFF | ((int) (alpha * 255F)) << 24;
        RenderHelper.renderItemCustomColor(mc.player, new ItemStack(ExtraBotanyItems.INFLUX_WAVER.get()), color, matrixStack, bufferIn, 0xF000F0, OverlayTexture.NO_OVERLAY, model);

        matrixStack.scale(1 / s, 1 / s, 1 / s);
        matrixStack.popPose();
        super.render(weapon, entityYaw, partialTicks, matrixStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(BaseSwordProjectile pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
