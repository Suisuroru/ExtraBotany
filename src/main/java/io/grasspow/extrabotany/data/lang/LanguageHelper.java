package io.grasspow.extrabotany.data.lang;

import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import io.grasspow.extrabotany.common.libs.LibBlockNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LanguageHelper {

    public static final Map<String, String> en_us = new HashMap<>();
    public static final Map<String, String> zh_cn = new HashMap<>();

    public static void init() {
        add("itemGroup.extrabotany", "ExtraBotany", "额外植物学");
        transItem();
        transBlock();
        transEntity();
        transAdvancement();

        //brew
        add("extrabotany.brew.revolution", "Revolution", "革命");
        add("extrabotany.brew.all_mighty", "All-mighty", "全能");
        add("extrabotany.brew.shell", "Shell", "龟壳");
        add("extrabotany.brew.deadpool", "Deadpool", "死灵");
        add("extrabotany.brew.floating", "Floating", "漂浮");

        //sound
    }

    private static void transItem() {
        // cosmetic bauble
        addItem(ExtraBotanyItems.PYLON, "Pylon", "模拟人生");
        //bauble
        addItem(ExtraBotanyItems.AERO_STONE, "Aero Stone", "风之符石");
        addItem(ExtraBotanyItems.AQUA_STONE, "Aqua stone", "水之符石");
        addItem(ExtraBotanyItems.EARTH_STONE, "Earth Stone", "地之符石");
        addItem(ExtraBotanyItems.IGNIS_STONE, "Ignis Stone", "火之符石");
        addItem(ExtraBotanyItems.THE_COMMUNITY, "The Community", "四元归一石");
        addItem(ExtraBotanyItems.PEACE_AMULET, "Peace Amulet", "和平友好之证");
        addItem(ExtraBotanyItems.POWER_GLOVE, "Power Glove", "强力手套");
        addItem(ExtraBotanyItems.FROST_STAR, "Frost Star", "霜冻之星");
        addItem(ExtraBotanyItems.DEATH_RING, "Death Ring", "诅咒指环");
        addItem(ExtraBotanyItems.MANA_DRIVE_RING, "Ring of Mana Drive", "驭魔之戒");
        // food
        addItem(ExtraBotanyItems.SPIRIT_FUEL, "Spirit Fuel", "精神燃料");
        addItem(ExtraBotanyItems.NIGHTMARE_FUEL, "Nightmare Fuel", "梦魇染料");
        addItem(ExtraBotanyItems.GILDED_MASHED_POTATO, "Gilded Mashed Potato", "镀金土豆泥");
        addItem(ExtraBotanyItems.FRIED_CHICKEN, "Fried Chicken", "香香鸡");
        addItem(ExtraBotanyItems.MANA_DRINK, "Mana Drink", "魔力鸡尾酒");
        //tool
        addItem(ExtraBotanyItems.MANASTEEL_HAMMER, "Manasteel Hammer", "魔力钢锤");
        addItem(ExtraBotanyItems.ELEMENTIUM_HAMMER, "Elementium Hammer", "源质钢锤");
        addItem(ExtraBotanyItems.TERRASTEEL_HAMMER, "Terrasteel Hammer", "泰拉钢锤");
        addItem(ExtraBotanyItems.ULTIMATE_HAMMER, "Ultimate Hammer", "镀金究极锤");
        addItem(ExtraBotanyItems.MANA_READER, "Mana Reader", "魔力读取器");
        //brew
        addItem(ExtraBotanyItems.COCKTAIL, "Special-made CocktailItem of %s(%s)", "装有%s(%s)的秘制鸡尾酒");
        addItem(ExtraBotanyItems.INFINITE_WINE, "Infinite Wine of %s(%s)", "装有%s(%s)的无限之酒");
        addItem(ExtraBotanyItems.SPLASH_GRENADE, "Holy Grenade of %s", "装有%s的圣水手雷");
        //lens
        addItemLens(ExtraBotanyItems.MANA_LENS, "Mana", "魔力");
        addItemLens(ExtraBotanyItems.POTION_LENS, "Potion", "药水");
        addItemLens(ExtraBotanyItems.PUSH_LENS, "Push", "冲击");
        addItemLens(ExtraBotanyItems.SMELT_LENS, "Smelt", "冶炼");
        addItemLens(ExtraBotanyItems.SUPER_CONDUCTOR_LENS, "Super Conductor", "超导");
        addItemLens(ExtraBotanyItems.TRACE_LENS, "Mana", "追踪");
        //misc
        addItem(ExtraBotanyItems.GILDED_POTATO, "Gilded Potato", "镀金服务器");
        addItem(ExtraBotanyItems.PHOTONIUM, "Phontonium Ingot", "光子锭");
        addItem(ExtraBotanyItems.SHADOWIUM, "Shadowium Ingot", "暗影锭");
        addItem(ExtraBotanyItems.AERIALITE, "Aerialite Ingot", "天空锭");
        addItem(ExtraBotanyItems.ORICHALCOS, "Orichalcos Ingot", "奥利哈刚锭");
        addItem(ExtraBotanyItems.SPIRIT, "Spirit Fragment", "精神碎片");
        addItem(ExtraBotanyItems.EMPTY_BOTTLE, "Empty Mana Glass Bottle", "魔法玻璃空瓶");
        addItem(ExtraBotanyItems.HERO_MEDAL, "Medal of Heroism", "英雄勋章");
        addItem(ExtraBotanyItems.GOLD_CLOTH, "Das Rheingold", "莱茵河的黄金");

        addItem(ExtraBotanyItems.UNIVERSAL_PETAL, "Universal Petal", "彩虹花瓣");
        addItem(ExtraBotanyItems.ELEMENT_RUNE, "Rune of Element", "元灵符文");
        addItem(ExtraBotanyItems.SIN_RUNE, "Rune of Sin", "大罪符文");

        addItem(ExtraBotanyItems.THE_CHAOS, "The Chaos", "混沌物质");
        addItem(ExtraBotanyItems.THE_ORIGIN, "The Origin", "起源物质");
        addItem(ExtraBotanyItems.THE_END, "The End", "终末物质");
        addItem(ExtraBotanyItems.THE_UNIVERSE, "The Universe", "宇宙之心");
    }

    private static void transBlock() {
        addBlock(ExtraBotanyBlocks.PHOTONIUM_BLOCK, "Photonium Block", "光子块");
        addBlock(ExtraBotanyBlocks.SHADOWIUM_BLOCK, "Shadowium Block", "暗影块");
        addBlock(ExtraBotanyBlocks.AERIALITE_BLOCK, "Aerialite Block", "天空块");
        addBlock(ExtraBotanyBlocks.ORICHALCOS_BLOCK, "Orichalcos Block", "奥利哈刚块");
        addBlock(ExtraBotanyBlocks.PEDESTAL, "Livingrock Pedestal", "活石祭坛");
        addBlock(ExtraBotanyBlocks.MANA_BUFFER, "Mana Buffer", "魔力缓存器");
        addBlock(ExtraBotanyBlocks.QUANTUM_MANA_BUFFER, "Quantum Mana Buffer", "量子魔力缓存器");
        addBlock(ExtraBotanyBlocks.TROPHY, "Trophy", "奖杯");
        addBlock(ExtraBotanyBlocks.LIVINGROCK_BARREL, "Livingrock Barrel", "活石桶");
        addBlock(ExtraBotanyBlocks.DIMENSION_CATALYST, "Dimension Catalyst", "次元催化器");
        addBlock(ExtraBotanyBlocks.POWER_FRAME, "Power Frame", "力量框架");

        //flower 1.16
        addFlower(ExtraBotanyBlocks.ANNOYING_FLOWER, "Annoying Flower", "神烦花", "Time to rest", "摸了");
        addFlower(ExtraBotanyBlocks.SERENITIAN, "Serenitian", "永寂龙胆", "Torn to oblivion", "无念，断绝");
        addFlower(ExtraBotanyBlocks.BELL_FLOWER, "Bell Flower", "风铃草", "Lost wind", "迷失的风");
        addFlower(LibBlockNames.EDELWEISS, "Edelweiss", "雪绒花", "Do you want to build a snowman?", "你想堆个雪人吗？");
        addFlower(LibBlockNames.GEMINI_ORCHID, "Gemini Orchid", "双子兰", "Why is a raven like a writing desk?", "为什么乌鸦像写字台？");
        addFlower(LibBlockNames.SUN_BLESS, "Sunshine Lily", "日曜百合", "May the light heal and enlighten you", "愿光芒能治愈并指引你");
        addFlower(LibBlockNames.MOON_BLESS, "Moonlight Lily", "月光百合", "May you find all you have lost", "愿你能找到所有失去的东西");
        addFlower(LibBlockNames.OMNI_VIOLET, "Omniviolet", "全知瑾", "Need not to know", "我知万物");
        addFlower(LibBlockNames.REIKAR_LILY, "Reikar Lily", "雷卡兰", "Game Crash", "游戏崩溃");
        addFlower(LibBlockNames.TINKLE_FLOWER, "Tinkle Flower", "叮当舞花", "My turn", "接下来是我的回合了");
//        addFlower(LibBlockNames.BLOODY_ENCHANTRESS, "Bloody Enchantress", "血腥妖姬", "My turn", "接下来是我的回合了");
    }

    private static void transEntity() {
    }

    private static void transAdvancement() {
        add("advancement.extrabotany.root.title", "Welcome to the World", "欢迎来到世界");
        add("advancement.extrabotany.root.desc", "Don't have a good day, have a great day", "不要错过今天，去过好每一天");
        makeAdv(LibAdvancementNames.NIGHTMARE_FUEL_EAT,
                "Deep Dark Fantasy", "Deep Dark Fantasy",
                "Eat a Nightmare Fuel (Unbelievable)", "食用一个梦魇燃料(这真的能吃吗)"
        );
    }

    private static void makeAdv(String key, String titleEn, String titleZh, String descEn, String descZh) {
        add("advancement.extrabotany." + key + ".title", titleEn, titleZh);
        add("advancement.extrabotany." + key + ".desc", descEn, descZh);
    }

    /**
     * @param key the whole key,example: item.minecraft.apple
     */
    private static void add(String key, String en, String cn) {
        en_us.put(key, en);
        zh_cn.put(key, cn);
    }

    private static void addItem(@NotNull RegistryObject<? extends Item> item, String en, String zh) {
        en_us.put(item.get().getDescriptionId(), en);
        zh_cn.put(item.get().getDescriptionId(), zh);
    }

    private static void addItemLens(@NotNull RegistryObject<? extends Item> item, String en, String zh) {
        addItem(item, "Mana Lens: " + en, "魔力透镜:" + zh);
        add(item.get().getDescriptionId() + ".short", en, zh);
    }

    private static void addBlock(@NotNull RegistryObject<? extends Block> block, String en, String zh) {
        en_us.put(block.get().getDescriptionId(), en);
        zh_cn.put(block.get().getDescriptionId(), zh);
    }

    private static void addFlower(RegistryObject<Block> flower, String en, String zh, String refEn, String refZh) {
        ResourceLocation id = flower.getId();
        en_us.put(flower.get().getDescriptionId(), en);
        en_us.put("block." + id.getNamespace() + ".floating_" + id.getPath(), "Floating " + en);
        en_us.put(flower.get().getDescriptionId() + ".reference", refEn);
        en_us.put("block." + id.getNamespace() + ".floating_" + id.getPath() + ".reference", refEn);
        zh_cn.put(flower.get().getDescriptionId(), zh);
        zh_cn.put("block." + id.getNamespace() + ".floating_" + id.getPath(), "浮空" + zh);
        zh_cn.put(flower.get().getDescriptionId() + ".reference", refZh);
        zh_cn.put("block." + id.getNamespace() + ".floating_" + id.getPath() + ".reference", refZh);
    }

    private static void addFlower(String name, String en, String zh, String refEn, String refZh) {
        en_us.put("block.extrabotany." + name, en);
        en_us.put("block.extrabotany.floating_" + name, "Floating " + en);
        en_us.put("block.extrabotany." + name + ".reference", refEn);
        en_us.put("block.extrabotany.floating_" + name + ".reference", refEn);
        zh_cn.put("block.extrabotany." + name, zh);
        zh_cn.put("block.extrabotany.floating_" + name, "浮空" + zh);
        zh_cn.put("block.extrabotany." + name + ".reference", refZh);
        zh_cn.put("block.extrabotany.floating_" + name + ".reference", refZh);
    }
}
