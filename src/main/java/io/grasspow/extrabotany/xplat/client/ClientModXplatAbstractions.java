package io.grasspow.extrabotany.xplat.client;

import io.grasspow.extrabotany.forge.client.ClientModXplatAbstractionsImpl;
import vazkii.botania.api.ServiceUtil;
import vazkii.botania.network.BotaniaPacket;

public interface ClientModXplatAbstractions {
    void sendToServer(BotaniaPacket packet);

    ClientModXplatAbstractions INSTANCE = ServiceUtil.findService(ClientModXplatAbstractions.class, ClientModXplatAbstractionsImpl::new);
}
