package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.common.item.EmptyBottleItem;
import io.grasspow.extrabotany.common.item.ManaDrinkItem;
import io.grasspow.extrabotany.common.item.NightmareFuelItem;
import io.grasspow.extrabotany.common.item.brew.CocktailItem;
import io.grasspow.extrabotany.common.item.brew.InfiniteWineItem;
import io.grasspow.extrabotany.common.item.brew.SplashGrenadeItem;
import io.grasspow.extrabotany.common.item.equipment.bauble.CosmeticBaubleItem;
import io.grasspow.extrabotany.common.item.equipment.tool.ElementiumHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.ManasteelHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.TerrasteelHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.UltimateHammer;
import io.grasspow.extrabotany.common.item.food.ModFoods;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;

public class ExtraBotanyItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final ArrayList<RegistryObject<Item>> MOD_ITEMS = new ArrayList<>();
    public static final ArrayList<RegistryObject<BlockItem>> MOD_BLOCK_ITEMS = new ArrayList<>();

    //block
    public static final RegistryObject<BlockItem> PHOTONIUM_BLOCK_ITEM = regDefBlockItem(ExtraBotanyBlocks.PHOTONIUM_BLOCK);
    public static final RegistryObject<BlockItem> SHADOWIUM_BLOCK_ITEM = regDefBlockItem(ExtraBotanyBlocks.SHADOWIUM_BLOCK);
    public static final RegistryObject<BlockItem> ORICHALCOS_BLOCK = regDefBlockItem(ExtraBotanyBlocks.ORICHALCOS_BLOCK);
    public static final RegistryObject<BlockItem> PEDESTAL_ITEM = regDefBlockItem(ExtraBotanyBlocks.PEDESTAL);
    public static final RegistryObject<BlockItem> MANA_BUFFER_ITEM = regDefBlockItem(ExtraBotanyBlocks.MANA_BUFFER);
    public static final RegistryObject<BlockItem> QUANTUM_MANA_BUFFER_ITEM = regDefBlockItem(ExtraBotanyBlocks.QUANTUM_MANA_BUFFER);
    public static final RegistryObject<BlockItem> TROPHY_ITEM = regDefBlockItem(ExtraBotanyBlocks.TROPHY);
    public static final RegistryObject<BlockItem> LIVINGROCK_BARREL_ITEM = regDefBlockItem(ExtraBotanyBlocks.LIVINGROCK_BARREL);

    // cosmetic bauble
    public static final RegistryObject<Item> PYLON = regDefItem(LibItemNames.PYLON,
            CosmeticBaubleItem::new, CosmeticBaubleItem.Variant.PYLON, new Item.Properties()
    );

    // food
    public static final RegistryObject<Item> SPIRIT_FUEL = regDefItem(LibItemNames.SPIRIT_FUEL, food(ModFoods.SPIRIT_FUEL));
    public static final RegistryObject<Item> NIGHTMARE_FUEL = regDefItem(LibItemNames.NIGHTMARE_FUEL, NightmareFuelItem::new, food(ModFoods.NIGHTMARE_FUEL));
    public static final RegistryObject<Item> GILDED_MASHED_POTATO = regDefItem(LibItemNames.GILDED_MASHED_POTATO, food(ModFoods.GILDED_MASHED_POTATO));
    public static final RegistryObject<Item> FRIED_CHICKEN = regDefItem(LibItemNames.FRIED_CHICKEN, food(ModFoods.FRIED_CHICKEN));
    public static final RegistryObject<Item> MANA_DRINK = regDefItem(LibItemNames.MANA_DRINK, ManaDrinkItem::new, food(ModFoods.MANA_DRINK));

    //brew
    public static final RegistryObject<Item> COCKTAIL = regDefItem(LibItemNames.COCKTAIL, CocktailItem::new, stackTo32());
    public static final RegistryObject<Item> INFINITE_WINE = regDefItem(LibItemNames.INFINITE_WINE, InfiniteWineItem::new, nonStackable());
    public static final RegistryObject<Item> SPLASH_GRENADE = regDefItem(LibItemNames.SPLASH_GRENADE, SplashGrenadeItem::new, stackTo32());

    //tool
    public static final RegistryObject<Item> MANASTEEL_HAMMER = regDefItem(LibItemNames.MANASTEEL_HAMMER, ManasteelHammer::new, nonStackable());
    public static final RegistryObject<Item> ELEMENTIUM_HAMMER = regDefItem(LibItemNames.ELEMENTIUM_HAMMER, ElementiumHammer::new, nonStackable());
    public static final RegistryObject<Item> TERRASTEEL_HAMMER = regDefItem(LibItemNames.TERRASTEEL_HAMMER, TerrasteelHammer::new, nonStackable());
    public static final RegistryObject<Item> ULTIMATE_HAMMER = regDefItem(LibItemNames.ULTIMATE_HAMMER, UltimateHammer::new, nonStackable());

    //misc
    public static final RegistryObject<Item> GILDED_POTATO = regDefItem(LibItemNames.GILDED_POTATO,defaultItem());
    public static final RegistryObject<Item> PHOTONIUM = regDefItem(LibItemNames.PHOTONIUM,defaultItem());
    public static final RegistryObject<Item> SHADOWIUM = regDefItem(LibItemNames.SHADOWIUM,defaultItem());
    public static final RegistryObject<Item> ORICHALCOS = regDefItem(LibItemNames.ORICHALCOS, defaultItem());
    public static final RegistryObject<Item> SPIRIT = regDefItem(LibItemNames.SPIRIT, defaultItem());
    public static final RegistryObject<Item> EMPTY_BOTTLE = regDefItem(LibItemNames.EMPTY_BOTTLE, EmptyBottleItem::new, defaultItem());
    public static final RegistryObject<Item> HERO_MEDAL = regDefItem(LibItemNames.HERO_MEDAL, defaultItem());

    private static RegistryObject<BlockItem> regDefBlockItem(RegistryObject<Block> block) {
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

    private static Item.Properties stackTo32() {
        return defaultItem().stacksTo(32);
    }

    public static DeferredRegister<Item> getItems() {
        return ITEMS;
    }

    //    public static class DefaultStacks {
//        public static ItemStack gilded_potato = new ItemStack(ExtraBotanyItems.GILDED_POTATO.get());
//
//        static {
//            gilded_potato.addTagElement("Enchantments", get());
//            gilded_potato.getOrCreateTag().putShort("pedestal_deny", (short) 1);
//        }
//
//        private static ListTag get() {
//            ListTag listTag = new ListTag();
//            CompoundTag tag = new CompoundTag();
//            tag.putString("id", "minecraft:protection");
//            tag.putShort("lvl", (short) 4);
//            listTag.add(tag);
//            CompoundTag tag1 = new CompoundTag();
//            tag.putString("id", "minecraft:blast_protection");
//            tag.putShort("lvl", (short) 4);
//            listTag.add(tag1);
//            CompoundTag tag2 = new CompoundTag();
//            tag.putString("id", "minecraft:fire_protection");
//            tag.putShort("lvl", (short) 4);
//            listTag.add(tag2);
//            CompoundTag tag3 = new CompoundTag();
//            tag.putString("id", "minecraft:projectile_protection");
//            tag.putShort("lvl", (short) 4);
//            listTag.add(tag3);
//            return listTag;
//        }
//
//
//    }
}
