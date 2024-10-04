package io.grasspow.extrabotany.common.entity.block.flower;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class AnnoyingFlowerBlockEntity extends GeneratingFlowerBlockEntity {
    public AnnoyingFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.Flowers.ANNOYING_FLOWER.get(), pos, state);
    }

    @Override
    public int getMaxMana() {
        return 0;
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public @Nullable RadiusDescriptor getRadius() {
        return null;
    }
}
