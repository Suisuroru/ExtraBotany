package io.grasspow.extrabotany.api;

import io.grasspow.extrabotany.common.libs.LibMisc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vazkii.botania.api.ServiceUtil;

public interface ExtraBotanyAPI {
    String MOD_ID = LibMisc.MOD_ID;
    Logger logger = LogManager.getLogger(MOD_ID);
    ExtraBotanyAPI INSTANCE = ServiceUtil.findService(ExtraBotanyAPI.class, () -> new ExtraBotanyAPI() {
    });

    static ExtraBotanyAPI instance() {
        return INSTANCE;
    }

    default int apiVersion() {
        return 0;
    }
}
