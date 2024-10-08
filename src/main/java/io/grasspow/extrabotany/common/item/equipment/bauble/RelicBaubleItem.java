package io.grasspow.extrabotany.common.item.equipment.bauble;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class RelicBaubleItem extends vazkii.botania.common.item.relic.RelicBaubleItem {
    public RelicBaubleItem(Properties props) {
        super(props);
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public @NotNull Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }
}
