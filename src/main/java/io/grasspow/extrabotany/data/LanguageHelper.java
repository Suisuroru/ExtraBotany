package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import io.grasspow.extrabotany.common.registry.ModBlocks;
import io.grasspow.extrabotany.common.registry.ModItems;
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
    }

    private static void transItem() {
        //block item
        addItem(ModItems.PHOTONIUM_BLOCK_ITEM, "Photonium Block", "光子块");
        addItem(ModItems.SHADOWIUM_BLOCK_ITEM, "Shadowium Block", "暗影块");
        addItem(ModItems.PEDESTAL_ITEM, "Livingrock Pedestal", "活石祭坛");
        // cosmetic bauble
        addItem(ModItems.PYLON, "Pylon", "模拟人生");
        // food
        addItem(ModItems.SPIRIT_FUEL, "Spirit Fuel", "精神燃料");
        addItem(ModItems.NIGHTMARE_FUEL, "Nightmare Fuel", "梦魇染料");
        addItem(ModItems.GILDED_MASHED_POTATO, "Gilded Mashed Potato", "魔力鸡尾酒");
        addItem(ModItems.FRIED_CHICKEN, "Fried Chicken", "香香鸡");
        addItem(ModItems.MANA_DRINK, "Mana Drink", "镀金土豆泥");
        //tool
        addItem(ModItems.MANASTEEL_HAMMER, "Manasteel Hammer", "魔力钢锤");
        addItem(ModItems.ELEMENTIUM_HAMMER, "Elementium Hammer", "源质钢锤");
        addItem(ModItems.TERRASTEEL_HAMMER, "Terrasteel Hammer", "泰拉钢锤");
        addItem(ModItems.ULTIMATE_HAMMER, "Ultimate Hammer", "镀金究极锤");
        //misc
        addItem(ModItems.GILDED_POTATO, "Gilded Potato", "镀金服务器");
        addItem(ModItems.PHOTONIUM, "Phontonium", "光子锭");
        addItem(ModItems.SHADOWIUM, "Gilded Potato", "暗影锭");
        addItem(ModItems.SPIRIT, "Spirit Fragment", "精神碎片");
    }

    private static void transBlock() {
        addBlock(ModBlocks.PHOTONIUM_BLOCK, "Photonium Block", "光子块");
        addBlock(ModBlocks.SHADOWIUM_BLOCK, "Shadowium Block", "暗影块");
        addBlock(ModBlocks.PEDESTAL, "Livingrock Pedestal", "活石祭坛");
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
