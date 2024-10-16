package io.grasspow.extrabotany.common.entity.projectile;

import io.grasspow.extrabotany.client.handler.MiscellaneousModels;
import io.grasspow.extrabotany.common.handler.DamageHandler;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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

        if (this.tickCount >= getLifeTicks())
            discard();

        if (!level().isClientSide && (getOwner() == null || getOwner().isRemoved())) {
            discard();
            return;
        }

        super.tick();

        if (level().isClientSide && tickCount % 2 == 0) {
            WispParticleData data = WispParticleData.wisp(0.3F, 0.1F, 0.95F, 0.1F, 1F);
            ClientProxy.INSTANCE.addParticleForceNear(level(), data, getX(), getY(), getZ(), 0, 0, 0);
        }
        if (!level().isClientSide) {
            AABB axis = new AABB(getX(), getY(), getZ(), xOld, yOld, zOld).inflate(2);
            List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            List<LivingEntity> list = getFilteredEntities(entities, getOwner());
            for (LivingEntity living : list) {
                if (getOwner() instanceof Player) {
                    DamageHandler.INSTANCE.dmg(living, getOwner(), 11F, DamageHandler.INSTANCE.GENERAL);
                } else {
                    if (living.invulnerableTime == 0)
                        DamageHandler.INSTANCE.dmg(living, getOwner(), 2.5F, DamageHandler.INSTANCE.LIFE_LOSING);
                    DamageHandler.INSTANCE.dmg(living, getOwner(), 7F, DamageHandler.INSTANCE.MAGIC);
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
