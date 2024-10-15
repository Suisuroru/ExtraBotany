package io.grasspow.extrabotany.api;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.function.Supplier;

public enum ExtraBotanyArmorMaterial implements ArmorMaterial {
    MIKU("miku", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 4);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.HELMET, 1);
    }), 22, () -> SoundEvents.ARMOR_EQUIP_LEATHER, ExtraBotanyItems.MANA_DRINK::get, 0),
    MAID("maid", 40, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 7);
        map.put(ArmorItem.Type.LEGGINGS, 9);
        map.put(ArmorItem.Type.HELMET, 4);
    }), 32, () -> SoundEvents.ARMOR_EQUIP_DIAMOND, ExtraBotanyItems.GOLD_CLOTH::get, 3),
    GOBLINS_LAYER("goblins_layer", 27, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.LEGGINGS, 7);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 30, () -> SoundEvents.ARMOR_EQUIP_IRON, ExtraBotanyItems.PHOTONIUM::get, 1),
    SHADOW_WARRIOR("shadow_warrior", 24, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 5);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 26, () -> SoundEvents.ARMOR_EQUIP_IRON, ExtraBotanyItems.SHADOWIUM::get, 1),
    SHOOTING_GUARDIAN("shooting_guardian", 34, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 7);
        map.put(ArmorItem.Type.LEGGINGS, 8);
        map.put(ArmorItem.Type.HELMET, 9);
    }), 34, () -> SoundEvents.ARMOR_EQUIP_IRON, ExtraBotanyItems.ORICHALCOS::get, 2),
    SILENT_SAGES("silent_sages", 50, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 4);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.LEGGINGS, 9);
        map.put(ArmorItem.Type.HELMET, 5);
    }), 40, () -> SoundEvents.ARMOR_EQUIP_IRON, ExtraBotanyItems.ORICHALCOS::get, 3);

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantability;
    private final Supplier<SoundEvent> equipSound;
    private final Supplier<Item> repairItem;
    private final float toughness;

    ExtraBotanyArmorMaterial(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType, int enchantability, Supplier<SoundEvent> equipSound, Supplier<Item> repairItem, float toughness) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = pProtectionFunctionForType;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.repairItem = repairItem;
        this.toughness = toughness;
    }

    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound.get();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(repairItem.get());
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
