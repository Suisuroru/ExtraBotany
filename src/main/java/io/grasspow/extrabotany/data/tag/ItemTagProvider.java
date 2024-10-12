package io.grasspow.extrabotany.data.tag;

import io.grasspow.extrabotany.common.libs.ExtraBotanyTags;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.lens.LensItem;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static io.grasspow.extrabotany.common.item.ExtraBotanyItems.*;

public class ItemTagProvider extends ItemTagsProvider {
    public ItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                           CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blockTagProvider, LibMisc.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "ExtraBotany item tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        registerVanillaTag();
        registerCuriosTag();
        registerBotaniaTag();
        Stream.of(
                MANASTEEL_HAMMER, ELEMENTIUM_HAMMER, TERRASTEEL_HAMMER, ULTIMATE_HAMMER
        ).map(RegistryObject::get).forEach(tag(ExtraBotanyTags.Items.HAMMER)::add);
        tag(ExtraBotanyTags.Items.INGOTS_SHADOWIUM).add(SHADOWIUM.get());
        tag(ExtraBotanyTags.Items.INGOTS_PHOTONIUM).add(PHOTONIUM.get());
        tag(ExtraBotanyTags.Items.INGOTS_AERIALITE).add(AERIALITE.get());
        tag(ExtraBotanyTags.Items.INGOTS_ORICHALCOS).add(ORICHALCOS.get());
        tag(ExtraBotanyTags.Items.PEDESTAL_DENY).add(Items.SHIELD);
        tag(ExtraBotanyTags.Items.RUNES_WATER).add(ELEMENT_RUNE.get(), BotaniaItems.runeWater);
        tag(ExtraBotanyTags.Items.RUNES_FIRE).add(ELEMENT_RUNE.get(), BotaniaItems.runeFire);
        tag(ExtraBotanyTags.Items.RUNES_EARTH).add(ELEMENT_RUNE.get(), BotaniaItems.runeEarth);
        tag(ExtraBotanyTags.Items.RUNES_AIR).add(ELEMENT_RUNE.get(), BotaniaItems.runeAir);
        tag(ExtraBotanyTags.Items.RUNES_SPRING).add(ELEMENT_RUNE.get(), BotaniaItems.runeSpring);
        tag(ExtraBotanyTags.Items.RUNES_SUMMER).add(ELEMENT_RUNE.get(), BotaniaItems.runeSummer);
        tag(ExtraBotanyTags.Items.RUNES_AUTUMN).add(ELEMENT_RUNE.get(), BotaniaItems.runeAutumn);
        tag(ExtraBotanyTags.Items.RUNES_WINTER).add(ELEMENT_RUNE.get(), BotaniaItems.runeWinter);
        tag(ExtraBotanyTags.Items.RUNES_MANA).add(SIN_RUNE.get(), BotaniaItems.runeMana);
        tag(ExtraBotanyTags.Items.RUNES_LUST).add(SIN_RUNE.get(), BotaniaItems.runeLust);
        tag(ExtraBotanyTags.Items.RUNES_GLUTTONY).add(SIN_RUNE.get(), BotaniaItems.runeGluttony);
        tag(ExtraBotanyTags.Items.RUNES_GREED).add(SIN_RUNE.get(), BotaniaItems.runeGreed);
        tag(ExtraBotanyTags.Items.RUNES_SLOTH).add(SIN_RUNE.get(), BotaniaItems.runeSloth);
        tag(ExtraBotanyTags.Items.RUNES_WRATH).add(SIN_RUNE.get(), BotaniaItems.runeWrath);
        tag(ExtraBotanyTags.Items.RUNES_ENVY).add(SIN_RUNE.get(), BotaniaItems.runeEnvy);
        tag(ExtraBotanyTags.Items.RUNES_PRIDE).add(SIN_RUNE.get(), BotaniaItems.runePride);
    }

    private void registerVanillaTag() {
//        tag(ItemTags.MUSIC_DISCS).add(RECORD_EGO.get(),RECORD_HERRSCHER.get());

        //armor
        this.tag(Tags.Items.ARMORS_HELMETS).add(
                MIKU_HELM.get(), SHOOTING_GUARDIAN_HELM.get(), GOBLINS_LAYER_HELM.get(), MAID_HELM.get(), SHADOW_WARRIOR_HELM.get()
        );
        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(
                MIKU_CHEST.get(), SHOOTING_GUARDIAN_CHEST.get(), GOBLINS_LAYER_CHEST.get(), MAID_CHEST.get(), SHADOW_WARRIOR_CHEST.get()
        );
        this.tag(Tags.Items.ARMORS_LEGGINGS).add(
                MIKU_LEGS.get(), SHOOTING_GUARDIAN_LEGS.get(), GOBLINS_LAYER_LEGS.get(), MAID_LEGS.get(), SHADOW_WARRIOR_LEGS.get()
        );
        this.tag(Tags.Items.ARMORS_BOOTS).add(
                MIKU_BOOTS.get(), SHOOTING_GUARDIAN_BOOTS.get(), GOBLINS_LAYER_BOOTS.get(), MAID_BOOTS.get(), SHADOW_WARRIOR_BOOTS.get()
        );

        //weapon
        this.tag(ItemTags.SWORDS).add(
                SHADOW_KATANA.get(), FLAMESCION_WEAPON.get(), INFLUX_WAVER.get(), STAR_WRATH.get(),
                TRUE_SHADOW_KATANA.get()
        );
    }


    private void registerCuriosTag() {
        Stream.of(
                POTATO_CHIPS,
                FOX_EAR, FOX_MASK, PYLON, BLACK_GLASSES, SUPER_CROWN, THUG_LIFE, MASK
        ).map(RegistryObject::get).forEach(tag(accessory("head"))::add);
        Stream.of(
                MOON_PENDANT
        ).map(RegistryObject::get).forEach(tag(accessory("necklace"))::add);
        Stream.of(
                RED_SCARF
        ).map(RegistryObject::get).forEach(tag(accessory("body"))::add);
        Stream.of(
                FROST_STAR, DEATH_RING, MANA_DRIVE_RING, SUN_RING,
                SAGES_MANA_RING
        ).map(RegistryObject::get).forEach(tag(accessory("ring"))::add);
        Stream.of(
                AERO_STONE, AQUA_STONE, EARTH_STONE, IGNIS_STONE, THE_COMMUNITY,
                PEACE_AMULET, POWER_GLOVE, NATURE_ORB, JINGWEI_FEATHER
        ).map(RegistryObject::get).forEach(tag(accessory("curio"))::add);
    }

    private void registerBotaniaTag() {
        TagsProvider.TagAppender<Item> builder = this.tag(BotaniaTags.Items.LENS);
        MOD_ITEMS.stream()
                .map(RegistryObject::get)
                .filter(i -> i instanceof LensItem)
                .map(ForgeRegistries.ITEMS::getKey)
                .sorted()
                .forEach(item -> builder.add(ResourceKey.create(Registries.ITEM, item)));
        Stream.of(
                MANASTEEL_HAMMER, ELEMENTIUM_HAMMER, TERRASTEEL_HAMMER, ULTIMATE_HAMMER,
                INFINITE_WINE,
                FROST_STAR, DEATH_RING, MANA_DRIVE_RING, JINGWEI_FEATHER, POTATO_CHIPS,
                SUN_RING, MOON_PENDANT, ROD_OF_DISCORD, SILVER_BULLET,
                MIKU_HELM, MIKU_CHEST, MIKU_LEGS, MIKU_BOOTS,
                GOBLINS_LAYER_HELM, GOBLINS_LAYER_CHEST, GOBLINS_LAYER_LEGS, GOBLINS_LAYER_BOOTS,
                SHADOW_WARRIOR_HELM, SHADOW_WARRIOR_CHEST, SHADOW_WARRIOR_LEGS, SHADOW_WARRIOR_BOOTS,
                SHOOTING_GUARDIAN_HELM, SHOOTING_GUARDIAN_CHEST, SHOOTING_GUARDIAN_LEGS, SHOOTING_GUARDIAN_BOOTS,
                MAID_HELM, MAID_CHEST, MAID_LEGS, MAID_BOOTS,
                INFLUX_WAVER, STAR_WRATH, TRUE_SHADOW_KATANA
        ).map(RegistryObject::get).forEach(tag(BotaniaTags.Items.MANA_USING_ITEMS)::add);
        Stream.of(
                        BotaniaTags.Items.PETALS,
                        BotaniaTags.Items.PETALS_BLACK,
                        BotaniaTags.Items.PETALS_BLUE,
                        BotaniaTags.Items.PETALS_BROWN,
                        BotaniaTags.Items.PETALS_CYAN,
                        BotaniaTags.Items.PETALS_GRAY,
                        BotaniaTags.Items.PETALS_GREEN,
                        BotaniaTags.Items.PETALS_LIGHT_BLUE,
                        BotaniaTags.Items.PETALS_LIGHT_GRAY,
                        BotaniaTags.Items.PETALS_LIME,
                        BotaniaTags.Items.PETALS_MAGENTA,
                        BotaniaTags.Items.PETALS_ORANGE,
                        BotaniaTags.Items.PETALS_PINK,
                        BotaniaTags.Items.PETALS_PURPLE,
                        BotaniaTags.Items.PETALS_RED,
                        BotaniaTags.Items.PETALS_WHITE,
                        BotaniaTags.Items.PETALS_YELLOW)
                .forEach(i -> tag(i).add(UNIVERSAL_PETAL.get()));
        tag(BotaniaTags.Items.RUNES).add(ELEMENT_RUNE.get(), SIN_RUNE.get());
    }

    private static TagKey<Item> accessory(String name) {
        return ItemTags.create(new ResourceLocation("curios", name));
    }
}
