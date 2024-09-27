package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.client.render.block_entity.PedestalBlockEntityRenderer;

public class ExtraBotanyEntityRenders {

    public static void registerBlockEntityRenderers(vazkii.botania.client.render.entity.EntityRenderers.BERConsumer consumer) {
        consumer.register(ExtraBotanyBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntityRenderer::new);
    }
}
