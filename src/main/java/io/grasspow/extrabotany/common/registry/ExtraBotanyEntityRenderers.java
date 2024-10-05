package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.client.render.entity.block.LivingrockBarrelBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PedestalBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PowerFrameBlockEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;
import vazkii.botania.client.render.entity.EntityRenderers;

public class ExtraBotanyEntityRenderers {

    public static void registerBlockEntityRenderers(vazkii.botania.client.render.entity.EntityRenderers.BERConsumer consumer) {
        consumer.register(ExtraBotanyEntities.Blocks.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.LIVINGROCK_BARREL_BLOCK_ENTITY.get(), LivingrockBarrelBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.POWER_FRAME_BLOCK_ENTITY.get(), PowerFrameBlockEntityRenderer::new);

        //flower
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.ANNOYING_FLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.SERENITIAN.get(), SpecialFlowerBlockEntityRenderer::new);
    }

    public static void registerEntityRenderers(EntityRenderers.EntityRendererConsumer consumer) {
        consumer.accept(ExtraBotanyEntities.SPLASH_GRENADE.get(), ThrownItemRenderer::new);
    }
}
