package io.grasspow.extrabotany.common.handler;


import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static io.grasspow.extrabotany.ExtraBotany.logger;
import static io.grasspow.extrabotany.ExtraBotany.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class EventHandler {

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        logger.info("HELLO from server starting");
    }
//
//    @SubscribeEvent
//    public static void onClientSetup(FMLClientSetupEvent event)
//    {
//        // Some client setup code
//        LOGGER.info("HELLO FROM CLIENT SETUP");
//        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//    }
}
