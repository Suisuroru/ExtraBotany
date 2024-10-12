package io.grasspow.extrabotany.common.network.server;

import io.grasspow.extrabotany.api.item.IItemWithLeftClick;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.network.BotaniaPacket;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public record LeftClickPack(ItemStack stack) implements BotaniaPacket {
    public static final ResourceLocation ID = resId("lc");

    public void handle(MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            if (!stack.isEmpty()) {
                ((IItemWithLeftClick) (stack.getItem())).onLeftClick(player, null);
            }
        });
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeItem(stack);
    }

    public static LeftClickPack decode(FriendlyByteBuf buf) {
        return new LeftClickPack(buf.readItem());
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }
}
