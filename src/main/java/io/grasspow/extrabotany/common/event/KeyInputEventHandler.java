package io.grasspow.extrabotany.common.event;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.item.equipment.BuddhistRelicsItem;
import io.grasspow.extrabotany.common.network.server.BuddhistChangePack;
import io.grasspow.extrabotany.xplat.client.ClientModXplatAbstractions;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import vazkii.botania.common.handler.EquipmentHandler;

@Mod.EventBusSubscriber
public class KeyInputEventHandler {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END && mc.player != null && mc.player.getAttackStrengthScale(0) == 1
                && mc.options.keyAttack.isDown()) {
            HitResult hitResult = mc.hitResult;
            if (!EquipmentHandler.findOrEmpty(ExtraBotanyItems.POWER_GLOVE.get(), mc.player).isEmpty() && hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityhitresult = (EntityHitResult) hitResult;
                Entity entity = entityhitresult.getEntity();
                if (entity.isAlive() && entity.isAttackable()) {
                    mc.startAttack();
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;
        if (event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_LEFT_CONTROL) {
            if (!BuddhistRelicsItem.relicShift(player.getMainHandItem()).isEmpty()) {
                ClientModXplatAbstractions.INSTANCE.sendToServer(BuddhistChangePack.INSTANCE);
            }
        }
    }

}

