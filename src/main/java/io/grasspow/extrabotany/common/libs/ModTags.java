package io.grasspow.extrabotany.common.libs;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;


public final class ModTags {
    public static class Items {
        public static final TagKey<Item> HAMMER = tag("hammer");
        public static final TagKey<Item> PEDESTAL_DENY = tag("pedestal_deny");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, resId(name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> SHADOWIUM_BLOCK = tag(LibBlockNames.SHADOWIUM_BLOCK);
        public static final TagKey<Block> PHOTONIUM_BLOCK = tag(LibBlockNames.PHOTONIUM_BLOCK);
        public static final TagKey<Block> ORICHALCOS_BLOCK = tag(LibBlockNames.ORICHALCOS_BLOCK);

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, resId(name));
        }
    }
}
