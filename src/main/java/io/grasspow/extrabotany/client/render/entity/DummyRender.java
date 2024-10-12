package io.grasspow.extrabotany.client.render.entity;

import io.grasspow.extrabotany.common.entity.projectile.AuraFireProjectile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class DummyRender extends EntityRenderer<AuraFireProjectile> {

    public DummyRender(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(AuraFireProjectile pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
