package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.client.render.entity.BaseProjectileRender;
import io.grasspow.extrabotany.client.render.entity.DummyRender;
import io.grasspow.extrabotany.client.render.entity.block.LivingrockBarrelBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PedestalBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PowerFrameBlockEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;
import vazkii.botania.client.render.entity.EntityRenderers;

public class ExtraBotanyEntityRenderers {

    public static void registerBlockEntityRenderers(EntityRenderers.BERConsumer consumer) {
        consumer.register(ExtraBotanyEntities.Blocks.PEDESTAL_BLOCK_ENTITY.get(), PedestalBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.LIVINGROCK_BARREL_BLOCK_ENTITY.get(), LivingrockBarrelBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.POWER_FRAME_BLOCK_ENTITY.get(), PowerFrameBlockEntityRenderer::new);

        //flower 1.16
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.ANNOYING_FLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.SERENITIAN.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.BELL_FLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.EDELWEISS.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.GEMINI_ORCHID.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.SUN_BLESS.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.MOON_BLESS.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.OMNI_VIOLET.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.REIKAR_LILY.get(), SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyEntities.Blocks.Flowers.TINKLE_FLOWER.get(), SpecialFlowerBlockEntityRenderer::new);
    }

    public static void registerEntityRenderers(EntityRenderers.EntityRendererConsumer consumer) {
        consumer.accept(ExtraBotanyEntities.SPLASH_GRENADE.get(), ThrownItemRenderer::new);
        consumer.accept(ExtraBotanyEntities.AURA_FILE.get(), DummyRender::new);
        consumer.accept(ExtraBotanyEntities.INFLUX_WAVER_PROJECTILE.get(), BaseProjectileRender::new);
        consumer.accept(ExtraBotanyEntities.TRUE_SHADOW_KATANA_PROJECTILE.get(), BaseProjectileRender::new);
        consumer.accept(ExtraBotanyEntities.TRUE_TERRA_BLADE_PROJECTILE.get(), BaseProjectileRender::new);
    }
}
