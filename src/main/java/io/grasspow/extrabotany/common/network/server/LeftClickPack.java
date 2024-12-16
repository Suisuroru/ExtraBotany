package io.grasspow.extrabotany.common.network.server;

import io.grasspow.extrabotany.api.capability.IAdvancementRequirement;
import io.grasspow.extrabotany.api.item.IItemWithLeftClick;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.item.equipment.bauble.JingweiFeatherItem;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.network.BotaniaPacket;

import static io.grasspow.extrabotany.common.handler.AdvancementHandler.checkAdvancement;
import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public record LeftClickPack(ItemStack stack) implements BotaniaPacket {
    public static final ResourceLocation ID = resId("lc");

    public void handle(MinecraftServer server, ServerPlayer player) {
        server.execute(() -> {
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IAdvancementRequirement r) {
                    if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) return;
                }
                if (stack.getItem() instanceof IItemWithLeftClick click) {
                    click.onLeftClick(player, null);
                }
            }
            if (!EquipmentHandler.findOrEmpty(ExtraBotanyItems.JINGWEI_FEATHER.get(), player).isEmpty()) {
                ((JingweiFeatherItem) ExtraBotanyItems.JINGWEI_FEATHER.get()).onLeftClick(player, null);
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
