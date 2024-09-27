package io.grasspow.extrabotany.common.block;

import io.grasspow.extrabotany.common.entity.block.PedestalBlockEntity;
import io.grasspow.extrabotany.common.libs.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.BotaniaWaterloggedBlock;

public class PedestalBlock extends BotaniaWaterloggedBlock implements EntityBlock {
    public PedestalBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final VoxelShape TOP = Block.box(2, 12, 2, 14, 20, 14);
    private static final VoxelShape MIDDLE = Block.box(4, 4, 4, 12, 12, 12);
    private static final VoxelShape BOTTOM_TOP = Block.box(2, 2, 2, 14, 20, 14);
    private static final VoxelShape BOTTOM_END = Block.box(0, 0, 0, 16, 2, 16);
    private static final VoxelShape SHAPE = Shapes.or(TOP, MIDDLE, BOTTOM_TOP, BOTTOM_END);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
//      must return MODEL to render custom model
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PedestalBlockEntity(pPos, pState);
    }

    //todo: there is still a bug to fix,temporary put on hold.
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (tileEntity instanceof PedestalBlockEntity pedestal) {
            ItemStack heldStack = player.getItemInHand(hand);
            ItemStack offhandStack = player.getOffhandItem();
            if (pedestal.isEmpty() && !pedestal.processing) {
                if (!offhandStack.isEmpty()) {
                    if (hand.equals(InteractionHand.MAIN_HAND) && !offhandStack.is(ModTags.Items.PEDESTAL_DENY) && !(heldStack.getItem() instanceof BlockItem)) {
                        return InteractionResult.PASS; // Pass to off-hand if that item is placeable
                    }
                    if (hand.equals(InteractionHand.OFF_HAND) && offhandStack.is(ModTags.Items.PEDESTAL_DENY)) {
                        return InteractionResult.PASS; // Items in this tag should not be placed from the off-hand
                    }
                }
                if (heldStack.isEmpty()) {
                    return InteractionResult.PASS;
                } else if (pedestal.addItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
                    level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1F);
                    return InteractionResult.SUCCESS;
                }

            } else if (!heldStack.isEmpty()) {
                if (pedestal.processContainItem(heldStack, player)) {
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;
            } else if (hand.equals(InteractionHand.MAIN_HAND)) {
                player.getInventory().placeItemBackInInventory(pedestal.removeItem());
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1F, 1F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!(level.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal)) {
            return true;
        }
        if (!pedestal.isEmpty() && !player.getAbilities().instabuild) {
            ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pedestal.getItem());
            level.addFreshEntity(item);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}