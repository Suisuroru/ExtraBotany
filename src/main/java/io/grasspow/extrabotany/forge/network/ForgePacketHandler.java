package io.grasspow.extrabotany.forge.network;

import io.grasspow.extrabotany.common.network.LeftClickPack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import vazkii.botania.network.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class ForgePacketHandler {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            resId("main"),
            // We don't use this
            () -> "0",
            "0"::equals,
            "0"::equals);

    public static void init() {
        int i = 0;
        // Serverbound
        CHANNEL.registerMessage(i++, LeftClickPack.class, LeftClickPack::encode, LeftClickPack::decode,
                makeServerBoundHandler(LeftClickPack::handle));

        // Clientbound
//        CHANNEL.registerMessage(i++, LeftClickPack.class, LeftClickPack::encode, LeftClickPack::decode,
//                makeClientBoundHandler(LeftClickPack.Handler::handle));
    }

    private static <T> BiConsumer<T, Supplier<NetworkEvent.Context>> makeServerBoundHandler(TriConsumer<T, MinecraftServer, ServerPlayer> handler) {
        return (m, ctx) -> {
            handler.accept(m, ctx.get().getSender().getServer(), ctx.get().getSender());
            ctx.get().setPacketHandled(true);
        };
    }

    private static <T> BiConsumer<T, Supplier<NetworkEvent.Context>> makeClientBoundHandler(Consumer<T> consumer) {
        return (m, ctx) -> {
            consumer.accept(m);
            ctx.get().setPacketHandled(true);
        };
    }
}
