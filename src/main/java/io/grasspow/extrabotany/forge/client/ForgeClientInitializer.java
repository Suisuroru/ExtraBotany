package io.grasspow.extrabotany.forge.client;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.client.ExtraBotanyItemProperties;
import io.grasspow.extrabotany.client.model.ExtraBotanyLayerDefinitions;
import io.grasspow.extrabotany.client.render.ColorHandler;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ExtraBotanyAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientInitializer {

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent evt) {
//    forge recommend to use json to define render_type, so abandon this implementation
//        BlockRenderLayers.init(ItemBlockRenderTypes::setRenderLayer);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
        ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions evt) {
        ExtraBotanyLayerDefinitions.init(evt::registerLayerDefinition);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ExtraBotanyEntityRenderers.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
        ExtraBotanyEntityRenderers.registerEntityRenderers(event::registerEntityRenderer);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item evt) {
        ColorHandler.submitItems(evt::register);
    }
}
