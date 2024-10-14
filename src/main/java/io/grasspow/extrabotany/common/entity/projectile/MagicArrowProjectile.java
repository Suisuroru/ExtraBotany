package io.grasspow.extrabotany.common.entity.projectile;

import io.grasspow.extrabotany.common.handler.ConfigHandler;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.client.fx.WispParticleData;

import static io.grasspow.extrabotany.common.libs.CommonHelper.getFilteredEntities;


public class MagicArrowProjectile extends BaseProjectile {

    private static final String TAG_DAMAGE = "damage";
    private static final String TAG_LIFE = "life";

    private static final EntityDataAccessor<Integer> DAMAGE = SynchedEntityData.defineId(MagicArrowProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> LIFE = SynchedEntityData.defineId(MagicArrowProjectile.class, EntityDataSerializers.INT);

    public MagicArrowProjectile(EntityType<? extends MagicArrowProjectile> type, Level level) {
        super(type, level);
    }

    public MagicArrowProjectile(Level level, LivingEntity thrower) {
        this(ExtraBotanyEntities.MAGIC_ARROW_PROJECTILE.get(), level);
        setOwner(thrower);
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        var state = level().getBlockState(hitResult.getBlockPos());
        var hardness = state.getDestroySpeed(level(), hitResult.getBlockPos());
        var vec3 = getDeltaMovement();
        setDeltaMovement(Vec3.ZERO);
        if (ConfigHandler.COMMON.doProjectileBreakBlock.get() && hardness > 0 && !(state.getBlock() instanceof BeaconBlock)) {
            level().removeBlock(hitResult.getBlockPos(), false);
            level().levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, hitResult.getBlockPos(), Block.getId(state));
            tickCount += 2;
            setDeltaMovement(vec3);
        }
        super.onHitBlock(hitResult);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DAMAGE, 0);
        entityData.define(LIFE, 0);
    }


    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide && (getOwner() == null || !(getOwner() instanceof Player) || getOwner().isRemoved())) {
            discard();
            return;
        }

        if (level().isClientSide) {
            WispParticleData data = WispParticleData.wisp(0.5F, 0.1F, 0.85F, 0.1F, 1F);
            ClientProxy.INSTANCE.addParticleForceNear(level(), data, getX(), getY(), getZ(), 0, 0, 0);
        }

        Player player = (Player) getOwner();
        if (!level().isClientSide) {
            AABB axis = new AABB(getX() - 2F, getY() - 2F, getZ() - 2F, xOld + 2F, yOld + 2F, zOld + 2F);
            var entities = level().getEntitiesOfClass(LivingEntity.class, axis);
            var livings = getFilteredEntities(entities, player);
            for (LivingEntity living : livings) {
                living.hurt(player.damageSources().magic(), getDamage());
            }
        }

        if (tickCount > getLife())
            discard();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag cmp) {
        super.addAdditionalSaveData(cmp);
        cmp.putInt(TAG_LIFE, getLife());
        cmp.putInt(TAG_DAMAGE, getDamage());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag cmp) {
        super.readAdditionalSaveData(cmp);
        setLife(cmp.getInt(TAG_LIFE));
        setDamage(cmp.getInt(TAG_DAMAGE));
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public int getLife() {
        return entityData.get(LIFE);
    }

    public void setLife(int delay) {
        entityData.set(LIFE, delay);
    }

    public int getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(int delay) {
        entityData.set(DAMAGE, delay);
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
