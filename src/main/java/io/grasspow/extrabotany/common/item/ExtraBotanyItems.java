package io.grasspow.extrabotany.common.item;

import io.grasspow.extrabotany.common.item.brew.CocktailItem;
import io.grasspow.extrabotany.common.item.brew.InfiniteWineItem;
import io.grasspow.extrabotany.common.item.brew.SplashGrenadeItem;
import io.grasspow.extrabotany.common.item.equipment.armor.*;
import io.grasspow.extrabotany.common.item.equipment.bauble.*;
import io.grasspow.extrabotany.common.item.equipment.tool.ManaReader;
import io.grasspow.extrabotany.common.item.equipment.tool.RodOfDiscordItem;
import io.grasspow.extrabotany.common.item.equipment.tool.SilverBulletItem;
import io.grasspow.extrabotany.common.item.equipment.tool.WalkingCaneItem;
import io.grasspow.extrabotany.common.item.equipment.tool.hammer.ElementiumHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.hammer.ManasteelHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.hammer.TerrasteelHammer;
import io.grasspow.extrabotany.common.item.equipment.tool.hammer.UltimateHammer;
import io.grasspow.extrabotany.common.item.equipment.weapon.*;
import io.grasspow.extrabotany.common.item.food.ModFoods;
import io.grasspow.extrabotany.common.item.len.*;
import io.grasspow.extrabotany.common.item.misc.EmptyBottleItem;
import io.grasspow.extrabotany.common.item.misc.GoldClothItem;
import io.grasspow.extrabotany.common.item.misc.ManaDrinkItem;
import io.grasspow.extrabotany.common.item.misc.NightmareFuelItem;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.item.block.SpecialFlowerBlockItem;
import vazkii.botania.common.item.lens.Lens;
import vazkii.botania.common.item.lens.LensItem;
import vazkii.botania.common.item.record.BotaniaRecordItem;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;

public class ExtraBotanyItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final ArrayList<RegistryObject<Item>> MOD_ITEMS = new ArrayList<>();
    public static final ArrayList<RegistryObject<BlockItem>> MOD_BLOCK_ITEMS = new ArrayList<>();

    //bauble
    public static final RegistryObject<Item> AERO_STONE = regDefItem(LibItemNames.AERO_STONE, AeroStoneItem::new, nonStackable());
    public static final RegistryObject<Item> AQUA_STONE = regDefItem(LibItemNames.AQUA_STONE, AquaStoneItem::new, nonStackable());
    public static final RegistryObject<Item> EARTH_STONE = regDefItem(LibItemNames.EARTH_STONE, EarthStoneItem::new, nonStackable());
    public static final RegistryObject<Item> IGNIS_STONE = regDefItem(LibItemNames.IGNIS_STONE, IgnisStoneItem::new, nonStackable());
    public static final RegistryObject<Item> THE_COMMUNITY = regDefItem(LibItemNames.THE_COMMUNITY, TheCommunityItem::new, nonStackable());
    public static final RegistryObject<Item> PEACE_AMULET = regDefItem(LibItemNames.PEACE_AMULET, BaubleItem::new, nonStackable());
    public static final RegistryObject<Item> POWER_GLOVE = regDefItem(LibItemNames.POWER_GLOVE, PowerGloveItem::new, nonStackable());
    public static final RegistryObject<Item> FROST_STAR = regDefItem(LibItemNames.FROST_STAR, FrostStarItem::new, nonStackable());
    public static final RegistryObject<Item> DEATH_RING = regDefItem(LibItemNames.DEATH_RING, DeathRingItem::new, nonStackable());
    public static final RegistryObject<Item> MANA_DRIVE_RING = regDefItem(LibItemNames.MANA_DRIVE_RING, ManaDriveRingItem::new, nonStackable());
    public static final RegistryObject<Item> NATURE_ORB = regDefItem(LibItemNames.NATURE_ORB, NatureOrbItem::new, nonStackable());
    public static final RegistryObject<Item> JINGWEI_FEATHER = regDefItem(LibItemNames.JINGWEI_FEATHER, JingweiFeatherItem::new, nonStackable());
    public static final RegistryObject<Item> POTATO_CHIPS = regDefItem(LibItemNames.POTATO_CHIPS, PotatoChipsItem::new, nonStackable());
    public static final RegistryObject<Item> SUN_RING = regDefItem(LibItemNames.SUN_RING, SunRingItem::new, nonStackable());
    public static final RegistryObject<Item> MOON_PENDANT = regDefItem(LibItemNames.MOON_PENDANT, MoonPendantItem::new, nonStackable());
    public static final RegistryObject<Item> SAGES_MANA_RING = regDefItem(LibItemNames.SAGES_MANA_RING, SagesManaRingItem::new, nonStackable());

    // food
    public static final RegistryObject<Item> SPIRIT_FUEL = regDefItem(LibItemNames.SPIRIT_FUEL, food(ModFoods.SPIRIT_FUEL));
    public static final RegistryObject<Item> NIGHTMARE_FUEL = regDefItem(LibItemNames.NIGHTMARE_FUEL, NightmareFuelItem::new, food(ModFoods.NIGHTMARE_FUEL));
    public static final RegistryObject<Item> GILDED_MASHED_POTATO = regDefItem(LibItemNames.GILDED_MASHED_POTATO, food(ModFoods.GILDED_MASHED_POTATO));
    public static final RegistryObject<Item> FRIED_CHICKEN = regDefItem(LibItemNames.FRIED_CHICKEN, food(ModFoods.FRIED_CHICKEN));
    public static final RegistryObject<Item> MANA_DRINK = regDefItem(LibItemNames.MANA_DRINK, ManaDrinkItem::new, food(ModFoods.MANA_DRINK));

    //lens
    public static final RegistryObject<Item> MANA_LENS = regLensItem(LibItemNames.MANA_LENS, stackTo16(), ManaLens::new, LensItem.PROP_INTERACTION);
    public static final RegistryObject<Item> POTION_LENS = regLensItem(LibItemNames.POTION_LENS, stackTo16(), PotionLens::new, LensItem.PROP_INTERACTION, true);
    public static final RegistryObject<Item> PUSH_LENS = regLensItem(LibItemNames.PUSH_LENS, stackTo16(), PushLens::new, LensItem.PROP_INTERACTION);
    public static final RegistryObject<Item> SMELT_LENS = regLensItem(LibItemNames.SMELT_LENS, stackTo16(), SmeltLens::new, LensItem.PROP_TOUCH);
    public static final RegistryObject<Item> SUPER_CONDUCTOR_LENS = regLensItem(LibItemNames.SUPER_CONDUCTOR_LENS, stackTo16(), SuperConductorLens::new, LensItem.PROP_POWER);
    public static final RegistryObject<Item> TRACE_LENS = regLensItem(LibItemNames.TRACE_LENS, stackTo16(), TraceLens::new, LensItem.PROP_CONTROL);

    //tool
    public static final RegistryObject<Item> MANASTEEL_HAMMER = regDefItem(LibItemNames.MANASTEEL_HAMMER, ManasteelHammer::new, nonStackable());
    public static final RegistryObject<Item> ELEMENTIUM_HAMMER = regDefItem(LibItemNames.ELEMENTIUM_HAMMER, ElementiumHammer::new, nonStackable());
    public static final RegistryObject<Item> TERRASTEEL_HAMMER = regDefItem(LibItemNames.TERRASTEEL_HAMMER, TerrasteelHammer::new, nonStackable());
    public static final RegistryObject<Item> ULTIMATE_HAMMER = regDefItem(LibItemNames.ULTIMATE_HAMMER, UltimateHammer::new, nonStackable());
    public static final RegistryObject<Item> MANA_READER = regDefItem(LibItemNames.MANA_READER, ManaReader::new, nonStackable());
    public static final RegistryObject<Item> WALKING_CANE = regDefItem(LibItemNames.WALKING_CANE, WalkingCaneItem::new, nonStackable());
    public static final RegistryObject<Item> ROD_OF_DISCORD = regDefItem(LibItemNames.ROD_OF_DISCORD, RodOfDiscordItem::new, nonStackable());
    public static final RegistryObject<Item> SILVER_BULLET = regDefItem(LibItemNames.SILVER_BULLET, SilverBulletItem::new, nonStackable());

    //armor
    public static final RegistryObject<Item> MIKU_HELM = regArmorItem(LibItemNames.MIKU_HELM, MikuArmorItem::new, nonStackable(), ArmorItem.Type.HELMET);
    public static final RegistryObject<Item> MIKU_CHEST = regArmorItem(LibItemNames.MIKU_CHEST, MikuArmorItem::new, nonStackable(), ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<Item> MIKU_LEGS = regArmorItem(LibItemNames.MIKU_LEGS, MikuArmorItem::new, nonStackable(), ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<Item> MIKU_BOOTS = regArmorItem(LibItemNames.MIKU_BOOTS, MikuArmorItem::new, nonStackable(), ArmorItem.Type.BOOTS);
    public static final RegistryObject<Item> GOBLINS_LAYER_HELM = regArmorItem(LibItemNames.GOBLINS_LAYER_HELM, GoblinsLayerArmorItem::new, nonStackable(), ArmorItem.Type.HELMET);
    public static final RegistryObject<Item> GOBLINS_LAYER_CHEST = regArmorItem(LibItemNames.GOBLINS_LAYER_CHEST, GoblinsLayerArmorItem::new, nonStackable(), ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<Item> GOBLINS_LAYER_LEGS = regArmorItem(LibItemNames.GOBLINS_LAYER_LEGS, GoblinsLayerArmorItem::new, nonStackable(), ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<Item> GOBLINS_LAYER_BOOTS = regArmorItem(LibItemNames.GOBLINS_LAYER_BOOTS, GoblinsLayerArmorItem::new, nonStackable(), ArmorItem.Type.BOOTS);
    public static final RegistryObject<Item> SHADOW_WARRIOR_HELM = regArmorItem(LibItemNames.SHADOW_WARRIOR_HELM, ShadowWarriorArmorItem::new, nonStackable(), ArmorItem.Type.HELMET);
    public static final RegistryObject<Item> SHADOW_WARRIOR_CHEST = regArmorItem(LibItemNames.SHADOW_WARRIOR_CHEST, ShadowWarriorArmorItem::new, nonStackable(), ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<Item> SHADOW_WARRIOR_LEGS = regArmorItem(LibItemNames.SHADOW_WARRIOR_LEGS, ShadowWarriorArmorItem::new, nonStackable(), ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<Item> SHADOW_WARRIOR_BOOTS = regArmorItem(LibItemNames.SHADOW_WARRIOR_BOOTS, ShadowWarriorArmorItem::new, nonStackable(), ArmorItem.Type.BOOTS);
    public static final RegistryObject<Item> SHOOTING_GUARDIAN_HELM = regArmorItem(LibItemNames.SHOOTING_GUARDIAN_HELM, ShootingGuardianArmorHelmItem::new, nonStackable());
    public static final RegistryObject<Item> SHOOTING_GUARDIAN_CHEST = regArmorItem(LibItemNames.SHOOTING_GUARDIAN_CHEST, ShootingGuardianArmorItem::new, nonStackable(), ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<Item> SHOOTING_GUARDIAN_LEGS = regArmorItem(LibItemNames.SHOOTING_GUARDIAN_LEGS, ShootingGuardianArmorItem::new, nonStackable(), ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<Item> SHOOTING_GUARDIAN_BOOTS = regArmorItem(LibItemNames.SHOOTING_GUARDIAN_BOOTS, ShootingGuardianArmorItem::new, nonStackable(), ArmorItem.Type.BOOTS);
    public static final RegistryObject<Item> MAID_HELM = regArmorItem(LibItemNames.MAID_HELM, MaidArmorHelmetItem::new, nonStackable());
    public static final RegistryObject<Item> MAID_CHEST = regArmorItem(LibItemNames.MAID_CHEST, MaidArmorItem::new, nonStackable(), ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<Item> MAID_LEGS = regArmorItem(LibItemNames.MAID_LEGS, MaidArmorItem::new, nonStackable(), ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<Item> MAID_BOOTS = regArmorItem(LibItemNames.MAID_BOOTS, MaidArmorItem::new, nonStackable(), ArmorItem.Type.BOOTS);

    //weapon
    public static final RegistryObject<Item> SHADOW_KATANA = regDefItem(LibItemNames.SHADOW_KATANA, ShadowKatanaItem::new, nonStackable());
    public static final RegistryObject<Item> FLAMESCION_WEAPON = regDefItem(LibItemNames.FLAMESCION_WEAPON, FlamescionWeaponItem::new, relic());
    public static final RegistryObject<Item> INFLUX_WAVER = regDefItem(LibItemNames.INFLUX_WAVER, InfluxWaverItem::new, relic());
    public static final RegistryObject<Item> STAR_WRATH = regDefItem(LibItemNames.STAR_WRATH, StarWrathItem::new, relic());
    public static final RegistryObject<Item> TRUE_SHADOW_KATANA = regDefItem(LibItemNames.TRUE_SHADOW_KATANA, TrueShadowKatanaItem::new, relic());

    // cosmetic bauble
    public static final RegistryObject<Item> FOX_EAR = regComBaubleItem(LibItemNames.FOX_EAR, CosmeticBaubleItem.Variant.FOX_EAR, new Item.Properties());
    public static final RegistryObject<Item> FOX_MASK = regComBaubleItem(LibItemNames.FOX_MASK, CosmeticBaubleItem.Variant.FOX_MASK, new Item.Properties());
    public static final RegistryObject<Item> PYLON = regComBaubleItem(LibItemNames.PYLON, CosmeticBaubleItem.Variant.PYLON, new Item.Properties());
    public static final RegistryObject<Item> BLACK_GLASSES = regComBaubleItem(LibItemNames.BLACK_GLASSES, CosmeticBaubleItem.Variant.BLACK_GLASSES, new Item.Properties());
    public static final RegistryObject<Item> THUG_LIFE = regComBaubleItem(LibItemNames.THUG_LIFE, CosmeticBaubleItem.Variant.THUG_LIFE, new Item.Properties());
    public static final RegistryObject<Item> RED_SCARF = regComBaubleItem(LibItemNames.RED_SCARF, CosmeticBaubleItem.Variant.RED_SCARF, new Item.Properties());
    public static final RegistryObject<Item> MASK = regComBaubleItem(LibItemNames.MASK, CosmeticBaubleItem.Variant.MASK, new Item.Properties());
    public static final RegistryObject<Item> SUPER_CROWN = regComBaubleItem(LibItemNames.SUPER_CROWN, CosmeticBaubleItem.Variant.SUPER_CROWN, new Item.Properties());

    //misc
    public static final RegistryObject<Item> GILDED_POTATO = regDefItem(LibItemNames.GILDED_POTATO, defaultBuilder());
    public static final RegistryObject<Item> PHOTONIUM = regDefItem(LibItemNames.PHOTONIUM, defaultBuilder());
    public static final RegistryObject<Item> SHADOWIUM = regDefItem(LibItemNames.SHADOWIUM, defaultBuilder());
    public static final RegistryObject<Item> AERIALITE = regDefItem(LibItemNames.AERIALITE, defaultBuilder());
    public static final RegistryObject<Item> ORICHALCOS = regDefItem(LibItemNames.ORICHALCOS, defaultBuilder());
    public static final RegistryObject<Item> SPIRIT = regDefItem(LibItemNames.SPIRIT, defaultBuilder());
    public static final RegistryObject<Item> EMPTY_BOTTLE = regDefItem(LibItemNames.EMPTY_BOTTLE, EmptyBottleItem::new, defaultBuilder());
    public static final RegistryObject<Item> HERO_MEDAL = regDefItem(LibItemNames.HERO_MEDAL, defaultBuilder());
    public static final RegistryObject<Item> GOLD_CLOTH = regDefItem(LibItemNames.GOLD_CLOTH, GoldClothItem::new, defaultBuilder());

    public static final RegistryObject<Item> UNIVERSAL_PETAL = regDefItem(LibItemNames.UNIVERSAL_PETAL, defaultBuilder());
    public static final RegistryObject<Item> ELEMENT_RUNE = regDefItem(LibItemNames.ELEMENT_RUNE, defaultBuilder());
    public static final RegistryObject<Item> SIN_RUNE = regDefItem(LibItemNames.SIN_RUNE, defaultBuilder());

    public static final RegistryObject<Item> THE_CHAOS = regDefItem(LibItemNames.THE_CHAOS, defaultBuilder());
    public static final RegistryObject<Item> THE_ORIGIN = regDefItem(LibItemNames.THE_ORIGIN, defaultBuilder());
    public static final RegistryObject<Item> THE_END = regDefItem(LibItemNames.THE_END, defaultBuilder());
    public static final RegistryObject<Item> THE_UNIVERSE = regDefItem(LibItemNames.THE_UNIVERSE, defaultBuilder());

    //record
//    public static final RegistryObject<Item> RECORD_EGO = regRecordItem(LibItemNames.RECORD_EGO,
//            new BotaniaRecordItem(1,ExtraBotanySounds.SWORDLAND.get(),nonStackable().rarity(Rarity.RARE),47));
//    public static final RegistryObject<Item> RECORD_HERRSCHER = regRecordItem(LibItemNames.RECORD_HERRSCHER,
//            new BotaniaRecordItem(1,ExtraBotanySounds.SALVATION.get(),nonStackable().rarity(Rarity.RARE),197));

    //brew
    public static final RegistryObject<Item> COCKTAIL = regDefItem(LibItemNames.COCKTAIL, CocktailItem::new, stackTo32());
    public static final RegistryObject<Item> INFINITE_WINE = regDefItem(LibItemNames.INFINITE_WINE, InfiniteWineItem::new, nonStackable());
    public static final RegistryObject<Item> SPLASH_GRENADE = regDefItem(LibItemNames.SPLASH_GRENADE, SplashGrenadeItem::new, stackTo32());

    public static void regDefBlockItem(RegistryObject<Block> block) {
        RegistryObject<BlockItem> item = ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        MOD_BLOCK_ITEMS.add(item);
    }

    public static void regFlowerItem(RegistryObject<Block> block) {
        RegistryObject<BlockItem> item = ITEMS.register(block.getId().getPath(), () -> new SpecialFlowerBlockItem(block.get(), defaultBuilder()));
        MOD_BLOCK_ITEMS.add(item);
    }

    private static RegistryObject<Item> regLensItem(String name, Item.Properties props, Supplier<Lens> lens, int prop) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new BrewLensItem(props, lens.get(), prop, false));
        MOD_ITEMS.add(item);
        return item;
    }

    private static RegistryObject<Item> regLensItem(String name, Item.Properties props, Supplier<Lens> lens, int prop, boolean isBrew) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new BrewLensItem(props, lens.get(), prop, isBrew));
        MOD_ITEMS.add(item);
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

    private static RegistryObject<Item> regRecordItem(String name, BotaniaRecordItem item) {
        RegistryObject<Item> record = ITEMS.register(name, () -> item);
        MOD_ITEMS.add(record);
        return record;
    }

    private static <I extends Item> RegistryObject<Item> regComBaubleItem(String name, CosmeticBaubleItem.Variant variant, Item.Properties props) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new CosmeticBaubleItem(variant, props));
        MOD_ITEMS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<Item> regArmorItem(String name, BiFunction<Item.Properties, ArmorItem.Type, ? extends I> func, Item.Properties props, ArmorItem.Type type) {
        RegistryObject<Item> item = ITEMS.register(name, () -> func.apply(props, type));
        MOD_ITEMS.add(item);
        return item;
    }

    private static <I extends Item> RegistryObject<Item> regArmorItem(String name, Function<Item.Properties, ? extends I> func, Item.Properties props) {
        return regDefItem(name, func, props);
    }

    private static Item.Properties defaultBuilder() {
        return new Item.Properties();
    }

    private static Item.Properties food(FoodProperties props) {
        return defaultBuilder().food(props);
    }

    private static Item.Properties nonStackable() {
        return defaultBuilder().stacksTo(1);
    }

    private static Item.Properties stackTo16() {
        return defaultBuilder().stacksTo(16);
    }

    private static Item.Properties stackTo32() {
        return defaultBuilder().stacksTo(32);
    }

    private static Item.Properties relic() {
        return nonStackable().rarity(Rarity.EPIC).setNoRepair();
    }

    ;

    public static DeferredRegister<Item> getItems() {
        return ITEMS;
    }
}
