package io.grasspow.extrabotany.forge.xplat.client;

import io.grasspow.extrabotany.forge.network.ForgePacketHandler;
import io.grasspow.extrabotany.xplat.client.ClientModXplatAbstractions;
import vazkii.botania.network.BotaniaPacket;

public class ClientModXplatImpl implements ClientModXplatAbstractions {
    @Override
    public void sendToServer(BotaniaPacket packet) {
        ForgePacketHandler.CHANNEL.sendToServer(packet);
    }
}
