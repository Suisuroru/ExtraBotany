package io.grasspow.extrabotany.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ExtraBotanyCapabilities {

    public static final Capability<INatureOrb> NATURE_ORB = CapabilityManager.get(new CapabilityToken<>() {
    });
}
