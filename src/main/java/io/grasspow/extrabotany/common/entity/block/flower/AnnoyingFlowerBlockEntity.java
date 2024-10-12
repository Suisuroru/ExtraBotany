package io.grasspow.extrabotany.common.entity.block.flower;

import io.grasspow.extrabotany.common.entity.block.PedestalBlockEntity;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import java.util.Collections;
import java.util.List;

public class AnnoyingFlowerBlockEntity extends FunctionalFlowerBlockEntity {
    private static final int COST = 300;
    private static final int RANGE = 3;
    private static final String TAG_TIME = "times";
    int times = 0;

    public AnnoyingFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyEntities.Blocks.Flowers.ANNOYING_FLOWER.get(), pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if (redstoneSignal > 0)
            return;

        boolean hasWater = false;
        for (int x = -RANGE; x <= RANGE; x++) {
            for (int z = -RANGE; z <= RANGE; z++) {
                BlockPos posi = getEffectivePos().offset(x, 0, z);
                if (getLevel().getBlockEntity(posi) instanceof PedestalBlockEntity pedestal) {
                    hasWater = pedestal.getItem().is(Items.WATER_BUCKET);
                    if (hasWater)
                        break;
                }
            }
        }

        for (ItemEntity item : getLevel().getEntitiesOfClass(ItemEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
            if (item.getItem().getItem() == ExtraBotanyItems.FRIED_CHICKEN.get() && item.getItem().getCount() > 0) {
                item.getItem().shrink(1);
                times += 3;
            }
        }

        int cd = times > 0 ? (900 * 2 / 5) : 900;

        if (redstoneSignal == 0 && ticksExisted % cd == 0 && getMana() >= COST && hasWater && !this.getLevel().isClientSide()) {
            RandomSource rand = getLevel().random;
            ItemStack stack;
            do {
                LootParams lootParams = new LootParams.Builder((ServerLevel) getLevel()).create(LootContextParamSets.EMPTY);
                List<ItemStack> stacks = getLevel().getServer().getLootData()
                        .getLootTable(BuiltInLootTables.FISHING).getRandomItems(lootParams);
                if (times > 0) {
                    stacks = getLevel().getServer().getLootData()
                            .getLootTable(BuiltInLootTables.FISHING_TREASURE).getRandomItems(lootParams);
                }
                if (stacks.isEmpty()) {
                    return;
                } else {
                    Collections.shuffle(stacks);
                    stack = stacks.get(0);
                }
            } while (stack.isEmpty());

            int bound = RANGE * 2 + 1;
            ItemEntity entity = new ItemEntity(getLevel(), getEffectivePos().getX() - RANGE + rand.nextInt(bound), getEffectivePos().getY() + 2, getEffectivePos().getZ() - RANGE + rand.nextInt(bound), stack);

            if (!getLevel().isClientSide())
                getLevel().addFreshEntity(entity);
            addMana(-COST);
            sync();
        }
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_TIME, times);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        times = cmp.getInt(TAG_TIME);
    }

    @Override
    public int getColor() {
        return 0x000000;
    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
