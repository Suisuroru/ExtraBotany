package io.grasspow.extrabotany;

import io.grasspow.extrabotany.common.core.ConfigHandler;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtraBotany.MOD_ID)
public class ExtraBotany {

    public static final String MOD_ID = LibMisc.MOD_ID;
    public static final Logger logger = LogManager.getLogger(MOD_ID);

    public ExtraBotany(FMLJavaModLoadingContext context) {

        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModBlockEntities.init(modEventBus);
        ModBlocks.init(modEventBus);
        ModItems.init(modEventBus);
        ModTabs.init(modEventBus);
        ModRecipeTypes.init(modEventBus);
        ModSounds.init(modEventBus);
        context.registerConfig(ModConfig.Type.COMMON, ConfigHandler.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
//        logger.info("HELLO FROM COMMON SETUP");
//
//        if (ConfigHandler.logDirtBlock)
//            logger.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//
//        logger.info(ConfigHandler.magicNumberIntroduction + ConfigHandler.magicNumber);
//
//        ConfigHandler.items.forEach((item) -> logger.info("ITEM >> {}", item.toString()));
    }
}
