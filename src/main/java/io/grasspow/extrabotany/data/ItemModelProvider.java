package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.ExtraBotany;
import io.grasspow.extrabotany.api.IHammer;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static io.grasspow.extrabotany.common.registry.ModItems.MOD_ITEMS;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraBotany.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        MOD_ITEMS.forEach(defItem -> {
            Item item = defItem.get();
            if (item instanceof IHammer) {
                heldItem(item);
            } else {
                basicItem(item);
            }
        });
    }

    protected ItemModelBuilder heldItem(Item item) {
        ResourceLocation id = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
        return getBuilder(id.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(id.getNamespace(), "item/" + id.getPath()));
    }
}
