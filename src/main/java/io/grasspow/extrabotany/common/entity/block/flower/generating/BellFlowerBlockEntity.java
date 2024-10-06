package io.grasspow.extrabotany.common.entity.block.flower.generating;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class BellFlowerBlockEntity extends GeneratingFlowerBlockEntity {

    private static final int RANGE = 0;

    public BellFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.Flowers.BELL_FLOWER.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        int baseGen = 10;
        int baseY = 90;
        int y = this.getEffectivePos().getY();

        if (getLevel().canSeeSky(this.getEffectivePos()) && y > baseY) {
            int rain = getLevel().isRaining() ? 3 : 0;
            int gen = (baseGen + rain) * y / baseY;
            if (this.ticksExisted % 10 == 0)
                addMana(gen);
        }
    }

    @Override
    public int getMaxMana() {
        return 300;
    }

    @Override
    public int getColor() {
        return 0xFFFF99;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }
}
