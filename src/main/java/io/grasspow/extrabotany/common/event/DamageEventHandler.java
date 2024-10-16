package io.grasspow.extrabotany.common.event;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.handler.EquipmentHandler;

import static io.grasspow.extrabotany.common.handler.DamageHandler.checkPassable;

@Mod.EventBusSubscriber
public class DamageEventHandler {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!checkPassable(event.getEntity(), event.getSource().getEntity()) && !event.isCanceled()) {
            event.setCanceled(true);
        }
    }
}
