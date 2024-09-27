package io.grasspow.extrabotany.xplat;

import vazkii.botania.api.ServiceUtil;

public interface ModXplatAbstractions {
    // Yes, this forms a loop by default. Each loader overrides their own to break the loop
    default boolean isFabric() {
        return !isForge();
    }

    default boolean isForge() {
        return !isFabric();
    }

    boolean isModLoaded(String modId);

    boolean isDevEnvironment();

    boolean isPhysicalClient();

    String getExtraBotanyVersion();

    ModXplatAbstractions INSTANCE = ServiceUtil.findService(ModXplatAbstractions.class, null);
}
