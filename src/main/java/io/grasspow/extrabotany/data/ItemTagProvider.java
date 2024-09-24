package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.api.IHammer;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.libs.ModTags;
import io.grasspow.extrabotany.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static io.grasspow.extrabotany.common.registry.ModItems.PYLON;

public class ItemTagProvider extends ItemTagsProvider {
    public ItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider,
                           CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blockTagProvider, LibMisc.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.generateAccessoryTags();

        ModItems.MOD_ITEMS.forEach(item -> {
            if (item.get() instanceof IHammer) {
                tag(ModTags.Items.HAMMER).add(item.get());
            }
        });
    }

    @Override
    public String getName() {
        return "ExtraBotany item tags";
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
