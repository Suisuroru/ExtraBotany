package io.grasspow.extrabotany.data.tag;

import io.grasspow.extrabotany.common.libs.ExtraBotanyTags;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.lens.LensItem;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static io.grasspow.extrabotany.common.registry.ExtraBotanyItems.*;

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
    }


    private void registerCuriosTag() {
        Stream.of(
                PYLON, POTATO_CHIPS
        ).map(RegistryObject::get).forEach(tag(accessory("head"))::add);
        Stream.of(
                MOON_PENDANT
        ).map(RegistryObject::get).forEach(tag(accessory("necklace"))::add);
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
        BuiltInRegistries.ITEM.stream().filter(i -> i instanceof LensItem && BuiltInRegistries.ITEM.getKey(i).getNamespace().equals(LibMisc.MOD_ID))
                .map(BuiltInRegistries.ITEM::getKey)
                .sorted()
                .forEach(item -> builder.add(ResourceKey.create(Registries.ITEM, item)));
        Stream.of(
                MANASTEEL_HAMMER, ELEMENTIUM_HAMMER, TERRASTEEL_HAMMER, ULTIMATE_HAMMER,
                INFINITE_WINE,
                FROST_STAR, DEATH_RING, MANA_DRIVE_RING, JINGWEI_FEATHER, POTATO_CHIPS,
                SUN_RING, MOON_PENDANT, ROD_OF_DISCORD
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
