package io.grasspow.extrabotany.common.entity.block;

import io.grasspow.extrabotany.common.block.ManaBufferBlock;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

import java.util.Optional;

public class ManaBufferBlockEntity extends BotaniaBlockEntity implements ManaPool {

    private static final BlockPos[] INPUTS = {new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0)};

    private static final BlockPos[] OUTPUT = {new BlockPos(0, 1, 0)};

    private static final String TAG_MANA = "mana";
    private static final String TAG_SPEED = "speed";
    private static final String TAG_MANA_CAP = "manaCap";

    private ManaBufferBlock.Variant variant;
    private int manaCap = -1;
    private int mana;
    private int speed = 0;

    public int getSpeed() {
        return speed;
    }

    public ManaBufferBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.MANA_BUFFER.get(), pos, state);
    }

    public ManaBufferBlockEntity(BlockPos pos, BlockState state, ManaBufferBlock.Variant variant) {
        super(ExtraBotanyEntities.Blocks.MANA_BUFFER.get(), pos, state);
        this.variant = variant;
        manaCap = variant.equals(ManaBufferBlock.Variant.DEFAULT) ? 64000000 : 1024000000;
        speed = variant.equals(ManaBufferBlock.Variant.DEFAULT) ? 400 : 5000;
    }

    @Override
    public Level getManaReceiverLevel() {
        return getLevel();
    }

    @Override
    public BlockPos getManaReceiverPos() {
        return getBlockPos();
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    @Override
    public boolean isFull() {
        return mana >= manaCap;
    }

    @Override
    public boolean isOutputtingPower() {
        return false;
    }

    public int getMaxMana() {
        return manaCap;
    }

    @Override
    public Optional<DyeColor> getColor() {
        return Optional.empty();
    }

    @Override
    public void setColor(Optional<DyeColor> color) {

    }

    @Override
    public void receiveMana(int mana) {
        this.mana = Math.min(getMaxMana(), this.mana + mana);
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return true;
    }

    private void initManaCapAndSpeed() {
        if (getMaxMana() == -1) {
            manaCap = ((ManaBufferBlock) (getBlockState().getBlock())).variant.equals(ManaBufferBlock.Variant.DEFAULT) ? 64000000 : 1024000000;
        }
        if (getSpeed() == 0) {
            speed = ((ManaBufferBlock) (getBlockState().getBlock())).variant.equals(ManaBufferBlock.Variant.DEFAULT) ? 400 : 5000;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ManaBufferBlockEntity buffer) {
        buffer.initManaCapAndSpeed();
        int speed = buffer.getSpeed();
        for (BlockPos o : INPUTS)// inputs
        {
            if (buffer.isFull()) return;
            if (level.getBlockEntity(pos.offset(o)) instanceof ManaPoolBlockEntity pool) {
                if (pool.getCurrentMana() <= 0) return;
                int manaToGet = Math.min(speed, pool.getCurrentMana());
                int space = Math.max(0, buffer.getMaxMana() - buffer.getCurrentMana());
                int current = Math.min(space, manaToGet);
                pool.receiveMana(-current);
                buffer.receiveMana(current);
            } else if (level.getBlockEntity(pos.offset(o)) instanceof ManaBufferBlockEntity buffer2) {
                if (buffer2.getCurrentMana() <= 0) return;
                int manaToGet = Math.min(speed, buffer.getCurrentMana());
                int space = Math.max(0, buffer.getMaxMana() - buffer.getCurrentMana());
                int current = Math.min(space, manaToGet);
                buffer2.receiveMana(-current);
                buffer.receiveMana(current);
            }
        }

        for (BlockPos o : OUTPUT)// output
            if (level.getBlockEntity(pos.offset(o)) instanceof ManaPoolBlockEntity pool) {
                if (pool.isFull()) return;
                int manaToGet = Math.min(speed, buffer.getCurrentMana());
                int space = Math.max(0, pool.getMaxMana() - pool.getCurrentMana());
                int current = Math.min(space, manaToGet);
                pool.receiveMana(current);
                buffer.receiveMana(-current);
            } else if (level.getBlockEntity(pos.offset(o)) instanceof ManaBufferBlockEntity buffer2) {
                if (buffer2.isFull()) return;
                int manaToGet = Math.min(speed, buffer.getCurrentMana());
                int space = Math.max(0, buffer2.manaCap - buffer2.getCurrentMana());
                int current = Math.min(space, manaToGet);
                buffer2.receiveMana(current);
                buffer.receiveMana(-current);
            }
    }

    @Override
    public void readPacketNBT(CompoundTag cmp) {
        mana = cmp.getInt(TAG_MANA);
        mana = cmp.getInt(TAG_SPEED);
        if (cmp.contains(TAG_MANA_CAP)) {
            manaCap = cmp.getInt(TAG_MANA_CAP);
        }
        setChanged();
    }

    @Override
    public void writePacketNBT(CompoundTag cmp) {
        cmp.putInt(TAG_MANA, getCurrentMana());
        cmp.putInt(TAG_SPEED, getSpeed());
        cmp.putInt(TAG_MANA_CAP, getMaxMana());
    }
}
