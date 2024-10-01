package io.grasspow.extrabotany.common.entity.block;

import com.google.common.base.Predicates;
import io.grasspow.extrabotany.common.block.ManaBufferBlock;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.KeyLocked;
import vazkii.botania.api.mana.ManaBlockType;
import vazkii.botania.api.mana.ManaNetworkAction;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.api.mana.spark.ManaSpark;
import vazkii.botania.api.mana.spark.SparkAttachable;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntity;
import vazkii.botania.common.block.block_entity.mana.ThrottledPacket;
import vazkii.botania.common.handler.ManaNetworkHandler;

import java.util.List;
import java.util.Optional;

public class ManaBufferBlockEntity extends BotaniaBlockEntity implements ManaPool, KeyLocked, SparkAttachable, ThrottledPacket {

    private static final BlockPos[] INPUTS = {new BlockPos(1, 0, 0), new BlockPos(0, 0, 1),
            new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, -1, 0)};

    private static final BlockPos[] OUTPUT = {new BlockPos(0, 1, 0)};

    private static final String TAG_MANA = "mana";
    private static final String TAG_SPEED = "speed";
    private static final String TAG_MANA_CAP = "manaCap";
    private static final String TAG_INPUT_KEY = "inputKey";
    private static final String TAG_OUTPUT_KEY = "outputKey";
    private boolean sendPacket = false;
    private int ticks = 0;

    private ManaBufferBlock.Variant variant;
    private int manaCap = -1;
    private int mana;
    private int speed = 0;
    private String inputKey = "";
    private final String outputKey = "";

    public int getSpeed() {
        return speed;
    }

    public ManaBufferBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.MANA_BUFFER_BLOCK_ENTITY.get(), pos, state);
    }

    public ManaBufferBlockEntity(BlockPos pos, BlockState state, ManaBufferBlock.Variant variant) {
        super(ExtraBotanyEntities.Blocks.MANA_BUFFER_BLOCK_ENTITY.get(), pos, state);
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
        return getBlockState().getBlock() instanceof ManaBufferBlock ? mana : 0;
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
        int old = this.mana;
        this.mana = Math.max(0, Math.min(getCurrentMana() + mana, getMaxMana()));
        if (old != this.mana) {
            setChanged();
            markDispatchable();
        }
    }

    @Override
    public void markDispatchable() {
        sendPacket = true;
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return true;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.REMOVE);
    }

    private void initManaCapAndSpeed() {
        if (getMaxMana() == -1) {
            manaCap = ((ManaBufferBlock) (getBlockState().getBlock())).variant.equals(ManaBufferBlock.Variant.DEFAULT) ? 64000000 : 1024000000;
        }
        if (getSpeed() == 0) {
            speed = ((ManaBufferBlock) (getBlockState().getBlock())).variant.equals(ManaBufferBlock.Variant.DEFAULT) ? 400 : 5000;
        }
        if (!ManaNetworkHandler.instance.isPoolIn(level, this) && !isRemoved()) {
            BotaniaAPI.instance().getManaNetworkInstance().fireManaNetworkEvent(this, ManaBlockType.POOL, ManaNetworkAction.ADD);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ManaBufferBlockEntity buffer) {
        buffer.initManaCapAndSpeed();
        int speed = buffer.getSpeed();
        if (buffer.sendPacket && buffer.ticks % 10 == 0) {
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(buffer);
            buffer.sendPacket = false;
        }
        if (!buffer.isFull()) {
            for (BlockPos o : INPUTS) {
                if (level.getBlockEntity(pos.offset(o)) instanceof ManaPool pool && pool.getCurrentMana() >= 0) {
                    int manaToGet = Math.min(speed, pool.getCurrentMana());
                    int space = Math.max(0, buffer.getMaxMana() - buffer.getCurrentMana());
                    int current = Math.min(space, manaToGet);
                    pool.receiveMana(-current);
                    buffer.receiveMana(current);
                }
            }
        }

        for (BlockPos o : OUTPUT) {
            if (level.getBlockEntity(pos.offset(o)) instanceof ManaPool pool && !pool.isFull()) {
                int manaToGet = Math.min(speed, buffer.getCurrentMana());
                int space = Math.max(0, pool.getMaxMana() - pool.getCurrentMana());
                int current = Math.min(space, manaToGet);
                pool.receiveMana(current);
                buffer.receiveMana(-current);
            }
        }
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(buffer);
        buffer.ticks++;
    }

    @Override
    public void readPacketNBT(CompoundTag cmp) {
        mana = cmp.getInt(TAG_MANA);
        speed = cmp.getInt(TAG_SPEED);
        if (cmp.contains(TAG_MANA_CAP)) {
            manaCap = cmp.getInt(TAG_MANA_CAP);
        }
        if (cmp.contains(TAG_INPUT_KEY)) {
            inputKey = cmp.getString(TAG_INPUT_KEY);
        }
        if (cmp.contains(TAG_OUTPUT_KEY)) {
            inputKey = cmp.getString(TAG_OUTPUT_KEY);
        }
        setChanged();
    }

    @Override
    public void writePacketNBT(CompoundTag cmp) {
        cmp.putInt(TAG_MANA, getCurrentMana());
        cmp.putInt(TAG_SPEED, getSpeed());
        cmp.putInt(TAG_MANA_CAP, getMaxMana());
        cmp.putString(TAG_INPUT_KEY, inputKey);
        cmp.putString(TAG_OUTPUT_KEY, outputKey);
    }

    @Override
    public String getInputKey() {
        return inputKey;
    }

    @Override
    public String getOutputKey() {
        return outputKey;
    }

    @Override
    public boolean canAttachSpark(ItemStack stack) {
        return true;
    }

    @Override
    public ManaSpark getAttachedSpark() {
        List<Entity> sparks = level.getEntitiesOfClass(Entity.class, new AABB(worldPosition.above(), worldPosition.above().offset(1, 1, 1)), Predicates.instanceOf(ManaSpark.class));
        if (sparks.size() == 1) {
            Entity e = sparks.get(0);
            return (ManaSpark) e;
        }

        return null;
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }

    @Override
    public int getAvailableSpaceForMana() {
        int space = Math.max(0, getMaxMana() - getCurrentMana());
        if (space > 0) {
            return space;
        } else if (level.getBlockState(worldPosition.below()).is(BotaniaBlocks.manaVoid)) {
            return getMaxMana();
        } else {
            return 0;
        }
    }
}
