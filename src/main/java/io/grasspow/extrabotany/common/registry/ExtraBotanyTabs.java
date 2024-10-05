package io.grasspow.extrabotany.common.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.item.CustomCreativeTabContents;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;
import static io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks.MOD_BLOCKS;
import static io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks.MOD_FLOWERS;
import static io.grasspow.extrabotany.common.registry.ExtraBotanyItems.MOD_ITEMS;
import static io.grasspow.extrabotany.common.registry.ExtraBotanyItems.PYLON;

public class ExtraBotanyTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = TABS.register("tab_extrabotany", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.extrabotany").withStyle(style -> style.withColor(ChatFormatting.WHITE)))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> PYLON.get().getDefaultInstance())
            .backgroundSuffix("botania.png")
            .withSearchBar()
            .displayItems((parameters, output) -> {
                MOD_FLOWERS.forEach(item -> {
                    output.accept(item.get());
                });
                MOD_BLOCKS.forEach(item -> {
                    output.accept(item.get());
                });
                MOD_ITEMS.forEach(item -> {
                    if (item.get() instanceof CustomCreativeTabContents cc) {
                        cc.addToCreativeTab(item.get(), output);
                    } else {
                        output.accept(item.get());
                    }
                });
            }).build());

    public static DeferredRegister<CreativeModeTab> getTabs() {
        return TABS;
    }
}
