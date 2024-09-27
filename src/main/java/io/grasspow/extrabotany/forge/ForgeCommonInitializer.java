package io.grasspow.extrabotany.forge;

import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;


@Mod(LibMisc.MOD_ID)
public class ForgeCommonInitializer {
    private static IEventBus modEventBus;
    public ForgeCommonInitializer(FMLJavaModLoadingContext context) {
        modEventBus = context.getModEventBus();
        modEventBus.addListener(ForgeCommonInitializer::commonSetup);
        registryInit();
    }

    public static void commonSetup(FMLCommonSetupEvent evt) {
    }

    public static void registryInit() {
        ExtraBotanyBlocks.getBlocks().register(modEventBus);
        ExtraBotanyItems.getItems().register(modEventBus);

        ExtraBotanyEntities.Blocks.getBlockEntityTypes().register(modEventBus);
        bind(Registries.ENTITY_TYPE, ExtraBotanyEntities.Brews::registerEntities);

        ExtraBotanySounds.getSounds().register(modEventBus);

        ExtraBotanyRecipeTypes.getRecipeTypes().register(modEventBus);
        ExtraBotanyRecipeTypes.getRecipeSerializers().register(modEventBus);
//        ModEffects.getEffects().register(bus);
//        ModBrews.registerBrews();
        ExtraBotanyTabs.getTabs().register(modEventBus);
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        modEventBus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }
}
