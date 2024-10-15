package io.grasspow.extrabotany.data.lang;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import io.grasspow.extrabotany.common.libs.LibBlockNames;
import io.grasspow.extrabotany.common.libs.LibEntityNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
        add("advancement.item_disabled.desc", "You can' use it until you complete corresponding advancement <%s>.", "你无法使用该物品直到你完成进度 <%s>");
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
        addItem(ExtraBotanyItems.FOX_EAR, "Fox Ear", "樱樱耳");
        addItem(ExtraBotanyItems.FOX_MASK, "Mask with Memory", "附有记忆的面具");
        addItem(ExtraBotanyItems.PYLON, "Sims", "模拟人生");
        addItem(ExtraBotanyItems.BLACK_GLASSES, "Black Glasses", "黑框眼镜");
        addItem(ExtraBotanyItems.THUG_LIFE, "THUG LIFE", "THUG LIFE");
        addItem(ExtraBotanyItems.RED_SCARF, "Red Scarf", "红领巾");
        addItem(ExtraBotanyItems.MASK, "Mask", "石鬼面");
        addItem(ExtraBotanyItems.SUPER_CROWN, "Super Crown", "超级王冠");

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
        addItem(ExtraBotanyItems.NATURE_ORB, "Nature Orb", "自然蕴息宝珠");
        add("extrabotany.nature_orb", "Natural Breath: %s / %s", "自然之息: %s / %s");
        add("extrabotany.nature_orb_effect1", "Blessing of Mana", "魔力的加护");
        add("extrabotany.nature_orb_effect2", "Blessing of Regeneration", "再生的加护");
        add("extrabotany.nature_orb_effect3", "Blessing of Senro", "森罗的加护");
        addItem(ExtraBotanyItems.JINGWEI_FEATHER, "Feather of Jingwei", "精卫之羽");
        addItem(ExtraBotanyItems.POTATO_CHIPS, "Potato Chips", "薯片");
        addItem(ExtraBotanyItems.SUN_RING, "Ring of Sacred Sun", "圣阳尊戒");
        addItem(ExtraBotanyItems.MOON_PENDANT, "Heart of Corrupted Moon", "蚀月之心");
        addItem(ExtraBotanyItems.SAGES_MANA_RING, "Sage's Ring of Mana", "贤者魔力指环");
        addItem(ExtraBotanyItems.CORE_GOD, "Core of God", "律者核心");
        add("extrabotany.wings0", "Wings: Herrscher", "翅膀：律者");
        add("extrabotany.wings1", "Wings: Flandre", "翅膀：芙兰朵露");
        add("extrabotany.wings2", "Wings: Jim", "翅膀：吉米");
        add("extrabotany.wings3", "Wings: Steampunk", "翅膀：蒸汽朋克");

        // food
        addItem(ExtraBotanyItems.SPIRIT_FUEL, "Spirit Fuel", "精神燃料");
        addItem(ExtraBotanyItems.NIGHTMARE_FUEL, "Nightmare Fuel", "梦魇燃料");
        addItem(ExtraBotanyItems.GILDED_MASHED_POTATO, "Gilded Mashed Potato", "镀金土豆泥");
        addItem(ExtraBotanyItems.FRIED_CHICKEN, "Fried Chicken", "香香鸡");
        addItem(ExtraBotanyItems.MANA_DRINK, "Mana Drink", "魔力鸡尾酒");

        //tool
        addItem(ExtraBotanyItems.MANASTEEL_HAMMER, "Manasteel Hammer", "魔力钢锤");
        addItem(ExtraBotanyItems.ELEMENTIUM_HAMMER, "Elementium Hammer", "源质钢锤");
        addItem(ExtraBotanyItems.TERRASTEEL_HAMMER, "Terrasteel Hammer", "泰拉钢锤");
        addItem(ExtraBotanyItems.ULTIMATE_HAMMER, "Ultimate Hammer", "镀金究极锤");
        addItem(ExtraBotanyItems.MANA_READER, "Mana Reader", "魔力读取器");
        addItem(ExtraBotanyItems.WALKING_CANE, "Walking Cane", "步行者手杖");
        addItem(ExtraBotanyItems.ROD_OF_DISCORD, "Rod of Discord", "不谐传送杖");
        addItem(ExtraBotanyItems.SILVER_BULLET, "Silver Bullet", "银翼射手");
        addItem(ExtraBotanyItems.CAMERA, "Shameimaru's Camera", "文文的相机");

        //weapon
        addItem(ExtraBotanyItems.SHADOW_KATANA, "Shadow Catana", "影刃");
        addItem(ExtraBotanyItems.FLAMESCION_WEAPON, "Key of Flamescion", "焢煌之境:劫炎永燎");
        addItem(ExtraBotanyItems.INFLUX_WAVER, "Influx Waver", "波涌之刃");
        addItem(ExtraBotanyItems.STAR_WRATH, "Star Wrath", "狂星之怒");
        addItem(ExtraBotanyItems.TRUE_SHADOW_KATANA, "True Shadow Catana", "真影刃");
        addItem(ExtraBotanyItems.TRUE_TERRA_BLADE, "True Terra Blade", "真泰拉之刃");
        addItem(ExtraBotanyItems.EXCALIBER, "Excaliber", "王者圣剑");
        addItem(ExtraBotanyItems.FIRST_FRACTAL, "First Fractal", "最初分型");
        addItem(ExtraBotanyItems.FAILNAUGHT, "Failnaught", "百中弓");

        //armor
        add("extrabotany.armorset.miku.name", "Starry Idol", "星空歌姬");
        addItem(ExtraBotanyItems.MIKU_HELM, "Starry Idol Headgear", "星空歌姬头饰");
        addItem(ExtraBotanyItems.MIKU_CHEST, "Starry Idol Suit", "星空歌姬服");
        addItem(ExtraBotanyItems.MIKU_LEGS, "Starry Idol Skirt", "星空歌姬裙甲");
        addItem(ExtraBotanyItems.MIKU_BOOTS, "Starry Idol Boots", "星空歌姬鞋子");
        add("extrabotany.armorset.mana.desc", "Super Mana Affinity.", "超强魔力亲和");

        add("extrabotany.armorset.goblins_layer.name", "Goblin Slayer", "哥布林杀手");
        addItem(ExtraBotanyItems.GOBLINS_LAYER_HELM, "Goblin Slayer Helmet", "哥布林杀手头盔");
        addItem(ExtraBotanyItems.GOBLINS_LAYER_CHEST, "Goblin Slayer Chestplate", "哥布林杀手胸甲");
        addItem(ExtraBotanyItems.GOBLINS_LAYER_LEGS, "Goblin Slayer Leggings", "哥布林杀手护腿");
        addItem(ExtraBotanyItems.GOBLINS_LAYER_BOOTS, "Goblin Slayer Boots", "哥布林杀手靴子");
        add("extrabotany.armorset.goblins_layer.desc0", "Praise the Sun.", "我不拯救世界，只管杀哥布林。");
        add("extrabotany.armorset.goblins_layer.desc1", "May the sun enlighten you.", "想象力也是武器，没有想象力的人会先死。");

        add("extrabotany.armorset.shadow_warrior.name", "Shadow Warrior", "暗影武士");
        addItem(ExtraBotanyItems.SHADOW_WARRIOR_HELM, "Shadow Warrior Helmet", "暗影武士头盔");
        addItem(ExtraBotanyItems.SHADOW_WARRIOR_CHEST, "Shadow Warrior Chestplate", "暗影武士胸甲");
        addItem(ExtraBotanyItems.SHADOW_WARRIOR_LEGS, "Shadow Warrior Leggings", "暗影武士护腿");
        addItem(ExtraBotanyItems.SHADOW_WARRIOR_BOOTS, "Shadow Warrior Boots", "暗影武士靴子");
        add("extrabotany.armorset.shadow_warrior.desc0", "This night is so frightful and boundless. That my eyes come down with gloomy darkness.", "黑夜给了我黑色的眼睛，");
        add("extrabotany.armorset.shadow_warrior.desc1", "But just by them both. I am seeking my rosiness.", "我却用它寻找光明。");

        add("extrabotany.armorset.shooting_guardian.name", "Shooting Guardian", "银翼护卫");
        addItem(ExtraBotanyItems.SHOOTING_GUARDIAN_HELM, "Shooting Guardian Helmet", "银翼护卫头盔");
        addItem(ExtraBotanyItems.SHOOTING_GUARDIAN_CHEST, "Shooting Guardian Chestplate", "银翼护卫胸甲");
        addItem(ExtraBotanyItems.SHOOTING_GUARDIAN_LEGS, "Shooting Guardian Leggings", "银翼护卫护腿");
        addItem(ExtraBotanyItems.SHOOTING_GUARDIAN_BOOTS, "Shooting Guardian Boots", "银翼护卫靴子");
        add("extrabotany.armorset.shooting_guardian.desc0", "Faster Bow Drawing.", "快速拉弓");
        add("extrabotany.armorset.shooting_guardian.desc1", "Grant Armor-Piercing and Life Stealing.", "攻击附带穿甲和吸血");
        add("extrabotany.armorset.shooting_guardian.desc2", "Faster Speed.", "移动速度增加");
        add("extrabotany.armorset.shooting_guardian.desc3", "Greatly decrease natural life regeneration.", "生命恢复大幅减弱");

        add("extrabotany.armorset.maid.name", "Pleiades Combat Maid", "昴星团战斗女仆");
        addItem(ExtraBotanyItems.MAID_HELM, "Pleiades Combat Maid Headgear", "昴星团战斗女仆头饰");
        addItem(ExtraBotanyItems.MAID_CHEST, "Pleiades Combat Maid Suit", "昴星团战斗女仆服");
        addItem(ExtraBotanyItems.MAID_LEGS, "Pleiades Combat Maid Skirt", "昴星团战斗女仆裙甲");
        addItem(ExtraBotanyItems.MAID_BOOTS, "Pleiades Combat Maid Boots", "昴星团战斗女仆鞋子");
        add("extrabotany.armorset.maid.desc0", "Super Empty-handed Power.", "空手怪力");
        add("extrabotany.armorset.maid.desc1", "Greater Regeneration.", "再生增强");
        add("extrabotany.armorset.maid.desc2", "Mana Affinity.", "魔力亲和");

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

        addItem(ExtraBotanyItems.CHALLENGE_TICKET, "Challenge Ticket", "寄给自己的邀请函");
        addItem(ExtraBotanyItems.TREASURE_BOX, "Pandora's Box", "潘多拉魔盒");
        addItem(ExtraBotanyItems.REWARD_BAG_A, "Reward Bag Eins", "奖励袋Eins");
        addItem(ExtraBotanyItems.REWARD_BAG_B, "Reward Bag Zwei", "奖励袋Zwei");
        addItem(ExtraBotanyItems.REWARD_BAG_C, "Reward Bag Drei", "奖励袋Drei");
        addItem(ExtraBotanyItems.REWARD_BAG_D, "Reward Bag Vier", "奖励袋Vier");
        addItem(ExtraBotanyItems.REWARD_BAG_943, "Reward Bag 9-3/4", "九又四分之三奖励袋");
        addItem(ExtraBotanyItems.BUDDHIST_RELICS, "Origin Creation - Omniscience", "源初造物丨虚空万藏");
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
        addEntity(ExtraBotanyEntities.SPLASH_GRENADE.get(), "Splash Grenade", "圣水手雷");
        addEntity(ExtraBotanyEntities.MAGIC_ARROW_PROJECTILE.get(), "Magic Arrow Projectile", "百中弓射弹");
        addEntity(ExtraBotanyEntities.TRUE_TERRA_BLADE_PROJECTILE.get(), "True TerraBlade Projectile", "真泰拉之刃射弹");
        addEntity(ExtraBotanyEntities.TRUE_SHADOW_KATANA_PROJECTILE.get(), "True ShadowKatana Projectile", "真影刃射弹");
        addEntity(ExtraBotanyEntities.INFLUX_WAVER_PROJECTILE.get(), "Influx Waver Projectile", "波涌之刃射弹");
        addEntity(ExtraBotanyEntities.PHANTOM_SWORD_PROJECTILE.get(), "First Fractal Projectile", "最初分型射弹");
        addEntity(LibEntityNames.EGO, "EGO", "本我");
        addEntity(LibEntityNames.EGO_MINION, "EGO's Minion", "本我的仆从");
        addEntity(LibEntityNames.EGO_LANDMINE, "EGO's Landmine", "本我的地雷");
        add("extrabotanymisc.inventoryUnfeasible",
                "You have something that isn't allowed in this boss fight. Check your inventory and tips in the lexicon again!",
                "你的背包里包含违禁物品，请再次检查你的背包！"
        );
        add("extrabotanymisc.unlegalPlayercount",
                "There are more people than there were when the boss is summoned. That's illegal!",
                "在场玩家数大于召唤时的玩家数，这是不符合规则的！"
        );
        add("extrabotanymisc.description",
                "You can not use it until you complete corresponding advancement <%s>.",
                "你无法使用该物品直到你完成进度 <%s>。"
        );
    }


    private static void transAdvancement() {
        add("advancement.extrabotany.root.title", "Welcome to the World", "欢迎来到世界");
        add("advancement.extrabotany.root.desc", "Don't have a good day, have a great day", "不要错过今天，去过好每一天");
        makeAdv(LibAdvancementNames.NIGHTMARE_FUEL_EAT,
                "Deep Dark Fantasy", "Deep Dark Fantasy",
                "Eat a Nightmare Fuel (Unbelievable)", "食用一个梦魇燃料(这真的能吃吗)"
        );
        makeAdv(LibAdvancementNames.POWER_FRAME_CRAFT, "Letter Song", "Letter Song", "Craft a Position Reader", "合成一个力量框架");
        makeAdv(LibAdvancementNames.ARMORSET_MIKU, "Cat's Dance", "Cat's Dance", "Equip Starry Idol Armor Set", "装备一套星空歌姬");
        makeAdv(LibAdvancementNames.TINKLE_USE, "Project Diva Desu", "Project Diva Desu", "Dance around a Tinkle Flower", "在叮当舞花旁跳舞");
        makeAdv(LibAdvancementNames.MANA_BUFFER_CRAFT, "ロストワンの号哭", "ロストワンの号哭", "Craft a Mana Buffer", "合成一个魔力缓存器");
        makeAdv(LibAdvancementNames.MANA_READER_CRAFT, "Satisfaction", "Satisfaction", "Craft a Mana Reader", "合成一个魔力读取器");
        makeAdv(LibAdvancementNames.SPIRIT_CRAFT, "PONPONPON", "PONPONPON", "Craft a Spirit Fragment", "获得精神碎片");
        makeAdv(LibAdvancementNames.ARMORSET_GOBLINS_LAYER, "Befall", "Befall", "Equip Goblin Slayer Armor Set", "装备一套哥布林杀手");
        makeAdv(LibAdvancementNames.ARMORSET_SHADOW_WARRIOR, "Crazy ∞ nighT", "Crazy ∞ nighT", "Equip Shadow Warrior Armor Set", "装备一套暗影武士");
        makeAdv(LibAdvancementNames.ARMORSET_SHOOTING_GUARDIAN, "CONNECT", "CONNECT", "Equip Shooting Guardian Armor Set", "装备一套银翼护卫");
        makeAdv(LibAdvancementNames.ARMORSET_MAID, "Drug Of Gold", "Drug Of Gold", "Equip Pleiades Combat Maid Armor Set", "装备一套昂星团战斗女仆");
        makeAdv(LibAdvancementNames.NATURE_ORB_CRAFT, "Single floor", "雨夢楼", "Craft a Nature Orb", "合成一个自然蕴息宝珠");
        makeAdv(LibAdvancementNames.EGO_DEFEAT, "KiLLER LADY", "KiLLER LADY", "Defeat Ego", "击败本我");
        makeAdv(LibAdvancementNames.SUN_RING_CRAFT, "Promise", "Promise", "Obtain Ring of Sacred Sun", "获得圣阳尊戒");
        makeAdv(LibAdvancementNames.MOON_PENDANT_CRAFT, "Crystalline", "Crystalline", "Obtain Heart of Corrupted Moon", "获得蚀月之心");
        makeAdv(LibAdvancementNames.FAILNAUGHT_CRAFT, "From Y to Y", "From Y to Y", "Obtain Failnaught", "获得百中弓");
        makeAdv(LibAdvancementNames.CAMERA_CRAFT, "lukaluka night fever", "lukaluka night fever", "Obtain Shameimaru's Camera", "获得文文的相机");
        makeAdv(LibAdvancementNames.THE_UNIVERSE_CRAFT, "Gears of Love", "Gears of Love", "Obtain The Universe", "获得宇宙之心");
        makeAdv(LibAdvancementNames.FIRST_FRACTAL_CRAFT, "Infinity +1 Sword", "Infinity +1 Sword", "Obtain First Fractal", "获得最初分型");
        makeAdv(LibAdvancementNames.CORE_GOD_CRAFT, "All Hail The Queen", "All Hail The Queen", "Obtain Core of Herrscher", "获得律者核心");
        makeAdv(LibAdvancementNames.INFINITE_WINE_CRAFT, "Shake it", "Shake it", "Obtain Infinite Wine", "获得无限之酒");
        makeAdv(LibAdvancementNames.BUDDHIST_RELICS_OBTAIN, "SPiCa", "SPiCa", "Obtain Origin Creation|Omniscience", "获得源初造物丨虚空万藏");
        makeAdv(LibAdvancementNames.EXCALIBER_CRAFT, "ReAct", "ReAct", "Obtain Excaliber", "获得王者圣剑");
        makeAdv(LibAdvancementNames.SAGES_MANA_RING_CRAFT, "COLOR", "COLOR", "Obtain Sages Mana Ring", "获得贤者魔力指环");
        makeAdv(LibAdvancementNames.SAGES_MANA_RING_FILL, "Fairytale", "Fairytale", "Fill it to WIN the game", "将一个贤者魔力指环充满以获得游戏胜利");
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

    private static void addEntity(EntityType<? extends Entity> type, String en, String zh) {
        en_us.put(type.getDescriptionId(), en);
        zh_cn.put(type.getDescriptionId(), zh);
    }

    private static void addEntity(String name, String en, String zh) {
        en_us.put("entity.extrabotany." + name, en);
        zh_cn.put("entity.extrabotany." + name, zh);
    }
}
