package io.grasspow.extrabotany.common.entity.block;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block.PetalApothecary;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntity;
import vazkii.botania.common.block.block_entity.PetalApothecaryBlockEntity;

public class LivingrockBarrelBlockEntity extends BotaniaBlockEntity {
    private static final BlockPos[] OUTPUTS = {new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1)};
    protected FluidTank tank = new FluidTank(16000);
    private int count = 0;

    public void plusCount() {
        count++;
    }

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    public LivingrockBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.LIVINGROCK_BARREL_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void readPacketNBT(CompoundTag cmp) {
        tank.readFromNBT(cmp.getCompound("tank"));
        count = cmp.getInt("count");
    }

    @Override
    public void writePacketNBT(CompoundTag cmp) {
        cmp.put("tank", tank.writeToNBT(new CompoundTag()));
        cmp.putInt("count", count);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER)
            return holder.cast();
        return super.getCapability(cap, side);
    }

    private void initTank() {
        if (tank == null) {
            tank = new FluidTank(1600);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LivingrockBarrelBlockEntity barrel) {
        barrel.initTank();
        barrel.plusCount();
        if ((level.getBlockState(pos.below()).getFluidState().is(Fluids.WATER) || level.getBlockState(pos.below()).is(Blocks.WATER)) && barrel.count % 60 == 0) {
            barrel.tank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
        }
        BlockPos o = OUTPUTS[barrel.count % 4];
        if (barrel.tank.isEmpty()) return;
        if (level.getBlockEntity(pos.offset(o)) instanceof PetalApothecaryBlockEntity a) {
            if (a.getFluid() == PetalApothecary.State.WATER) return;
            a.setFluid(PetalApothecary.State.WATER);
            barrel.tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public int getAmount() {
        return tank.getFluidAmount();
    }

    public int getMax() {
        return tank.getCapacity();
    }
}
