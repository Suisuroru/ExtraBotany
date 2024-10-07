package io.grasspow.extrabotany.common.item.brew;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import io.grasspow.extrabotany.common.entity.item.brew.SplashGrenadeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.api.brew.BrewItem;
import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.CustomCreativeTabContents;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

/**
 * copy and edit from <a href="https://github.com/GoldChick/ExtraBotany">...</a>
 */
public class SplashGrenadeItem extends Item implements BrewItem, CustomCreativeTabContents {
    private static final String TAG_BREW_KEY = "brewKey";
    private static final float multiplier = 0.6F;


    public SplashGrenadeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            SplashGrenadeEntity sg = new SplashGrenadeEntity(level, player);
            sg.setItem(stack);
            sg.setPos(player.getX(), player.getY() + 1.1D, player.getZ());
            sg.shootFromRotation(player, player.getXRot(), player.getYRot(), -5.0F, 0.8F, 1.0F);
            level.addFreshEntity(sg);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.8F);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void addToCreativeTab(Item me, CreativeModeTab.Output output) {
        for (Brew brew : BotaniaAPI.instance().getBrewRegistry()) {
            if (brew == BotaniaBrews.fallbackBrew) {
                continue;
            }
            ItemStack stack = new ItemStack(this);
            setBrew(stack, brew);
            output.accept(stack);
        }
    }

    @Nonnull
    @Override
    public Component getName(@Nonnull ItemStack stack) {
        return Component.translatable(getDescriptionId(), Component.translatable(getBrew(stack).getTranslationKey(stack)));
    }

    @OnlyIn(Dist.CLIENT)
    public void addPotionTooltip(List<MobEffectInstance> list, List<Component> lores, float durationFactor) {
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
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + effectinstance.getAmplifier()));
                }

                if (effectinstance.getDuration() > 20) {
                    iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(new MobEffectInstance(effectinstance.getEffect(), (int) (effectinstance.getDuration() * multiplier)), durationFactor));
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        addPotionTooltip(getBrew(stack).getPotionEffects(stack), list, 1);
    }


    @Override
    public Brew getBrew(ItemStack stack) {
        String key = ItemNBTHelper.getString(stack, TAG_BREW_KEY, "");
        return BotaniaAPI.instance().getBrewRegistry().get(ResourceLocation.tryParse(key));
    }

    public static void setBrew(ItemStack stack, @Nullable Brew brew) {
        ResourceLocation id;
        if (brew != null) {
            id = BotaniaAPI.instance().getBrewRegistry().getKey(brew);
        } else {
            id = prefix("fallback");
        }
        setBrew(stack, id);
    }

    public static void setBrew(ItemStack stack, ResourceLocation brew) {
        ItemNBTHelper.setString(stack, TAG_BREW_KEY, brew.toString());
    }

    @Nonnull
    public static String getSubtype(ItemStack stack) {
        return stack.hasTag() ? ItemNBTHelper.getString(stack, TAG_BREW_KEY, "none") : "none";
    }
}
