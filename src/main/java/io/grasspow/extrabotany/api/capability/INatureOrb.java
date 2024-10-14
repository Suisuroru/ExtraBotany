package io.grasspow.extrabotany.api.capability;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * An item that has this capability can contain nature.
 */
public interface INatureOrb {
    int getNature();

    int getMaxNature();

    boolean addNature(int x);

    void setNature(int x);

    boolean canReceiveNatureFromNatureAdder(BlockEntity adder);

    boolean canReceiveManaFromItem(ItemStack otherStack);

    boolean canExportManaToPool(BlockEntity pool);

    boolean canExportManaToItem(ItemStack otherStack);

    boolean isNoExport();
}
