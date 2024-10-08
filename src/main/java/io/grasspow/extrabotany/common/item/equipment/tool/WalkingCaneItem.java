package io.grasspow.extrabotany.common.item.equipment.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class WalkingCaneItem extends Item {
    public WalkingCaneItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> ret = super.getAttributeModifiers(slot, stack);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Cane modifier", 0.3D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return ret;
    }
}
