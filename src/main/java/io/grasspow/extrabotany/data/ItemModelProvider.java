package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static io.grasspow.extrabotany.common.registry.ExtraBotanyItems.MOD_ITEMS;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {


    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraBotanyAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        List<RegistryObject<Item>> ignores = List.of(
                ExtraBotanyItems.COCKTAIL, ExtraBotanyItems.INFINITE_WINE, ExtraBotanyItems.SPLASH_GRENADE
        );
        new ArrayList<>(MOD_ITEMS).stream().filter(o -> !ignores.contains(o)).forEach(defItem -> basicItem(defItem.get()));
        List.of(
                ExtraBotanyItems.ELEMENTIUM_HAMMER,
                ExtraBotanyItems.MANASTEEL_HAMMER,
                ExtraBotanyItems.TERRASTEEL_HAMMER,
                ExtraBotanyItems.ULTIMATE_HAMMER,
                ExtraBotanyItems.MANA_READER
        ).forEach(this::heldItem);
        generated1(LibItemNames.SPLASH_GRENADE);

        //todo: brews' model swift
        override1(LibItemNames.COCKTAIL, LibItemNames.EMPTY_BOTTLE, 7);
        override1(LibItemNames.INFINITE_WINE, LibItemNames.EMPTY_BOTTLE, 6, 2);
    }

    protected void heldItem(RegistryObject<Item> item) {
        basicItem(item.get()).parent(new ModelFile.UncheckedModelFile("item/handheld"));
    }

    protected void generated1(String name) {
        singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + name)
        ).texture("layer1", modLoc("item/" + name + "_1"));
    }

    protected void override1(String name, String empty, int times) {
        for (int i = 1; i <= times; i++) {
            singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + empty)).texture("layer1", modLoc("item/" + name + "_" + i));
        }
        ItemModelBuilder builder = singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + empty)).texture("layer1", modLoc("item/" + name));
        for (int i = 1; i <= times; i++) {
            builder.override().predicate(new ResourceLocation("botania:swigs_taken"), i * 1.0f).model(new ModelFile.UncheckedModelFile(modLoc("item/" + name + "_" + i))).end();
        }
    }

    protected void override1(String name, String empty, int times, int skip) {
        for (int i = 1; i <= times; i++) {
            singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + empty)).texture("layer1", modLoc("item/" + name + "_" + (i * skip - 1)));
        }
        ItemModelBuilder builder = singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("item/" + empty)).texture("layer1", modLoc("item/" + name));
        for (int i = 1; i <= times; i++) {
            builder.override().predicate(new ResourceLocation("botania:swigs_taken"), i * skip * 1.0f - 1).model(new ModelFile.UncheckedModelFile(modLoc("item/" + name + "_" + (i * skip - 1)))).end();
        }
    }
}
