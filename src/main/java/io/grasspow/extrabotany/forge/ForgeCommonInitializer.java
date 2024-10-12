package io.grasspow.extrabotany.forge;

import com.google.common.base.Suppliers;
import io.grasspow.extrabotany.common.effect.brew.ExtraBotanyBrews;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.item.brew.InfiniteWineItem;
import io.grasspow.extrabotany.common.item.equipment.bauble.MoonPendantItem;
import io.grasspow.extrabotany.common.item.equipment.bauble.SagesManaRingItem;
import io.grasspow.extrabotany.common.item.equipment.bauble.SunRingItem;
import io.grasspow.extrabotany.common.item.equipment.weapon.InfluxWaverItem;
import io.grasspow.extrabotany.common.item.equipment.weapon.StarWrathItem;
import io.grasspow.extrabotany.common.item.equipment.weapon.TrueShadowKatanaItem;
import io.grasspow.extrabotany.common.item.equipment.weapon.TrueTerraBladeItem;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.*;
import io.grasspow.extrabotany.forge.network.ForgePacketHandler;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.BotaniaRegistries;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.forge.CapabilityUtil;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;


@Mod(LibMisc.MOD_ID)
public class ForgeCommonInitializer {
    private static IEventBus modEventBus;
    public ForgeCommonInitializer(FMLJavaModLoadingContext context) {
        modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        registryInit();
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        ForgePacketHandler.init();
        registerEvents();
    }

    public static void registryInit() {
        ExtraBotanySounds.getSounds().register(modEventBus);

        ExtraBotanyBlocks.getBlocks().register(modEventBus);
        ExtraBotanyItems.getItems().register(modEventBus);

        ExtraBotanyEntities.Blocks.getBlockEntityTypes().register(modEventBus);
        ExtraBotanyEntities.getEntityTypes().register(modEventBus);

        ExtraBotanyRecipeTypes.getRecipeTypes().register(modEventBus);
        ExtraBotanyRecipeTypes.getRecipeSerializers().register(modEventBus);
        bind(BotaniaRegistries.BREWS, ExtraBotanyBrews::submitRegistrations);
        ExtraBotanyTabs.getTabs().register(modEventBus);
    }

    private void registerEvents() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addGenericListener(ItemStack.class, this::attachItemCaps);
    }

    private static final Supplier<Map<Item, Function<ItemStack, ManaItem>>> MANA_ITEM = Suppliers.memoize(() -> Map.of(
            ExtraBotanyItems.SAGES_MANA_RING.get(), SagesManaRingItem.SagesManaRingItemImpl::new
    ));

    private static final Supplier<Map<Item, Function<ItemStack, Relic>>> RELIC = Suppliers.memoize(() -> Map.of(
            ExtraBotanyItems.INFINITE_WINE.get(), InfiniteWineItem::makeRelic,
            ExtraBotanyItems.SUN_RING.get(), SunRingItem::makeRelic,
            ExtraBotanyItems.MOON_PENDANT.get(), MoonPendantItem::makeRelic,
            ExtraBotanyItems.SAGES_MANA_RING.get(), SagesManaRingItem::makeRelic,
            ExtraBotanyItems.INFLUX_WAVER.get(), InfluxWaverItem::makeRelic,
            ExtraBotanyItems.STAR_WRATH.get(), StarWrathItem::makeRelic,
            ExtraBotanyItems.TRUE_SHADOW_KATANA.get(), TrueShadowKatanaItem::makeRelic,
            ExtraBotanyItems.TRUE_TERRA_BLADE.get(), TrueTerraBladeItem::makeRelic
    ));


    private void attachItemCaps(AttachCapabilitiesEvent<ItemStack> e) {
        var stack = e.getObject();
        var makeManaItem = MANA_ITEM.get().get(stack.getItem());
        if (makeManaItem != null) {
            e.addCapability(prefix("mana_item"),
                    CapabilityUtil.makeProvider(BotaniaForgeCapabilities.MANA_ITEM, makeManaItem.apply(stack)));
        }
        var makeRelic = RELIC.get().get(stack.getItem());
        if (makeRelic != null) {
            e.addCapability(prefix("relic"),
                    CapabilityUtil.makeProvider(BotaniaForgeCapabilities.RELIC, makeRelic.apply(stack)));
        }
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        modEventBus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }
}
