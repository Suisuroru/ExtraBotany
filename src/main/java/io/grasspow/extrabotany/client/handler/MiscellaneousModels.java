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
    public BakedModel trueTerraBladeProjectileModel;
    public BakedModel[] firstFractalWeaponModels = new BakedModel[10];
    public BakedModel[] coreGodWingsModel = new BakedModel[4];
    public BakedModel coreGodModel;
    ;

    public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {
        consumer.accept(resId("icon/influx_waver_projectile"));
        consumer.accept(resId("icon/true_shadow_katana_projectile"));
        consumer.accept(resId("icon/true_terra_blade_projectile"));
        for (int i = 0; i < 10; i++) {
            consumer.accept(resId("icon/sword_domain_" + i));
        }
        for (int i = 0; i < 4; i++) {
            consumer.accept(resId("icon/wing_" + i));
        }
        consumer.accept(resId("icon/wing_core_god"));
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
        trueTerraBladeProjectileModel = map.get(resId("icon/true_terra_blade_projectile"));
        for (int i = 0; i < firstFractalWeaponModels.length; i++) {
            firstFractalWeaponModels[i] = map.get(resId("icon/sword_domain_" + i));
        }
        for (int i = 0; i < coreGodWingsModel.length; i++) {
            coreGodWingsModel[i] = map.get(resId("icon/wing_" + i));
        }
        coreGodModel = map.get(resId("icon/wing_core_god"));
    }

    private MiscellaneousModels() {
    }
}
