package io.grasspow.extrabotany.data.model;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlock;
import vazkii.botania.common.block.LuminizerBlock;
import vazkii.botania.common.block.decor.BotaniaMushroomBlock;
import vazkii.botania.common.block.decor.FloatingFlowerBlock;
import vazkii.botania.data.util.ModelWithOverrides;
import vazkii.botania.data.util.OverrideHolder;
import vazkii.botania.mixin.TextureSlotAccessor;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class ItemModelProvider extends vazkii.botania.data.ItemModelProvider {
    private static final TextureSlot LAYER1 = TextureSlotAccessor.make("layer1");
    private static final TextureSlot LAYER2 = TextureSlotAccessor.make("layer2");
    private static final TextureSlot LAYER3 = TextureSlotAccessor.make("layer3");
    private static final ModelTemplate GENERATED_1 = new ModelTemplate(Optional.of(new ResourceLocation("item/generated")), Optional.empty(), TextureSlot.LAYER0, LAYER1);
    private static final ModelTemplate GENERATED_2 = new ModelTemplate(Optional.of(new ResourceLocation("item/generated")), Optional.empty(), TextureSlot.LAYER0, LAYER1, LAYER2);
    private static final ModelTemplate HANDHELD_1 = new ModelTemplate(Optional.of(new ResourceLocation("item/handheld")), Optional.empty(), TextureSlot.LAYER0, LAYER1);
    private static final ModelTemplate HANDHELD_3 = new ModelTemplate(Optional.of(new ResourceLocation("item/handheld")), Optional.empty(), TextureSlot.LAYER0, LAYER1, LAYER2, LAYER3);
    private static final TextureSlot OUTSIDE = TextureSlotAccessor.make("outside");
    private static final TextureSlot CORE = TextureSlotAccessor.make("core");
    private static final ModelWithOverrides GENERATED_OVERRIDES = new ModelWithOverrides(new ResourceLocation("item/generated"), TextureSlot.LAYER0);
    private static final ModelWithOverrides GENERATED_OVERRIDES_1 = new ModelWithOverrides(new ResourceLocation("item/generated"), TextureSlot.LAYER0, LAYER1);
    private static final ModelWithOverrides HANDHELD_OVERRIDES = new ModelWithOverrides(new ResourceLocation("item/handheld"), TextureSlot.LAYER0);
    private static final ModelWithOverrides HANDHELD_OVERRIDES_2 = new ModelWithOverrides(new ResourceLocation("item/handheld"), TextureSlot.LAYER0, LAYER1, LAYER2);

    private final PackOutput packOutput;

    public ItemModelProvider(PackOutput packOutput) {
        super(packOutput);
        this.packOutput = packOutput;
    }

    @Override
    public String getName() {
        return "ExtraBotany item models";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i -> LibMisc.MOD_ID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace()))
                .collect(Collectors.toSet());
        Map<ResourceLocation, Supplier<JsonElement>> map = new HashMap<>();
        registerItemBlocks(takeAll(items, i -> i instanceof BlockItem).stream().map(i -> (BlockItem) i).collect(Collectors.toSet()), map::put);
        registerItemOverrides(items, map::put);
        registerItems(items, map::put);
        PackOutput.PathProvider modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        List<CompletableFuture<?>> output = new ArrayList<>();
        for (Map.Entry<ResourceLocation, Supplier<JsonElement>> e : map.entrySet()) {
            ResourceLocation id = e.getKey();
            output.add(DataProvider.saveStable(cache, e.getValue().get(), modelPathProvider.json(id)));
        }
        return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
    }

    private void registerItemBlocks(Set<BlockItem> itemBlocks, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        //flowers
        itemBlocks.removeIf(i -> {
            var id = BuiltInRegistries.BLOCK.getKey(i.getBlock());
            return id.getNamespace().equals(vazkii.botania.common.lib.LibMisc.MOD_ID) && i.getBlock() instanceof FloatingFlowerBlock;
        });
        Predicate<BlockItem> defaultGenerated = i -> {
            Block b = i.getBlock();
            return XplatAbstractions.INSTANCE.isSpecialFlowerBlock(b)
                    || b instanceof BotaniaMushroomBlock
                    || b instanceof LuminizerBlock
                    || b instanceof BotaniaFlowerBlock
                    || b == BotaniaBlocks.ghostRail;
        };
        takeAll(itemBlocks, defaultGenerated).forEach(i -> {
            ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(i), TextureMapping.layer0(i.getBlock()), consumer);
        });
    }

    private void registerItemOverrides(Set<Item> items, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        OverrideHolder cocktailOverrides = new OverrideHolder();
        for (int i = 1; i <= 8; i++) {
            ResourceLocation overrideModel = ModelLocationUtils.getModelLocation(ExtraBotanyItems.COCKTAIL.get(), "_" + i);
            GENERATED_1.create(overrideModel,
                    TextureMapping.layer0(resId("item/" + LibItemNames.EMPTY_BOTTLE)).put(LAYER1, overrideModel),
                    consumer);
            cocktailOverrides.add(overrideModel, Pair.of(resId("swigs_taken"), (double) i / 100));
        }
        GENERATED_OVERRIDES_1.create(ModelLocationUtils.getModelLocation(ExtraBotanyItems.COCKTAIL.get()),
                TextureMapping.layer0(resId("item/" + LibItemNames.EMPTY_BOTTLE)).put(LAYER1, TextureMapping.getItemTexture(ExtraBotanyItems.COCKTAIL.get(), "_1")),
                cocktailOverrides,
                consumer);
        items.remove(ExtraBotanyItems.COCKTAIL.get());

        OverrideHolder infiniteWineOverrides = new OverrideHolder();
        for (int i = 1; i <= 12; i++) {
            ResourceLocation overrideModel = ModelLocationUtils.getModelLocation(ExtraBotanyItems.INFINITE_WINE.get(), "_" + (i / 2 + i % 2));
            GENERATED_1.create(overrideModel,
                    TextureMapping.layer0(resId("item/" + LibItemNames.INFINITE_WINE)).put(LAYER1, overrideModel),
                    consumer);
            infiniteWineOverrides.add(overrideModel, Pair.of(resId("swigs_taken"), (double) i / 100));
        }
        GENERATED_OVERRIDES_1.create(ModelLocationUtils.getModelLocation(ExtraBotanyItems.INFINITE_WINE.get()),
                TextureMapping.layer0(resId("item/" + LibItemNames.INFINITE_WINE)).put(LAYER1, TextureMapping.getItemTexture(ExtraBotanyItems.INFINITE_WINE.get(), "_1")),
                infiniteWineOverrides,
                consumer);
        items.remove(ExtraBotanyItems.INFINITE_WINE.get());
    }

    private void registerItems(Set<Item> items, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        //skip
        items.remove(ExtraBotanyItems.SILVER_BULLET.get());
        items.remove(ExtraBotanyItems.FLAMESCION_WEAPON.get());

        takeAll(items,
                ExtraBotanyItems.SPLASH_GRENADE.get()
        ).forEach(i -> GENERATED_1.create(ModelLocationUtils.getModelLocation(i),
                TextureMapping
                        .layer0(TextureMapping.getItemTexture(i))
                        .put(LAYER1, TextureMapping.getItemTexture(i, "_1")),
                consumer));
        takeAll(items,
                //hammer
                ExtraBotanyItems.ELEMENTIUM_HAMMER.get(),
                ExtraBotanyItems.MANASTEEL_HAMMER.get(),
                ExtraBotanyItems.TERRASTEEL_HAMMER.get(),
                ExtraBotanyItems.ULTIMATE_HAMMER.get(),

                //tool
                ExtraBotanyItems.MANA_READER.get(),
                ExtraBotanyItems.WALKING_CANE.get(),
                ExtraBotanyItems.ROD_OF_DISCORD.get(),

                //weapon
                ExtraBotanyItems.SHADOW_KATANA.get()
        ).forEach(i -> ModelTemplates.FLAT_HANDHELD_ITEM.create(ModelLocationUtils.getModelLocation(i), TextureMapping.layer0(i), consumer));
        takeAll(items, i -> true).forEach(i -> ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(i), TextureMapping.layer0(i), consumer));
    }


}
