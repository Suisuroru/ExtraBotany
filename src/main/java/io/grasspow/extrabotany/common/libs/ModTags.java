package io.grasspow.extrabotany.common.libs;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;


public final class ModTags {
    public static class Items {
        public static final TagKey<Item> HAMMER = tag("hammer");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, resId(name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> SHADOWIUM_BLOCK = tag("shadowium_blocks");
        public static final TagKey<Block> PHOTONIUM_BLOCK = tag("photonium_blocks");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, resId(name));
        }
    }
}
