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
        this.generateAccessoryTags();

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
