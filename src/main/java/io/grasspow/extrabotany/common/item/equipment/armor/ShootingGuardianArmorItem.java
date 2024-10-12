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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class ShootingGuardianArmorItem extends MikuArmorItem {
    public ShootingGuardianArmorItem(Properties props, Type type) {
        this(ExtraBotanyAPI.instance().getShootingGuardianArmorMaterial(), props, type);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerHeal);
    }

    public ShootingGuardianArmorItem(ArmorMaterial mat, Properties props, Type type) {
        super(mat, props, type);
        setArmorTexture(LibMisc.MOD_ID + ":textures/model/shooting_guardian_armor.png");
    }

    @SubscribeEvent
    public void onPlayerHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player player && hasArmorSet(player)) {
            float originalHealAmount = event.getAmount();
            float newHealAmount = originalHealAmount * 0.1f; // 恢复速度降低
            event.setAmount(newHealAmount);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID(stack.getItem().hashCode() + slot.toString().hashCode(), 0);
        if (slot == getEquipmentSlot()) {
            attributes = HashMultimap.create(attributes);
            attributes.put(Attributes.FLYING_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier flying speed" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            attributes.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier speed" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, "Shooting Guardian modifier attack damage" + type, 0.10F, AttributeModifier.Operation.MULTIPLY_BASE));
            attributes.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "Shooting Guardian modifier attack speed" + type, 0.03F, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return attributes;
    }

    private static final Supplier<ItemStack[]> armorSet = Suppliers.memoize(() -> new ItemStack[]{
            new ItemStack(ExtraBotanyItems.SHOOTING_GUARDIAN_HELM.get()),
            new ItemStack(ExtraBotanyItems.SHOOTING_GUARDIAN_CHEST.get()),
            new ItemStack(ExtraBotanyItems.SHOOTING_GUARDIAN_LEGS.get()),
            new ItemStack(ExtraBotanyItems.SHOOTING_GUARDIAN_BOOTS.get())
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
            case HEAD -> stack.is(ExtraBotanyItems.SHOOTING_GUARDIAN_HELM.get());
            case CHEST -> stack.is(ExtraBotanyItems.SHOOTING_GUARDIAN_CHEST.get());
            case LEGS -> stack.is(ExtraBotanyItems.SHOOTING_GUARDIAN_LEGS.get());
            case FEET -> stack.is(ExtraBotanyItems.SHOOTING_GUARDIAN_BOOTS.get());
            default -> false;
        };
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.shooting_guardian.name");
    }

    @Override
    public void addArmorSetDescription(ItemStack stack, List<Component> list, Player player) {
        list.add(Component.translatable("extrabotany.armorset.shooting_guardian.desc0").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shooting_guardian.desc1").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shooting_guardian.desc2").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("extrabotany.armorset.shooting_guardian.desc3").withStyle(ChatFormatting.GRAY));
    }
}
