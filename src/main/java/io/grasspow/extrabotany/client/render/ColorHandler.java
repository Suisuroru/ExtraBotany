package io.grasspow.extrabotany.client.render;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.BrewItem;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.common.brew.BotaniaBrews;

public final class ColorHandler {
    public interface BlockHandlerConsumer {
        void register(BlockColor handler, Block... blocks);
    }

    public interface ItemHandlerConsumer {
        void register(ItemColor handler, ItemLike... items);
    }

    public static void submitBlocks(BlockHandlerConsumer blocks) {
    }

    public static void submitItems(ItemHandlerConsumer items) {
        items.register((s, t) ->
        {
            if (t != 1) {
                return -1;
            }

            Brew brew = ((BrewItem) s.getItem()).getBrew(s);
            if (brew == BotaniaBrews.fallbackBrew) {
                return 0xC6000E;
                // return s.getItem() instanceof ItemBloodPendant ? 0xC6000E : 0x989898;
            }
            int color = brew.getColor(s);
            double speed = 0.2;

            //s.is(chick.extrabotany.common.ModItems.COCK_TAIL.get()) || s.is(ModItems.brewVial) ? 0.1 : 0.2;

            int add = (int) (Math.sin(ClientTickHandler.ticksInGame * speed) * 24);

            int r = Math.max(0, Math.min(255, (color >> 16 & 0xFF) + add));
            int g = Math.max(0, Math.min(255, (color >> 8 & 0xFF) + add));
            int b = Math.max(0, Math.min(255, (color & 0xFF) + add));

            return r << 16 | g << 8 | b;
        }, ExtraBotanyItems.SPLASH_GRENADE.get(), ExtraBotanyItems.COCKTAIL.get(), ExtraBotanyItems.INFINITE_WINE.get());

        items.register((s, t) -> {
            if (t != 0) {
                return -1;
            }
            return Mth.hsvToRgb(ClientTickHandler.ticksInGame * 2 % 360 / 360F, 0.25F, 1F);
        }, ExtraBotanyItems.UNIVERSAL_PETAL.get());
    }

    private ColorHandler() {
    }
}
