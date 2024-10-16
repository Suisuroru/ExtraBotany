package io.grasspow.extrabotany.common.entity.projectile;


import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.common.handler.DamageHandler;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class AuraFireProjectile extends BaseProjectile {
    public AuraFireProjectile(EntityType<? extends BaseProjectile> type, Level level) {
        super(type, level);
    }

    public AuraFireProjectile(Level level, LivingEntity thrower) {
        super(ExtraBotanyEntities.AURA_FILE.get(), thrower, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 80)
            this.kill();
        if (this.level().isClientSide())
            for (int i = 0; i < 5; i++)
                this.level().addParticle(ParticleTypes.FLAME, this.getX() + Math.random() * 0.4F - 0.2F,
                        this.getY() + Math.random() * 0.4F - 0.2F, this.getZ() + Math.random() * 0.4F - 0.2F, 0, 0, 0);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (getOwner() instanceof Player player) {
            if (result.getEntity() != player) {
                float dmg = ExtraBotanyAPI.instance().calcDamage(5F, player);
                DamageHandler.INSTANCE.dmg(result.getEntity(), player, dmg, DamageHandler.INSTANCE.GENERAL_PIERCING);
                player.setAbsorptionAmount(Math.min(10, player.getAbsorptionAmount() + 1F));
                this.kill();
            }
        }
    }

}
