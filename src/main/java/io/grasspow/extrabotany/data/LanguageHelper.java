package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
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
        add("itemGroup.extrabotany","Extra Botany","额外植物学");
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
    }

    private static void transItem() {
        //block item
//        addItem(ExtraBotanyItems.PHOTONIUM_BLOCK_ITEM, "Photonium Block", "光子块");
//        addItem(ExtraBotanyItems.SHADOWIUM_BLOCK_ITEM, "Shadowium Block", "暗影块");
//        addItem(ExtraBotanyItems.PEDESTAL_ITEM, "Livingrock Pedestal", "活石祭坛");
        // cosmetic bauble
        addItem(ExtraBotanyItems.PYLON, "Pylon", "模拟人生");
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
        //misc
        addItem(ExtraBotanyItems.GILDED_POTATO, "Gilded Potato", "镀金服务器");
        addItem(ExtraBotanyItems.PHOTONIUM, "Phontonium Ingot", "光子锭");
        addItem(ExtraBotanyItems.SHADOWIUM, "Shadowium Ingot", "暗影锭");
        addItem(ExtraBotanyItems.ORICHALCOS, "Orichalcos Ingot", "奥利哈刚锭");
        addItem(ExtraBotanyItems.SPIRIT, "Spirit Fragment", "精神碎片");
        addItem(ExtraBotanyItems.EMPTY_BOTTLE, "Empty Mana Glass Bottle", "魔法玻璃空瓶");
        addItem(ExtraBotanyItems.HERO_MEDAL, "Medal of Heroism", "英雄勋章");
        addItem(ExtraBotanyItems.GOLD_CLOTH, "Das Rheingold", "莱茵河的黄金");
    }

    private static void transBlock() {
        addBlock(ExtraBotanyBlocks.PHOTONIUM_BLOCK, "Photonium Block", "光子块");
        addBlock(ExtraBotanyBlocks.SHADOWIUM_BLOCK, "Shadowium Block", "暗影块");
        addBlock(ExtraBotanyBlocks.ORICHALCOS_BLOCK, "Orichalcos Block", "奥利哈刚块");
        addBlock(ExtraBotanyBlocks.PEDESTAL, "Livingrock Pedestal", "活石祭坛");
        addBlock(ExtraBotanyBlocks.MANA_BUFFER, "Mana Buffer", "魔力缓存器");
        addBlock(ExtraBotanyBlocks.QUANTUM_MANA_BUFFER, "Quantum Mana Buffer", "量子魔力缓存器");
        addBlock(ExtraBotanyBlocks.TROPHY, "Trophy", "奖杯");
        addBlock(ExtraBotanyBlocks.LIVINGROCK_BARREL, "Livingrock Barrel", "活石桶");
        addBlock(ExtraBotanyBlocks.DIMENSION_CATALYST, "Dimension Catalyst", "次元催化器");
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

    private static void addBlock(@NotNull RegistryObject<? extends Block> block, String en, String zh) {
        en_us.put(block.get().getDescriptionId(), en);
        zh_cn.put(block.get().getDescriptionId(), zh);
    }
}
