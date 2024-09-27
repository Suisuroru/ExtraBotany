package io.grasspow.extrabotany.common.item.brew;

import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import vazkii.botania.common.item.brew.BaseBrewItem;

public class Cocktail extends BaseBrewItem {
    public Cocktail(Properties builder) {
        super(builder, 8, 20, ExtraBotanyItems.EMPTY_BOTTLE);
    }
}
