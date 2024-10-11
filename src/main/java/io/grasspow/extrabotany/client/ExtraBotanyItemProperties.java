package io.grasspow.extrabotany.client;

import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.item.brew.BaseBrewItem;
import vazkii.botania.network.TriConsumer;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class ExtraBotanyItemProperties {
    public static void init(TriConsumer<ItemLike, ResourceLocation, ClampedItemPropertyFunction> consumer) {
        ClampedItemPropertyFunction brewGetter = (stack, world, entity, seed) -> {
            BaseBrewItem item = ((BaseBrewItem) stack.getItem());
            return (float) (item.getSwigs() - item.getSwigsLeft(stack)) / 100;
        };
        consumer.accept(ExtraBotanyItems.COCKTAIL.get(), resId("swigs_taken"), brewGetter);
        consumer.accept(ExtraBotanyItems.INFINITE_WINE.get(), resId("swigs_taken"), brewGetter);
    }
}
