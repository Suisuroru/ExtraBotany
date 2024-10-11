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

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class MaidArmorItem extends MikuArmorItem {
    public MaidArmorItem(Properties props, Type type) {
        this(ExtraBotanyAPI.instance().getMaidArmorMaterial(), props, type);
    }

    public MaidArmorItem(ArmorMaterial mat, Properties props, Type type) {
        super(mat, props, type);
        setArmorTexture(LibMisc.MOD_ID + ":textures/model/maid_armor.png");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(stack.getItem().hashCode() + slot.toString().hashCode(), 0);
        if (slot == getEquipmentSlot()) {
            attributes = HashMultimap.create(attributes);
            attributes.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "Maid modifier " + type, 5, AttributeModifier.Operation.ADDITION));
            attributes.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Maid modifier " + type, type.ordinal() / 20, AttributeModifier.Operation.ADDITION));
        }
        return attributes;
    }

    private static final Supplier<ItemStack[]> armorSet = Suppliers.memoize(() -> new ItemStack[]{
            new ItemStack(ExtraBotanyItems.MAID_HELM.get()),
            new ItemStack(ExtraBotanyItems.MAID_CHEST.get()),
            new ItemStack(ExtraBotanyItems.MAID_LEGS.get()),
            new ItemStack(ExtraBotanyItems.MAID_BOOTS.get())
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
            case HEAD -> stack.is(ExtraBotanyItems.MAID_HELM.get());
            case CHEST -> stack.is(ExtraBotanyItems.MAID_CHEST.get());
            case LEGS -> stack.is(ExtraBotanyItems.MAID_LEGS.get());
            case FEET -> stack.is(ExtraBotanyItems.MAID_BOOTS.get());
            default -> false;
        };
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.maid.name");
    }

    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list, Player player) {
        list.add(Component.translatable("extrabotany.armorset.maid.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.maid.desc1").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.maid.desc2").withStyle(ChatFormatting.GRAY));
    }
}
