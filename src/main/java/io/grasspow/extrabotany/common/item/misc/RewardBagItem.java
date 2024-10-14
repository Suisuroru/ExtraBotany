package io.grasspow.extrabotany.common.item.misc;

import io.grasspow.extrabotany.api.item.BonusHelper;
import io.grasspow.extrabotany.api.item.WeightCategory;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.item.BotaniaItems;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardBagItem extends Item {
    private static final Map<Variant, List<WeightCategory>> categoryMap = new HashMap<>();
    private Variant variant;

    public RewardBagItem(Properties prop, Variant variant) {
        super(prop);
        this.variant = variant;
    }

    public enum Variant {
        Eins, Zwei, Drei, Vier, _943
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack reward = BonusHelper.rollItem(player, categoryMap.get(variant));
        if (!reward.isEmpty() && !level.isClientSide) {
            ItemStack stack = reward.copy();
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
            player.drop(stack, true).setNoPickUpDelay();
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        DecimalFormat df = new DecimalFormat("0.00%");
        int sum = BonusHelper.sum(categoryMap.get(variant));
        for (WeightCategory category : categoryMap.get(variant)) {
            String percentage = df.format((float) category.getWeight() / sum);
            String stackname = Component.translatable(category.getCategory().getDescriptionId()).getString();
            int count = category.getCategory().getCount();
            ChatFormatting color = (float) category.getWeight() / sum <= 0.01F ? ChatFormatting.GOLD : ChatFormatting.RESET;
            list.add(Component.literal(String.format("%s x%d %s", stackname, count, percentage)).withStyle(color));
        }
    }

    public static void initCategoryMap() {
        List<WeightCategory> categoryListA = new ArrayList<>();
        List<WeightCategory> categoryListB = new ArrayList<>();
        List<WeightCategory> categoryListC = new ArrayList<>();
        List<WeightCategory> categoryListD = new ArrayList<>();
        List<WeightCategory> categoryList943 = new ArrayList<>();
        categoryListA.add(new WeightCategory(new ItemStack(ExtraBotanyItems.UNIVERSAL_PETAL.get(), 4), 10));
        categoryListA.add(new WeightCategory(new ItemStack(ExtraBotanyItems.UNIVERSAL_PETAL.get(), 8), 10));
        categoryListA.add(new WeightCategory(new ItemStack(ExtraBotanyItems.UNIVERSAL_PETAL.get(), 6), 30));

        categoryListB.add(new WeightCategory(new ItemStack(ExtraBotanyItems.ELEMENT_RUNE.get(), 2), 80));
        categoryListB.add(new WeightCategory(new ItemStack(ExtraBotanyItems.SIN_RUNE.get(), 1), 20));

        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.manaSteel, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.manaPearl, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.manaDiamond, 4), 15));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.elementium, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.pixieDust, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.dragonstone, 3), 11));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.manaPowder, 8), 10));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.terrasteel, 1), 9));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.lifeEssence, 4), 8));
        categoryListC.add(new WeightCategory(new ItemStack(BotaniaItems.gaiaIngot, 1), 7));
        categoryListC.add(new WeightCategory(new ItemStack(ExtraBotanyItems.HERO_MEDAL.get(), 1), 1));

        categoryListD.add(new WeightCategory(new ItemStack(Items.COAL, 6), 40));
        categoryListD.add(new WeightCategory(new ItemStack(Items.IRON_INGOT, 4), 36));
        categoryListD.add(new WeightCategory(new ItemStack(Items.GOLD_INGOT, 4), 24));
        categoryListD.add(new WeightCategory(new ItemStack(Items.REDSTONE, 8), 22));
        categoryListD.add(new WeightCategory(new ItemStack(Items.ENDER_PEARL, 4), 20));
        categoryListD.add(new WeightCategory(new ItemStack(Items.DIAMOND, 1), 18));
        categoryListD.add(new WeightCategory(new ItemStack(BotaniaItems.blackerLotus, 2), 16));
        categoryListD.add(new WeightCategory(new ItemStack(BotaniaItems.overgrowthSeed, 1), 12));
        categoryListD.add(new WeightCategory(new ItemStack(ExtraBotanyItems.BUDDHIST_RELICS.get()), 1));

        categoryList943.add(new WeightCategory(new ItemStack(ExtraBotanyItems.CHALLENGE_TICKET.get(), 1), 45));
        categoryList943.add(new WeightCategory(new ItemStack(ExtraBotanyItems.REWARD_BAG_A.get(), 16), 30));
        categoryList943.add(new WeightCategory(new ItemStack(ExtraBotanyItems.REWARD_BAG_B.get(), 10), 20));
        categoryList943.add(new WeightCategory(new ItemStack(BotaniaItems.lifeEssence, 4), 20));
        categoryList943.add(new WeightCategory(new ItemStack(BotaniaItems.gaiaIngot, 1), 14));
        categoryList943.add(new WeightCategory(new ItemStack(ExtraBotanyItems.REWARD_BAG_C.get(), 6), 10));
        categoryList943.add(new WeightCategory(new ItemStack(ExtraBotanyItems.REWARD_BAG_D.get(), 6), 10));
        categoryList943.add(new WeightCategory(new ItemStack(ExtraBotanyItems.HERO_MEDAL.get(), 1), 1));

        categoryMap.put(Variant.Eins, categoryListA);
        categoryMap.put(Variant.Zwei, categoryListB);
        categoryMap.put(Variant.Drei, categoryListC);
        categoryMap.put(Variant.Vier, categoryListD);
        categoryMap.put(Variant._943, categoryList943);
    }
}
