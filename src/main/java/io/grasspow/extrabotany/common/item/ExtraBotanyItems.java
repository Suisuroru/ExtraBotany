package io.grasspow.extrabotany.common.item;

import io.grasspow.extrabotany.common.block.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.item.equipment.bauble.CosmeticBaubleItem;
import io.grasspow.extrabotany.common.item.equipment.tool.ElementiumHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.ManasteelHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.TerrasteelHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.UltimateHammer;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.grasspow.extrabotany.ExtraBotany.MOD_ID;
import static io.grasspow.extrabotany.ExtraBotany.logger;

public class ExtraBotanyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final ArrayList<RegistryObject<Item>> MOD_ITEMS = new ArrayList<>();
    public static final ArrayList<RegistryObject<BlockItem>> MOD_BLOCK_ITEMS = new ArrayList<>();

    //block
    public static final RegistryObject<BlockItem> PHOTONIUM_BLOCK_ITEM = regDefBlockItem(ExtraBotanyBlocks.PHOTONIUM_BLOCK);
    public static final RegistryObject<BlockItem> SHADOWIUM_BLOCK_ITEM = regDefBlockItem(ExtraBotanyBlocks.SHADOWIUM_BLOCK);
    public static final RegistryObject<BlockItem> PEDESTAL_ITEM = regDefBlockItem(ExtraBotanyBlocks.PEDESTAL);

    // cosmetic bauble
    public static final RegistryObject<Item> PYLON = regDefItem(LibItemNames.PYLON,
            CosmeticBaubleItem::new, CosmeticBaubleItem.Variant.PYLON, new Item.Properties()
    );

    // food
    public static final RegistryObject<Item> SPIRIT_FUEL = regDefItem(LibItemNames.SPIRIT_FUEL, food(ExtraBotanyFoods.SPIRIT_FUEL));
    public static final RegistryObject<Item> NIGHTMARE_FUEL = regDefItem(LibItemNames.NIGHTMARE_FUEL, ItemNightmareFuel::new, food(ExtraBotanyFoods.NIGHTMARE_FUEL));
    public static final RegistryObject<Item> GILDED_MASHED_POTATO = regDefItem(LibItemNames.GILDED_MASHED_POTATO, food(ExtraBotanyFoods.GILDED_MASHED_POTATO));
    public static final RegistryObject<Item> FRIED_CHICKEN = regDefItem(LibItemNames.FRIED_CHICKEN, food(ExtraBotanyFoods.FRIED_CHICKEN));
    public static final RegistryObject<Item> MANA_DRINK = regDefItem(LibItemNames.MANA_DRINK, food(ExtraBotanyFoods.MANA_DRINK));

    //tool
    public static final RegistryObject<Item> MANASTEEL_HAMMER = regDefItem(LibItemNames.MANASTEEL_HAMMER, ManasteelHammer::new, nonStackable());
    public static final RegistryObject<Item> ELEMENTIUM_HAMMER = regDefItem(LibItemNames.ELEMENTIUM_HAMMER, ElementiumHammer::new, nonStackable());
    public static final RegistryObject<Item> TERRASTEEL_HAMMER = regDefItem(LibItemNames.TERRASTEEL_HAMMER, TerrasteelHammer::new, nonStackable());
    public static final RegistryObject<Item> ULTIMATE_HAMMER = regDefItem(LibItemNames.ULTIMATE_HAMMER, UltimateHammer::new, nonStackable());

    //misc
    public static final RegistryObject<Item> GILDED_POTATO = regDefItem(LibItemNames.GILDED_POTATO,defaultItem());
    public static final RegistryObject<Item> PHOTONIUM = regDefItem(LibItemNames.PHOTONIUM,defaultItem());
    public static final RegistryObject<Item> SHADOWIUM = regDefItem(LibItemNames.SHADOWIUM,defaultItem());
    public static final RegistryObject<Item> SPIRIT = regDefItem(LibItemNames.SPIRIT, defaultItem());

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        logger.info("Mod Items Initialized");
    }

    public static RegistryObject<BlockItem> regDefBlockItem(RegistryObject<Block> block) {
        RegistryObject<BlockItem> item = ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        MOD_BLOCK_ITEMS.add(item);
        return item;
    }

    private static RegistryObject<Item> regDefItem(String name, Item.Properties props) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new Item(props));
        MOD_ITEMS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<Item> regDefItem(String name, Function<Item.Properties, ? extends I> func, Item.Properties props) {
        RegistryObject<Item> item = ITEMS.register(name, () -> func.apply(props));
        MOD_ITEMS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<Item> regDefItem(String name, BiFunction<CosmeticBaubleItem.Variant, Item.Properties, ? extends I> func, CosmeticBaubleItem.Variant variant, Item.Properties props) {
        RegistryObject<Item> item = ITEMS.register(name, () -> func.apply(variant, props));
        MOD_ITEMS.add(item);
        return item;
    }

    private static Item.Properties defaultItem() {
        return new Item.Properties();
    }

    private static Item.Properties food(FoodProperties props) {
        return defaultItem().food(props);
    }

    private static Item.Properties nonStackable() {
        return defaultItem().stacksTo(1);
    }

    private static Item.Properties stackTo16() {
        return defaultItem().stacksTo(16);
    }
}
