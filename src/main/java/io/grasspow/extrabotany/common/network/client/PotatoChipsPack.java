package io.grasspow.extrabotany.common.network.client;

import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.network.BotaniaPacket;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public record PotatoChipsPack() implements BotaniaPacket {
    public static final ResourceLocation ID = resId("pc");

    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    public static PotatoChipsPack decode(FriendlyByteBuf buf) {
        return new PotatoChipsPack();
    }

    @Override
    public ResourceLocation getFabricId() {
        return ID;
    }

    public static class Handler {
        public static void handle(PotatoChipsPack packet) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ExtraBotanyItems.POTATO_CHIPS.get()));
        }
    }
}
