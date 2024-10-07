package io.grasspow.extrabotany.common.event;

import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.handler.EquipmentHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber
public class KeyInputEventHandler {
    private static final Method method;

    static {
        try {
            method = Minecraft.class.getDeclaredMethod("startAttack");//m_202354_
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

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
                    try {
                        method.invoke(mc);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


}

