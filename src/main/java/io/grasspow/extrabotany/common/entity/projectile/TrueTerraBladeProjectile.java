package io.grasspow.extrabotany.common.entity.projectile;

import io.grasspow.extrabotany.client.handler.MiscellaneousModels;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.client.fx.WispParticleData;

import java.util.List;

import static io.grasspow.extrabotany.common.libs.CommonHelper.getFilteredEntities;

public class TrueTerraBladeProjectile extends BaseSwordProjectile {
    public TrueTerraBladeProjectile(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public TrueTerraBladeProjectile(Level level, LivingEntity thrower, float damageTime) {
        super(ExtraBotanyEntities.TRUE_TERRA_BLADE_PROJECTILE.get(), level, thrower, damageTime);
    }

    @Override
    public void tick() {
        super.tick();
        if (getDeltaMovement().equals(Vec3.ZERO))
            return;
        if (level().isClientSide && tickCount % 2 == 0) {
            WispParticleData data = WispParticleData.wisp(0.3F, 0.1F, 0.95F, 0.1F, 1F);
            ClientProxy.INSTANCE.addParticleForceNear(level(), data, getX(), getY(), getZ(), 0, 0, 0);
        }
        if (!level().isClientSide) {
            AABB axis = new AABB(getX(), getY(), getZ(), xOld, yOld, zOld).inflate(2);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            List<LivingEntity> list = getFilteredEntities(entities, getOwner());
            for (LivingEntity living : list) {
                if (getOwner() != null) {
                    living.hurt(getOwner().damageSources().mobProjectile(this, (LivingEntity) getOwner()), 9F * damageTime);
                    if (attackedEntities != null && !attackedEntities.contains(living)) {
                        living.hurt(getOwner().damageSources().magic(), 2 * damageTime);
                        attackedEntities.add(living);
                        tickCount += 20;
                    }
                }

                if (tickCount > getLifeTicks()) {
                    discard();
                    break;
                }
            }
        }
    }

    @Override
    public int getLifeTicks() {
        return 60;
    }

    @Override
    public int getTickPerBlock() {
        return 10;
    }

    @Override
    public int getTickBreakBlockCap() {
        return 30;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BakedModel getIcon() {
        return MiscellaneousModels.INSTANCE.trueTerraBladeProjectileModel;
    }
}
