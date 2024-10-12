package io.grasspow.extrabotany.common.item.equipment.armor;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.LibMisc;
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

public class ShadowWarriorArmorItem extends MikuArmorItem {
    public static final String TAG_NIGHT = "is_night";

    public ShadowWarriorArmorItem(Properties props, Type type) {
        this(ExtraBotanyAPI.instance().getShadowWarriorArmorMaterial(), props, type);
    }

    public ShadowWarriorArmorItem(ArmorMaterial mat, Properties props, Type type) {
        super(mat, props, type);
        setArmorTexture(LibMisc.MOD_ID + ":textures/model/shadow_warrior_armor.png");
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (!level.isClientSide()) {
            ItemNBTHelper.setBoolean(stack, TAG_NIGHT, hasArmorSet(player) && level.isNight());
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(stack.getItem().hashCode() + slot.toString().hashCode(), 0);
        boolean night = ItemNBTHelper.getBoolean(stack, TAG_NIGHT, false);
        if (slot == getEquipmentSlot()) {
            attributes = HashMultimap.create(super.getAttributeModifiers(slot, stack));
            if (night) {
                attributes.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "ShadowWarrior modifier health" + type, 0.25F, AttributeModifier.Operation.MULTIPLY_BASE));
                attributes.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "ShadowWarrior modifier attack speed" + type, 0.125F, AttributeModifier.Operation.MULTIPLY_BASE));
                attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "ShadowWarrior modifier attack damage" + type, 0.05F, AttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
        return attributes;
    }

    private static final Supplier<ItemStack[]> armorSet = Suppliers.memoize(() -> new ItemStack[]{
            new ItemStack(ExtraBotanyItems.SHADOW_WARRIOR_HELM.get()),
            new ItemStack(ExtraBotanyItems.SHADOW_WARRIOR_CHEST.get()),
            new ItemStack(ExtraBotanyItems.SHADOW_WARRIOR_LEGS.get()),
            new ItemStack(ExtraBotanyItems.SHADOW_WARRIOR_BOOTS.get())
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
            case HEAD -> stack.is(ExtraBotanyItems.SHADOW_WARRIOR_HELM.get());
            case CHEST -> stack.is(ExtraBotanyItems.SHADOW_WARRIOR_CHEST.get());
            case LEGS -> stack.is(ExtraBotanyItems.SHADOW_WARRIOR_LEGS.get());
            case FEET -> stack.is(ExtraBotanyItems.SHADOW_WARRIOR_BOOTS.get());
            default -> false;
        };
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.shadow_warrior.name");
    }

    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list, Player player) {
        list.add(Component.translatable("extrabotany.armorset.shadow_warrior.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shadow_warrior.desc1").withStyle(ChatFormatting.GRAY));
    }
}
