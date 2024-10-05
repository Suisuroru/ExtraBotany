package io.grasspow.extrabotany.common.entity.block.flower.generating;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MoonBlessBlockEntity extends SunBlessBlockEntity {

    public MoonBlessBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.Flowers.MOON_BLESS.get(), pos, state);
    }

    @Override
    public int getColor() {
        return 0xFFFF00;
    }

    @Override
    protected boolean canGeneratePassively() {
        return this.getLevel().isNight() && this.ticksExisted % 4 == 0;
    }

}
