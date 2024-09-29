/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package io.grasspow.extrabotany.common.item.brew;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.common.item.brew.BaseBrewItem;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseBrewItemEX extends BaseBrewItem {
    private static float multiplier;
    private static int amplifier;

    public BaseBrewItemEX(Properties builder, int swigs, int drinkSpeed, float multiplier, int amplifier, Supplier<Item> baseItem) {
        super(builder, swigs, drinkSpeed, baseItem);
        BaseBrewItemEX.multiplier = multiplier;
        BaseBrewItemEX.amplifier = amplifier;
    }

    @NotNull
    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, Level world, LivingEntity living) {
        if (!world.isClientSide) {
            for (MobEffectInstance effect : getBrew(stack).getPotionEffects(stack)) {
                MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), (int) (effect.getDuration() * multiplier), effect.getAmplifier() * amplifier, true, true);
                if (effect.getEffect().isInstantenous()) {
                    effect.getEffect().applyInstantenousEffect(living, living, living, newEffect.getAmplifier(), 1F);
                } else {
                    living.addEffect(newEffect);
                }
            }

            if (world.random.nextBoolean()) {
                world.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 1F, 1F);
            }

            int swigs = getSwigsLeft(stack);
            if (living instanceof Player player && !player.getAbilities().instabuild) {
                if (swigs == 1) {
                    ItemStack result = getBaseStack();
                    if (!player.getInventory().add(result)) {
                        return result;
                    } else {
                        return ItemStack.EMPTY;
                    }
                }

                setSwigsLeft(stack, swigs - 1);
            }
        }

        return stack;
    }

    // [VanillaCopy] PotionUtils.addPotionTooltip, with custom effect list
    public static void addPotionTooltip(List<MobEffectInstance> list, List<Component> lores, float durationFactor) {
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (list.isEmpty()) {
            lores.add((Component.translatable("effect.none")).withStyle(ChatFormatting.GRAY));
        } else {
            for (MobEffectInstance effectinstance : list) {
                MutableComponent iformattabletextcomponent = Component.translatable(effectinstance.getDescriptionId());
                MobEffect effect = effectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier() + amplifier, attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + effectinstance.getAmplifier()));
                }

                if (effectinstance.getDuration() > 20) {
                    iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(effectinstance, durationFactor * multiplier));
                }

                lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }

        if (!list1.isEmpty()) {
            lores.add(Component.empty());
            lores.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> pair : list1) {
                AttributeModifier attributemodifier2 = pair.getSecond();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    lores.add((Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    lores.add((Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
                }
            }
        }
    }
}
