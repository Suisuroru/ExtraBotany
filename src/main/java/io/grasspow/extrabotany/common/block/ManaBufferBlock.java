package io.grasspow.extrabotany.common.block;

import io.grasspow.extrabotany.common.entity.block.ManaBufferBlockEntity;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.BotaniaWaterloggedBlock;

public class ManaBufferBlock extends BotaniaWaterloggedBlock implements EntityBlock {
    public enum Variant {
        DEFAULT,
        QUANTUM
    }

    public final Variant variant;

    public ManaBufferBlock(Variant v, Properties builder) {
        super(builder);
        this.variant = v;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ManaBufferBlockEntity(pPos, pState, variant);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return variant.equals(Variant.DEFAULT) ? Shapes.MANA_BUFFER : Shapes.QUANTUM_MANA_BUFFER;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return createTickerHelper(type, ExtraBotanyEntities.Blocks.MANA_BUFFER_BLOCK_ENTITY.get(), ManaBufferBlockEntity::serverTick);
    }
}
