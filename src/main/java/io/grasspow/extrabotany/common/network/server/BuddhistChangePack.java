package io.grasspow.extrabotany.common.network.server;

import io.grasspow.extrabotany.common.item.equipment.BuddhistRelicsItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import vazkii.botania.network.BotaniaPacket;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class BuddhistChangePack implements BotaniaPacket {
    public static final BuddhistChangePack INSTANCE = new BuddhistChangePack();
    public static final ResourceLocation ID = resId("bc");

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    public static BuddhistChangePack decode(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    public void handle(MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            if (!BuddhistRelicsItem.relicShift(player.getMainHandItem()).isEmpty())
                player.setItemSlot(EquipmentSlot.MAINHAND, BuddhistRelicsItem.relicShift(player.getMainHandItem()));
        });
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}
