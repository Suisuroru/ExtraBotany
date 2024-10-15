package io.grasspow.extrabotany.common.item.equipment.bauble;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.grasspow.extrabotany.api.capability.IAdvancementRequirement;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.item.Relic;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.bauble.*;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;

public class MoonPendantItem extends CirrusAmuletItem implements IAdvancementRequirement {

    public MoonPendantItem(Properties props) {
        super(props);
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        ((SpectatorItem) BotaniaItems.itemFinder).onWornTick(stack, entity);
        ((SnowflakePendantItem) BotaniaItems.icePendant).onWornTick(stack, entity);
        ((CrimsonPendantItem) BotaniaItems.superLavaPendant).onWornTick(stack, entity);
        ((TectonicGirdleItem) BotaniaItems.knockbackBelt).onWornTick(stack, entity);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getEquippedAttributeModifiers(ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(getBaubleUUID(stack), "Moon Pendant", 1, AttributeModifier.Operation.ADDITION));
        return attributes;
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty();
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
}
