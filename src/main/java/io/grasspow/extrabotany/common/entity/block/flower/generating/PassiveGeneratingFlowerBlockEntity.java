package io.grasspow.extrabotany.common.entity.block.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;

public abstract class PassiveGeneratingFlowerBlockEntity extends GeneratingFlowerBlockEntity {
    private int decayTime;
    private int passiveDecayTicks = 0;

    public void setDecayTime(int decayTime) {
        this.decayTime = decayTime;
    }

    public PassiveGeneratingFlowerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if (!getLevel().isClientSide) {
            if (++passiveDecayTicks > decayTime) {
                getLevel().destroyBlock(getBlockPos(), false);
                if (Blocks.DEAD_BUSH.defaultBlockState().canSurvive(getLevel(), getBlockPos())) {
                    getLevel().setBlockAndUpdate(getBlockPos(), Blocks.DEAD_BUSH.defaultBlockState());
                }
            }
        }
    }
}
