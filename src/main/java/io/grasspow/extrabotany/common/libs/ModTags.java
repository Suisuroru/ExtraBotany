package io.grasspow.extrabotany.common.libs;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;


public class ModTags {
    public static class Items {
        public static final TagKey<Item> HAMMER = tag("hammer");
    }

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, resId(name));
    }
}
