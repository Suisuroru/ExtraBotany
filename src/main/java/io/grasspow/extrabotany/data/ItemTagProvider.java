package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.api.item.IHammer;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.libs.ModTags;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
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
import vazkii.botania.common.item.lens.LensItem;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.grasspow.extrabotany.common.registry.ExtraBotanyItems.PYLON;

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
        generateAccessoryTags();
        ExtraBotanyItems.MOD_ITEMS.forEach(item -> {
            if (item.get() instanceof IHammer) {
                tag(ModTags.Items.HAMMER).add(item.get());
                tag(BotaniaTags.Items.MANA_USING_ITEMS).add(item.get());
            }
        });
        tag(ModTags.Items.PEDESTAL_DENY).add(Items.SHIELD);
        tag(BotaniaTags.Items.MANA_USING_ITEMS).add(ExtraBotanyItems.INFINITE_WINE.get());
        TagsProvider.TagAppender<Item> builder = this.tag(BotaniaTags.Items.LENS);
        BuiltInRegistries.ITEM.stream().filter(i -> i instanceof LensItem && BuiltInRegistries.ITEM.getKey(i).getNamespace().equals(LibMisc.MOD_ID))
                .map(BuiltInRegistries.ITEM::getKey)
                .sorted()
                .forEach(item -> builder.add(ResourceKey.create(Registries.ITEM, item)));
        List.of(
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
                .forEach(i -> tag(i).add(ExtraBotanyItems.UNIVERSAL_PETAL.get()));
        tag(BotaniaTags.Items.RUNES).add(ExtraBotanyItems.ELEMENT_RUNE.get(), ExtraBotanyItems.SIN_RUNE.get());
    }


    private void generateAccessoryTags() {
        tag(accessory("curio")).add(
                PYLON.get()
        );
    }

    private static TagKey<Item> accessory(String name) {
        return ItemTags.create(new ResourceLocation("curios", name));
    }
}
