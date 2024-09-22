package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.ExtraBotany;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import static io.grasspow.extrabotany.common.block.ExtraBotanyBlocks.BLOCKS;
import static io.grasspow.extrabotany.common.block.ExtraBotanyBlocks.MOD_BLOCKS;
import static io.grasspow.extrabotany.common.item.ExtraBotanyItems.MOD_BLOCK_ITEMS;
import static io.grasspow.extrabotany.common.item.ExtraBotanyItems.MOD_ITEMS;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraBotany.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        MOD_ITEMS.forEach(item -> basicItem(item.get()));
        MOD_BLOCKS.forEach(block -> withExistingParent(block.getId().getPath(), ModelLocationUtils.getModelLocation(block.get())));
    }
}
