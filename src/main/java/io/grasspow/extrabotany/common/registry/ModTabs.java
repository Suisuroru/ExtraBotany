package io.grasspow.extrabotany.common.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static io.grasspow.extrabotany.ExtraBotany.MOD_ID;
import static io.grasspow.extrabotany.ExtraBotany.logger;
import static io.grasspow.extrabotany.common.registry.ModBlocks.MOD_BLOCKS;
import static io.grasspow.extrabotany.common.registry.ModItems.MOD_ITEMS;
import static io.grasspow.extrabotany.common.registry.ModItems.PYLON;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB_EXTRABOTANY = CREATIVE_MODE_TABS.register("tab_extrabotany", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.extrabotany").withStyle(style -> style.withColor(ChatFormatting.WHITE)))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> PYLON.get().getDefaultInstance())
            .backgroundSuffix("botania.png")
            .withSearchBar()
            .displayItems((parameters, output) -> {
                MOD_ITEMS.forEach(item -> {
                    output.accept(item.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                });
                MOD_BLOCKS.forEach(item -> {
                    output.accept(item.get());
                });
            }).build());

    public static void init(IEventBus modEventBus) {
        CREATIVE_MODE_TABS.register(modEventBus);
        logger.debug("Mod Tabs Initialized");
    }
}
