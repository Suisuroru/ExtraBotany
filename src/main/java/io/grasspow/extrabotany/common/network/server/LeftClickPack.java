package io.grasspow.extrabotany.common.network.server;

import io.grasspow.extrabotany.common.item.equipment.bauble.JingweiFeatherItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.network.BotaniaPacket;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class LeftClickPack implements BotaniaPacket {
    public static final LeftClickPack INSTANCE = new LeftClickPack();
    public static final ResourceLocation ID = resId("lc");

    public void handle(MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            ItemStack stack = EquipmentHandler.findOrEmpty(s -> s.getItem() instanceof JingweiFeatherItem, player);
            if (!stack.isEmpty()) {
                ((JingweiFeatherItem) (stack.getItem())).onLeftClick(player, null);
            }
        });
    }

    @Override
    public void encode(FriendlyByteBuf buf) {

    }

    public static LeftClickPack decode(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}
