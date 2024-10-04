package io.grasspow.extrabotany.forge.client;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.client.ExtraBotanyItemProperties;
import io.grasspow.extrabotany.client.render.ColorHandler;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExtraBotanyAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientInitializer {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ExtraBotanyEntityRenderers.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        ExtraBotanyEntityRenderers.registerEntityRenderers(event::registerEntityRenderer);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item evt) {
        ColorHandler.submitItems(evt::register);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
        ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));
    }
}
