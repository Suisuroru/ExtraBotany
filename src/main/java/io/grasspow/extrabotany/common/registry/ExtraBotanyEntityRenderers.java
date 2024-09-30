package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.client.render.entity.block.LivingrockBarrelBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PedestalBlockEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import vazkii.botania.client.render.entity.EntityRenderers;

public class ExtraBotanyEntityRenderers {

    public static void registerBlockEntityRenderers(vazkii.botania.client.render.entity.EntityRenderers.BERConsumer consumer) {
        consumer.register(ExtraBotanyEntities.Blocks.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.LIVINGROCK_BARREL_BLOCK_ENTITY.get(), LivingrockBarrelBlockEntityRenderer::new);
    }

    public static void registerEntityRenderers(EntityRenderers.EntityRendererConsumer consumer) {
        consumer.accept(ExtraBotanyEntities.Brews.SPLASH_GRENADE, ThrownItemRenderer::new);
    }
}
