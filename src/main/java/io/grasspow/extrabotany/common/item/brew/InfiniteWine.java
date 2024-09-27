package io.grasspow.extrabotany.common.item.brew;

import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.item.Relic;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.brew.BaseBrewItem;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class InfiniteWine extends BaseBrewItem implements Relic, CustomDamageItem {
    private static final String TAG_SOULBIND_UUID = "soulbindUUID";
    private static final int MANA_PER_DAMAGE = 12000;

    public InfiniteWine(Properties builder) {
        super(builder, 12, 18, ExtraBotanyItems.EMPTY_BOTTLE);
    }

    @Override
    public void bindToUUID(UUID uuid) {
        ItemNBTHelper.setString(getBaseStack(), TAG_SOULBIND_UUID, uuid.toString());
    }

    @Override
    public @Nullable UUID getSoulbindUUID() {
        if (ItemNBTHelper.verifyExistance(getBaseStack(), TAG_SOULBIND_UUID)) {
            try {
                return UUID.fromString(ItemNBTHelper.getString(getBaseStack(), TAG_SOULBIND_UUID, ""));
            } catch (IllegalArgumentException ex) { // Bad UUID in tag
                ItemNBTHelper.removeEntry(getBaseStack(), TAG_SOULBIND_UUID);
            }
        }
        return null;
    }

    @Override
    public void tickBinding(Player player) {

    }

    @Override
    public boolean isRightPlayer(Player player) {
        return player.getUUID().equals(getSoulbindUUID());
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, MANA_PER_DAMAGE);
    }

    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null) {
        };
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
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
}
