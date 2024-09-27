package io.grasspow.extrabotany.forge;

import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(LibMisc.MOD_ID)
public class ForgeCommonInitializer {
    public ForgeCommonInitializer(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(ForgeCommonInitializer::commonSetup);
        registryInit(modEventBus);
    }

    public static void commonSetup(FMLCommonSetupEvent evt) {
    }

    public static void registryInit(IEventBus bus) {
        ExtraBotanyBlocks.getBlocks().register(bus);
        ExtraBotanyBlockEntities.getBlockEntityTypes().register(bus);
        ExtraBotanyItems.getItems().register(bus);
        ExtraBotanyTabs.getTabs().register(bus);
        ExtraBotanySounds.getSounds().register(bus);

        ExtraBotanyRecipeTypes.getRecipeTypes().register(bus);
        ExtraBotanyRecipeTypes.getRecipeSerializers().register(bus);
//        ModEffects.getEffects().register(bus);
//        ModBrews.registerBrews();
    }
}
