package io.grasspow.extrabotany.forge.client.render;

import io.grasspow.extrabotany.client.render.entity.BaseProjectileRender;
import io.grasspow.extrabotany.client.render.entity.DummyRender;
import io.grasspow.extrabotany.client.render.entity.block.LivingrockBarrelBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PedestalBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.block.PowerFrameBlockEntityRenderer;
import io.grasspow.extrabotany.client.render.entity.ego.EGOLandmineRender;
import io.grasspow.extrabotany.client.render.entity.ego.EGORender;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
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
        consumer.accept(ExtraBotanyEntities.PHANTOM_SWORD_PROJECTILE.get(), BaseProjectileRender::new);
        consumer.accept(ExtraBotanyEntities.MAGIC_ARROW_PROJECTILE.get(), DummyRender::new);

        //ego
        consumer.accept(ExtraBotanyEntities.EGO.get(), EGORender::new);
        consumer.accept(ExtraBotanyEntities.EGO_MINION.get(), EGORender::new);
        consumer.accept(ExtraBotanyEntities.EGO_LANDMINE.get(), EGOLandmineRender::new);
    }
}
