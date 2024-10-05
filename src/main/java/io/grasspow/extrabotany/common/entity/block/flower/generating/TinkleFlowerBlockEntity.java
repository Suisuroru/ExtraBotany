package io.grasspow.extrabotany.common.entity.block.flower.generating;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class TinkleFlowerBlockEntity extends GeneratingFlowerBlockEntity {

    private static final int RANGE = 8;
    private static final String TAG_TIME = "time";
    private int time = 0;

    public TinkleFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.Flowers.TINKLE_FLOWER.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if (!getLevel().isClientSide() && ticksExisted % 20L == 0) {
            for (Player player : getLevel().getEntitiesOfClass(Player.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                double vx = player.getX() - player.xCloakO;
                double vy = player.getY() - player.yCloakO;
                double vz = player.getZ() - player.zCloakO;
                double vel = Math.sqrt(vx * vx + vy * vy + vz * vz);
                if (player.hasEffect(MobEffects.MOVEMENT_SPEED))
                    vel *= 1.2;

                time += Mth.clamp((int) (vel * 10.0), 0, 8);

                final int limit = 10;

                if (time >= limit) {
                    if (getMana() < getMaxMana())
                        addMana(30);
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 0, 1));
//                    AdvancementHandler.INSTANCE.grantAdvancement((ServerPlayerEntity) player, LibAdvancementNames.TINKLE_USE);
                    time %= limit;
                }
            }
        }

    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public int getColor() {
        return 0xCCFF00;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_TIME, time);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        time = cmp.getInt(TAG_TIME);
    }

}
