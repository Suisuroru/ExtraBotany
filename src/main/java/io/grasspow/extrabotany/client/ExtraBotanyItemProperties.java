package io.grasspow.extrabotany.client;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.item.equipment.weapon.FailnaughtItem;
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
        ClampedItemPropertyFunction pulling = (stack, worldIn, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        ClampedItemPropertyFunction pull = (stack, worldIn, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                FailnaughtItem item = ((FailnaughtItem) stack.getItem());
                return entity.getUseItem() != stack
                        ? 0.0F
                        : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) * item.chargeVelocityMultiplier() / 20.0F;
            }
        };
        consumer.accept(ExtraBotanyItems.COCKTAIL.get(), resId("swigs_taken"), brewGetter);
        consumer.accept(ExtraBotanyItems.INFINITE_WINE.get(), resId("swigs_taken"), brewGetter);
        consumer.accept(ExtraBotanyItems.FAILNAUGHT.get(), new ResourceLocation("pulling"), pulling);
        consumer.accept(ExtraBotanyItems.FAILNAUGHT.get(), new ResourceLocation("pull"), pull);
    }
}
