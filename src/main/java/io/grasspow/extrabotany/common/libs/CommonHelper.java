package io.grasspow.extrabotany.common.libs;

import io.grasspow.extrabotany.common.item.equipment.bauble.NatureOrbItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Map;

public class CommonHelper {

    public static ResourceLocation resId(String path) {
        return new ResourceLocation(LibMisc.MOD_ID, path);
    }

    public static void clearPotions(ItemStack stack, Player player) {
        List<MobEffect> potionsToRemove = player.getActiveEffectsMap().entrySet().stream()
                .filter(effect -> effect.getKey().getCategory() == MobEffectCategory.HARMFUL
                        && effect.getValue().isCurativeItem(new ItemStack(Items.MILK_BUCKET)))
                .map(Map.Entry::getKey)
                .distinct()
                .toList();

        potionsToRemove.forEach(potion -> {
            player.removeEffect(potion);
            NatureOrbItem.addXP(stack, -50);
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        });
    }
}
