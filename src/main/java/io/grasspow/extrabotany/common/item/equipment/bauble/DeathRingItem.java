package io.grasspow.extrabotany.common.item.equipment.bauble;

import io.grasspow.extrabotany.common.event.DamageEventHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.mana.ManaItemHandler;

public class DeathRingItem extends BaubleItem {

    public DeathRingItem(Properties props) {
        super(props);
    }

    private static final int RANGE = 6;
    private static final int MANA_PER_DAMAGE = 80;

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(player.getOnPos().offset(-RANGE, -RANGE, -RANGE), entity.getOnPos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                    if (((ServerPlayer) player).getCamera() != living
                            && living != player
                            && DamageEventHandler.checkPassable(living, player.damageSources().magic())
                            && ManaItemHandler.instance().requestManaExactForTool(stack, player, MANA_PER_DAMAGE, true)
                            && player.tickCount % 30 == 0) {
                        living.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1));
                        living.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 60, 1));
                        living.hurt(player.damageSources().magic(), 0.5F);
                    }
                }
            }
        }
    }
}
