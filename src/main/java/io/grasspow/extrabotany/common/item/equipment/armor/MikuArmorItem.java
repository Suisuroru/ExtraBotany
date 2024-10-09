/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package io.grasspow.extrabotany.common.item.equipment.armor;

import com.google.common.base.Suppliers;
import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.item.PhantomInkable;
import vazkii.botania.api.mana.ManaDiscountArmor;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.gui.TooltipHandler;
import vazkii.botania.client.lib.ResourcesLib;
import vazkii.botania.common.annotations.SoftImplement;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.proxy.Proxy;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MikuArmorItem extends ArmorItem implements CustomDamageItem, PhantomInkable, ManaDiscountArmor {

    private static final String TAG_PHANTOM_INK = "phantomInk";

    public final Type type;
    private String armorTexture;

    public void setArmorTexture(String armorTexture) {
        this.armorTexture = armorTexture;
    }

    public MikuArmorItem(Properties props, Type type) {
        this(ExtraBotanyAPI.instance().getMikuArmorMaterial(), props, type);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttacked);
    }

    public MikuArmorItem(ArmorMaterial mat, Properties props, Type type) {
        super(mat, type, props);
        setArmorTexture(LibMisc.MOD_ID + ":textures/model/miku_armor.png");
        this.type = type;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    private void onPlayerAttacked(LivingHurtEvent event) {
        Entity target = event.getEntity();
        if (target instanceof Player player) {
            if (hasArmorSet(player) && getEquipmentSlot() == EquipmentSlot.HEAD) {
                if (event.getSource().equals(player.damageSources().magic())) {
                    event.setAmount(event.getAmount() * 0.25F);
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (entity instanceof Player player) {
            if (!level.isClientSide && stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExact(stack, player, getManaPerDamage() * 2, true)) {
                stack.setDamageValue(stack.getDamageValue() - 1);
            }
        }
    }

    @SoftImplement("IForgeItem")
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (!level.isClientSide && stack.getDamageValue() > 0 && ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerDamage() * 2, true)) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
        return hasArmorSet(player) ? 0.15F : 0;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, getManaPerDamage());
    }

    protected int getManaPerDamage() {
        return 140;
    }

    @SoftImplement("IForgeItem")
    @NotNull
    public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return hasPhantomInk(stack) ? armorTexture : getArmorTextureAfterInk(stack, slot);
    }

    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return ResourcesLib.MODEL_INVISIBLE_ARMOR;
    }

    private static final Supplier<ItemStack[]> armorSet = Suppliers.memoize(() -> new ItemStack[]{
            new ItemStack(ExtraBotanyItems.MIKU_HELM.get()),
            new ItemStack(ExtraBotanyItems.MIKU_CHEST.get()),
            new ItemStack(ExtraBotanyItems.MIKU_LEGS.get()),
            new ItemStack(ExtraBotanyItems.MIKU_BOOTS.get())
    });

    public ItemStack[] getArmorSetStacks() {
        return armorSet.get();
    }

    public boolean hasArmorSet(Player player) {
        return hasArmorSetItem(player, EquipmentSlot.HEAD) && hasArmorSetItem(player, EquipmentSlot.CHEST) && hasArmorSetItem(player, EquipmentSlot.LEGS) && hasArmorSetItem(player, EquipmentSlot.FEET);
    }

    public boolean hasArmorSetItem(Player player, EquipmentSlot slot) {
        if (player == null) {
            return false;
        }

        ItemStack stack = player.getItemBySlot(slot);
        if (stack.isEmpty()) {
            return false;
        }

        return switch (slot) {
            case HEAD -> stack.is(ExtraBotanyItems.MIKU_HELM.get());
            case CHEST -> stack.is(ExtraBotanyItems.MIKU_CHEST.get());
            case LEGS -> stack.is(ExtraBotanyItems.MIKU_LEGS.get());
            case FEET -> stack.is(ExtraBotanyItems.MIKU_BOOTS.get());
            default -> false;
        };

    }

    private int getSetPiecesEquipped(Player player) {
        int pieces = 0;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR && hasArmorSetItem(player, slot)) {
                pieces++;
            }
        }

        return pieces;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        TooltipHandler.addOnShift(list, () -> addInformationAfterShift(stack, world, list, flags));
    }

    private Component getArmorSetTitle(Player player) {
        Component end = getArmorSetName()
                .append(" (" + getSetPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")")
                .withStyle(ChatFormatting.GRAY);
        return Component.translatable("botaniamisc.armorset")
                .append(" ")
                .append(end);
    }

    public void addInformationAfterShift(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        Player player = Proxy.INSTANCE.getClientPlayer();
        list.add(getArmorSetTitle(player));
        addArmorSetDescription(stack, list, player);
        ItemStack[] stacks = getArmorSetStacks();
        for (ItemStack armor : stacks) {
            MutableComponent cmp = Component.literal(" - ").append(armor.getHoverName());
            EquipmentSlot slot = ((ArmorItem) armor.getItem()).getEquipmentSlot();
            cmp.withStyle(hasArmorSetItem(player, slot) ? ChatFormatting.GREEN : ChatFormatting.GRAY);
            list.add(cmp);
        }
        if (hasPhantomInk(stack)) {
            list.add(Component.translatable("botaniamisc.hasPhantomInk").withStyle(ChatFormatting.GRAY));
        }
    }

    public MutableComponent getArmorSetName() {
        return Component.translatable("extrabotany.armorset.miku.name");
    }

    public void addArmorSetDescription(ItemStack stack, List<Component> list, Player player) {
        list.add(Component.translatable("extrabotany.armorset.mana.desc").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean hasPhantomInk(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_PHANTOM_INK, false);
    }

    @Override
    public void setPhantomInk(ItemStack stack, boolean ink) {
        ItemNBTHelper.setBoolean(stack, TAG_PHANTOM_INK, ink);
    }
}
