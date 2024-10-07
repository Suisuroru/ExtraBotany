package io.grasspow.extrabotany.api;

import io.grasspow.extrabotany.common.impl.ExtraBotanyApiImpl;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vazkii.botania.api.ServiceUtil;

public interface ExtraBotanyAPI {
    String MOD_ID = LibMisc.MOD_ID;
    Logger logger = LoggerFactory.getLogger(MOD_ID);
    ExtraBotanyAPI INSTANCE = ServiceUtil.findService(ExtraBotanyAPI.class, ExtraBotanyApiImpl::new);

    static ExtraBotanyAPI instance() {
        return INSTANCE;
    }

    default int apiVersion() {
        return 0;
    }

    float calcDamage(float v, Player player);
}
