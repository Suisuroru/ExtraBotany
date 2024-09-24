package io.grasspow.extrabotany.common.block;

import io.grasspow.extrabotany.common.block.block_entity.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.helper.InventoryHelper;

public class PedestalBlock extends BaseEntityBlock {
    public PedestalBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final VoxelShape TOP = Block.box(2, 12, 2, 14, 20, 14);
    private static final VoxelShape MIDDLE = Block.box(4, 4, 4, 12, 12, 12);
    private static final VoxelShape BOTTOM_TOP = Block.box(2, 2, 2, 14, 20, 14);
    private static final VoxelShape BOTTOM_END = Block.box(0, 0, 0, 16, 2, 16);
    private static final VoxelShape SHAPE = Shapes.join(TOP, Shapes.join(MIDDLE, Shapes.join(BOTTOM_TOP, BOTTOM_END, BooleanOp.OR), BooleanOp.OR), BooleanOp.OR);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
//        must return MODEL to render custom model
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal)) {
            return InteractionResult.PASS;
        }
        ItemStack heldStack = player.getItemInHand(hand);
        if (pedestal.isEmpty()) {
            if (heldStack.isEmpty()) {
                return InteractionResult.PASS;
            } else if (pedestal.addItem(player, heldStack)) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLOW_ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 0.8F);
                return InteractionResult.SUCCESS;
            }
        } else if (!heldStack.isEmpty()) {
            if (pedestal.processContainItem(heldStack, player)) {
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(pedestal);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.CONSUME;
        } else if (hand.equals(InteractionHand.MAIN_HAND)) {
            InventoryHelper.withdrawFromInventory(pedestal, player);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(pedestal);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.GLOW_ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.25F, 0.5F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
