package io.grasspow.extrabotany.common.entity.block;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import vazkii.botania.api.block.PetalApothecary;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntity;
import vazkii.botania.common.block.block_entity.PetalApothecaryBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class LivingrockBarrelBlockEntity extends BotaniaBlockEntity {
    private static final BlockPos[] OUTPUTS = {new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1)};
    public static final int MAX_FLUID_AMOUNT = 16000;
    private static final String FLUID_TAG = "fluid";
    private static final String FLUID_AMOUNT_TAG = "fluidAmount";
    protected LivingrockBarrelBlockState lastSyncedState = null;
    protected int fluidAmount;
    protected int ticksSinceLast = 0;

    public LivingrockBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.LIVINGROCK_BARREL_BLOCK_ENTITY.get(), pos, state);
        ticksSinceLast = 0;
        fluidAmount = 0;
    }

    public int getFluidAmount() {
        return FluidHandler.getHandler(this).getFluidAmount();
    }

    public float getFluidProportion() {
        return ((float) FluidHandler.getHandler(this).getFluidAmount())
                / FluidHandler.getHandler(this).getCapacity();
    }

    @Override
    @Nonnull
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void readPacketNBT(CompoundTag cmp) {
        FluidHandler.getHandler(this).readFromNBT(cmp.getCompound("tank"));
        ticksSinceLast = cmp.getInt("ticksSinceLast");
        fluidAmount = cmp.getInt(FLUID_AMOUNT_TAG);
        super.readPacketNBT(cmp);
    }

    @Override
    public void writePacketNBT(CompoundTag cmp) {
        super.writePacketNBT(cmp);
        if (!FluidHandler.getHandler(this).isEmpty()) {
            final CompoundTag fluidNbt =
                    FluidHandler.getHandler(this).writeToNBT(new CompoundTag());
            cmp.put(FLUID_TAG, fluidNbt);
        }
        cmp.put("tank", FluidHandler.getHandler(this).writeToNBT(new CompoundTag()));
        cmp.putInt("ticksSinceLast", ticksSinceLast);
        cmp.putInt(FLUID_AMOUNT_TAG, fluidAmount);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        final CompoundTag nbt = pkt.getTag();
        if (nbt.contains(FLUID_TAG)) {
            FluidHandler.getHandler(this).readFromNBT(nbt.getCompound(FLUID_TAG));
        } else {
            FluidHandler.getHandler(this).setFluid(FluidStack.EMPTY);
        }
        fluidAmount = nbt.getInt(FLUID_AMOUNT_TAG);
    }

    public void tick(Level level, LivingrockBarrelBlockEntity barrel) {
        if (level == null || level.isClientSide) {
            return;
        }
        ticksSinceLast++;
        final BlockPos pos = barrel.getBlockPos();
        if (ticksSinceLast >= 60) {
            ticksSinceLast = 0;
            if ((level.getBlockState(pos.below()).getFluidState().is(Fluids.WATER) || level.getBlockState(pos.below()).is(Blocks.WATER))) {
                fluidAmount += 1000;
                FluidHandler.getHandler(barrel).fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
            }
        }
        BlockPos o = OUTPUTS[barrel.ticksSinceLast % 4];
        if (level.getBlockEntity(pos.offset(o)) instanceof PetalApothecaryBlockEntity a && a.getFluid() != PetalApothecary.State.WATER) {
            a.setFluid(PetalApothecary.State.WATER);
            fluidAmount -= 1000;
            FluidHandler.getHandler(barrel).drain(1000, IFluidHandler.FluidAction.EXECUTE);
        }
        final LivingrockBarrelBlockState currentState = new LivingrockBarrelBlockState(this);
        if (!currentState.equals(lastSyncedState)) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            lastSyncedState = currentState;
        }
    }

    private boolean canAddWater(FluidStack fluidStack) {
        return MAX_FLUID_AMOUNT - fluidAmount > 1000;
    }

    protected static class FluidHandler extends FluidTank {
        private static final Map<BlockEntity, FluidHandler> Barrels =
                new IdentityHashMap<>();

        public static FluidHandler getHandler(LivingrockBarrelBlockEntity entity) {
            return Barrels.computeIfAbsent(
                    entity, (block1) -> new FluidHandler(entity::canAddWater));
        }

        public FluidHandler(Predicate<FluidStack> validator) {
            super(LivingrockBarrelBlockEntity.MAX_FLUID_AMOUNT, validator);
        }
    }

    protected static class LivingrockBarrelBlockState {

        @Nullable
        private final Fluid fluid = Fluids.WATER;
        private final int fluidAmount;

        LivingrockBarrelBlockState(@Nonnull final LivingrockBarrelBlockEntity barrel) {
            fluidAmount = barrel.getFluidAmount();
        }

        @Override
        public boolean equals(@Nullable final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            LivingrockBarrelBlockState that = (LivingrockBarrelBlockState) o;
            return fluidAmount == that.fluidAmount && Objects.equals(fluid, that.fluid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fluid, fluidAmount);
        }
    }
}
