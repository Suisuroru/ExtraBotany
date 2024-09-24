package io.grasspow.extrabotany;

import io.grasspow.extrabotany.common.block.block_entity.ExtraBotanyBlockEntities;
import io.grasspow.extrabotany.common.item.ExtraBotanyTabs;
import io.grasspow.extrabotany.common.block.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.core.ConfigHandler;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.ModRecipeTypes;
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
        ExtraBotanyBlockEntities.init(modEventBus);
        ExtraBotanyBlocks.init(modEventBus);
        ExtraBotanyItems.init(modEventBus);
        ExtraBotanyTabs.init(modEventBus);
        ModRecipeTypes.init(modEventBus);
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
