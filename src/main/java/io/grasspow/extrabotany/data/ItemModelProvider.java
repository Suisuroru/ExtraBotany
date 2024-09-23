package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.ExtraBotany;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import static io.grasspow.extrabotany.common.item.ExtraBotanyItems.MOD_ITEMS;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraBotany.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        MOD_ITEMS.forEach(item -> basicItem(item.get()));
    }
}
