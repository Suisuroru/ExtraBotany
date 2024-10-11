package io.grasspow.extrabotany.common.item.equipment.armor;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.botania.common.helper.ItemNBTHelper;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class GoblinsLayerArmorItem extends MikuArmorItem {
    public static final String TAG_DAY = "is_day";

    public GoblinsLayerArmorItem(Properties props, Type type) {
        this(ExtraBotanyAPI.instance().getGoblinSlayerArmorMaterial(), props, type);
    }

    public GoblinsLayerArmorItem(ArmorMaterial mat, Properties props, Type type) {
        super(mat, props, type);
        setArmorTexture(LibMisc.MOD_ID + ":textures/model/goblins_layer_armor.png");
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (!level.isClientSide()) {
            ItemNBTHelper.setBoolean(stack, TAG_DAY, hasArmorSet(player) && level.isDay());
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(stack.getItem().hashCode() + slot.toString().hashCode(), 0);
        boolean night = ItemNBTHelper.getBoolean(stack, TAG_DAY, false);
        if (slot == getEquipmentSlot()) {
            attributes = HashMultimap.create(attributes);
            if (night) {
                attributes.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "GoblinSlayer modifier " + type, 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
                attributes.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "GoblinSlayer modifier " + type, 0.04F, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
        return attributes;
    }

    private static final Supplier<ItemStack[]> armorSet = Suppliers.memoize(() -> new ItemStack[]{
            new ItemStack(ExtraBotanyItems.GOBLINS_LAYER_HELM.get()),
            new ItemStack(ExtraBotanyItems.GOBLINS_LAYER_CHEST.get()),
            new ItemStack(ExtraBotanyItems.GOBLINS_LAYER_LEGS.get()),
            new ItemStack(ExtraBotanyItems.GOBLINS_LAYER_BOOTS.get())
    });

    @Override
    public ItemStack[] getArmorSetStacks() {
        return armorSet.get();
    }

    @Override
    public boolean hasArmorSetItem(Player player, EquipmentSlot slot) {
        if (player == null) {
            return false;
        }

        ItemStack stack = player.getItemBySlot(slot);
        if (stack.isEmpty()) {
            return false;
        }

        return switch (slot) {
            case HEAD -> stack.is(ExtraBotanyItems.GOBLINS_LAYER_HELM.get());
            case CHEST -> stack.is(ExtraBotanyItems.GOBLINS_LAYER_CHEST.get());
            case LEGS -> stack.is(ExtraBotanyItems.GOBLINS_LAYER_LEGS.get());
            case FEET -> stack.is(ExtraBotanyItems.GOBLINS_LAYER_BOOTS.get());
            default -> false;
        };
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.goblins_layer.name");
    }

    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list, Player player) {
        list.add(Component.translatable("extrabotany.armorset.goblins_layer.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.goblins_layer.desc1").withStyle(ChatFormatting.GRAY));
    }
}
