package io.grasspow.extrabotany.common.entity.block.flower.generating;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class SunBlessBlockEntity extends PassiveLimitedGeneratingFlowerBlockEntity {

    private static final int RANGE = 0;

    public SunBlessBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setDecayTime(36000);
    }

    public SunBlessBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.Flowers.SUN_BLESS.get(), pos, state);
    }

    @Override
    public int getMaxMana() {
        return 200;
    }

    @Override
    protected int getValueForPassiveGeneration() {
        return 1;
    }

    @Override
    public int getColor() {
        return 0xFFA500;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

    @Override
    protected boolean canGeneratePassively() {
        return this.getLevel().isDay() && this.ticksExisted % 2 == 0;
    }

    @Override
    protected int getDelayBetweenPassiveGeneration() {
        return 2;
    }
}
