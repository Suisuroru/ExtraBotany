package io.grasspow.extrabotany.client.handler;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Map;
import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class MiscellaneousModels {

    public static final MiscellaneousModels INSTANCE = new MiscellaneousModels();
    public boolean registeredModels = false;
    public BakedModel influxWaverProjectileModel;
    public BakedModel trueShadowKatanaProjectileModel;
    public BakedModel trueTerraBladeProjectile;

    public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {
        consumer.accept(resId("icon/influx_waver_projectile"));
        consumer.accept(resId("icon/true_shadow_katana_projectile"));
        consumer.accept(resId("icon/true_terra_blade_projectile"));
        if (!registeredModels) {
            registeredModels = true;
        }
    }

    public void onModelBake(ModelBakery loader, Map<ResourceLocation, BakedModel> map) {
        if (!registeredModels) {
            ExtraBotanyAPI.logger.error("Additional models failed to register! Aborting baking models to avoid early crashing.");
            return;
        }
        influxWaverProjectileModel = map.get(resId("icon/influx_waver_projectile"));
        trueShadowKatanaProjectileModel = map.get(resId("icon/true_shadow_katana_projectile"));
        trueTerraBladeProjectile = map.get(resId("icon/true_terra_blade_projectile"));
    }

    private MiscellaneousModels() {
    }
}
