package io.grasspow.extrabotany.common.item.equipment.tool.hammer;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.tool.terrasteel.TerraShattererItem;
import vazkii.botania.common.lib.BotaniaTags;

public class TerrasteelHammer extends ManasteelHammer {
    public TerrasteelHammer(Properties props) {
        super(ModTiers.TERRASTEEL, props, -2.8F);
    }

    public static boolean shouldFilterOut(Entity e, ItemStack tool, ItemStack drop) {
        if (!tool.isEmpty() && (tool.is(BotaniaItems.elementiumPick)
                || tool.is(BotaniaItems.terraPick) && TerraShattererItem.isTipped(tool))) {
            return !drop.isEmpty() && (isDisposable(drop) || isSemiDisposable(drop) && !e.isShiftKeyDown());
        }
        return false;
    }

    private static boolean isDisposable(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return stack.is(BotaniaTags.Items.DISPOSABLE);
    }

    private static boolean isSemiDisposable(ItemStack stack) {
        return stack.is(BotaniaTags.Items.SEMI_DISPOSABLE);
    }
}
