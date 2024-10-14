package io.grasspow.extrabotany.common.libs;

import io.grasspow.extrabotany.api.capability.INatureOrb;
import io.grasspow.extrabotany.common.item.equipment.armor.MaidArmorHelmetItem;
import io.grasspow.extrabotany.xplat.ModXplatAbstractions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.grasspow.extrabotany.common.event.DamageEventHandler.checkPassable;

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
        INatureOrb orb = ModXplatAbstractions.INSTANCE.findNatureOrbItem(stack);
        if (orb == null) {
        }
        potionsToRemove.forEach(potion -> {
            if (stack.getItem() instanceof MaidArmorHelmetItem || orb != null && orb.addNature(-50)) {
                player.removeEffect(potion);
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
        });
    }

    public static List<LivingEntity> getFilteredEntities(List<LivingEntity> entities, Entity source) {
        List<LivingEntity> list = entities.stream().filter((living) -> checkPassable(living, source) && !living.isRemoved()).collect(Collectors.toList());
        return list;
    }


}
