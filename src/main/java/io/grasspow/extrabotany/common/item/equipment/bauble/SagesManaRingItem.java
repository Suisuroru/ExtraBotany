package io.grasspow.extrabotany.common.item.equipment.bauble;

import io.grasspow.extrabotany.api.capability.IAdvancementRequirement;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.item.Relic;
import vazkii.botania.common.item.equipment.bauble.GreaterBandOfManaItem;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;


public class SagesManaRingItem extends GreaterBandOfManaItem implements IAdvancementRequirement {

    protected static final int MAX_MANA = Integer.MAX_VALUE - 1;

    public SagesManaRingItem(Properties props) {
        super(props);
    }

    @Override
    public void addToCreativeTab(Item me, CreativeModeTab.Output output) {
        output.accept(this);

        ItemStack full = new ItemStack(this);
        setMana(full, MAX_MANA);
        output.accept(full);
    }

    public static Relic makeRelic(net.minecraft.world.item.ItemStack stack) {
        return new RelicImpl(stack, null) {
        };
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof Player player) {
            var relic = XplatAbstractions.INSTANCE.findRelic(stack);
            if (relic != null) {
                relic.tickBinding(player);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        RelicImpl.addDefaultTooltip(stack, tooltip);
    }

    @Override
    public @NotNull Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGO_DEFEAT;
    }

    public static class SagesManaRingItemImpl extends GreaterManaItemImpl {
        public SagesManaRingItemImpl(ItemStack stack) {
            super(stack);
        }

        @Override
        public int getMaxMana() {
            return MAX_MANA * stack.getCount();
        }
    }
}
