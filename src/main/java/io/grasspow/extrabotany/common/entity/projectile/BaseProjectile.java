package io.grasspow.extrabotany.common.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;

public class BaseProjectile extends ThrowableProjectile {

    public BaseProjectile(EntityType<? extends ThrowableProjectile> type, LivingEntity entity, Level level) {
        super(type, entity, level);
        this.setNoGravity(true);
    }

    public BaseProjectile(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    protected void defineSynchedData() {
    }
}
