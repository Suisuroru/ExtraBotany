package io.grasspow.extrabotany.common.entity.block;

import com.google.common.base.Suppliers;
import io.grasspow.extrabotany.common.crafting.PedestalClickRecipe;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.item.equipment.bauble.NatureOrbItem;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import io.grasspow.extrabotany.common.registry.ExtraBotanyRecipeTypes;
import io.grasspow.extrabotany.xplat.ModXplatAbstractions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.client.core.handler.ClientTickHandler;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.block_entity.PylonBlockEntity;
import vazkii.botania.common.block.mana.ManaPoolBlock;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class PedestalBlockEntity extends ModBlockEntity {
    private static final String TAG_SPEED = "speed";
    private static final String TAG_ENERGY = "energy";
    private int speed = 0;
    private int energy = 0;
    public static final BlockPos[] POOL_LOCATIONS = {
            new BlockPos(3, 0, 3), new BlockPos(-3, 0, 3), new BlockPos(3, 0, -3), new BlockPos(-3, 0, -3)
    };
    public static final BlockPos[] PYLON_LOCATIONS = {
            new BlockPos(2, 0, 2), new BlockPos(-2, 0, 2), new BlockPos(2, 0, -2), new BlockPos(-2, 0, -2),
            new BlockPos(3, 1, 3), new BlockPos(-3, 1, 3), new BlockPos(3, 1, -3), new BlockPos(-3, 1, -3)
    };
    public static final Supplier<IMultiblock> MULTIBLOCK = Suppliers.memoize(() -> PatchouliAPI.get().makeMultiblock(
            new String[][]{
                    {
                            "N___N",
                            "_____",
                            "__0__",
                            "_____",
                            "N___N"
                    }
            },
            '0', ExtraBotanyBlocks.PEDESTAL.get(),
            'N', BotaniaBlocks.naturaPylon
    ));
    private static final Supplier<IStateMatcher> MANAPOOL_MATCHER = Suppliers.memoize(() -> PatchouliAPI.get().predicateMatcher(
            BotaniaBlocks.manaPool,
            state -> state.getBlock() instanceof ManaPoolBlock
    ));
    public static final Supplier<IMultiblock> MULTIBLOCK2 = Suppliers.memoize(() -> PatchouliAPI.get().makeMultiblock(
            new String[][]{
                    {
                            "N_____N",
                            "_______",
                            "_______",
                            "_______",
                            "_______",
                            "_______",
                            "N_____N",
                    },
                    {
                            "M_____M",
                            "_N___N_",
                            "_______",
                            "___0___",
                            "_______",
                            "_N___N_",
                            "M_____M"
                    },
                    {
                            "_______",
                            "_BBBBB_",
                            "_B___B_",
                            "_B___B_",
                            "_B___B_",
                            "_BBBBB_",
                            "_______",
                    }
            },
            '0', ExtraBotanyBlocks.PEDESTAL.get(),
            'M', MANAPOOL_MATCHER.get(),
            'B', BotaniaBlocks.shimmerrock,
            'N', BotaniaBlocks.naturaPylon
    ));

    private Random rand = new Random();
    public boolean processing = false;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ExtraBotanyEntities.Blocks.PEDESTAL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    public boolean isEmpty() {
        return getItemHandler().getItem(0).isEmpty();
    }

    public ItemStack getItem() {
        return getItemHandler().getItem(0);
    }

    public boolean addItem(ItemStack stack) {
        if (isEmpty() && !stack.isEmpty()) {
            if (stack.is(ExtraBotanyItems.GILDED_POTATO.get()) && stack.getOrCreateTag().getBoolean("pedestal_deny")) {
                return false;
            }
            getItemHandler().setItem(0, stack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack item = getItemHandler().getItem(0);
            getItemHandler().setItem(0, ItemStack.EMPTY);
            inventoryChanged();
            return item.copy();
        }
        return ItemStack.EMPTY;
    }

    public boolean processContainItem(ItemStack stack, Player player) {
        processing = true;
        if (level == null) {
            processing = false;
            return false;
        }
        SimpleContainer itemHandler = new SimpleContainer(2) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
        itemHandler.setItem(0, getItemHandler().getItem(0));
        itemHandler.setItem(1, stack.copy());
        Optional<PedestalClickRecipe> matchingRecipe = level.getRecipeManager().getRecipeFor(ExtraBotanyRecipeTypes.PEDESTAL_CLICK.get(), itemHandler, level);
        matchingRecipe.ifPresent(recipe -> {
            if (!recipe.containClickTool(stack.getItem())) return;
            if (player != null) {
                stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else {
                if (stack.hurt(1, level.random, null)) {
                    stack.setCount(0);
                }
            }
            if (rand.nextInt(10) < 3) {
                ItemStack result = recipe.assemble(itemHandler, getLevel().registryAccess());
                player.getInventory().placeItemBackInInventory(result.copy());
                removeItem();
            } else {
                level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), SoundEvents.ANVIL_HIT, SoundSource.BLOCKS, 1f, 1f);
            }
        });
        processing = false;
        return matchingRecipe.isPresent();
    }

    public static void commonTick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity self) {
        ItemStack stack = self.getItem();
        if (stack.getItem() instanceof NatureOrbItem) {
            if (!level.isClientSide) {
                self.freshSpeed();
                var orb = ModXplatAbstractions.INSTANCE.findNatureOrbItem(self.getItem());
                orb.addNature(self.speed);
            } else {
                Vec3 loc = self.getBlockPos().getCenter();
                for (int i = 0, pylonLocationsLength = PYLON_LOCATIONS.length; i < pylonLocationsLength; i++) {
                    BlockPos arr = PYLON_LOCATIONS[i];
                    BlockPos pylonPos = self.getBlockPos().offset(arr);
                    if (!(level.getBlockEntity(pylonPos) instanceof PylonBlockEntity)) {
                        continue;
                    }
                    if (MULTIBLOCK.get().validate(level, self.getBlockPos()) != null && i < 4) {
                        renderWisp(level, loc, pylonPos);
                    }
                    if (MULTIBLOCK2.get().validate(level, self.getBlockPos()) != null && i >= 4) {
                        renderWisp(level, loc, pylonPos);
                    }
                }
            }
        } else if (stack.getItem() == ExtraBotanyItems.NIGHTMARE_FUEL.get()) {
            if (!level.isClientSide && level.isDay()) {
                self.setEnergy(self.getEnergy() + 1);
                if (self.getEnergy() >= 6000) {
                    self.removeItem();
                    self.addItem(new ItemStack(ExtraBotanyItems.SPIRIT_FUEL.get()));
                }
                self.setEnergy(0);
            }
        }

    }

    private static void renderWisp(Level level, Vec3 loc, BlockPos pylonPos) {
        double worldTime = ClientTickHandler.ticksInGame;
        worldTime /= 5;

        float rad = 0.75F + (float) Math.random() * 0.05F;
        double xp = pylonPos.getX() + 0.5 + Math.cos(worldTime) * rad;
        double zp = pylonPos.getZ() + 0.5 + Math.sin(worldTime) * rad;

        Vec3 partPos = new Vec3(xp, pylonPos.getY(), zp);
        Vec3 mot = loc.subtract(partPos).multiply(0.04, 0.04, 0.04);

        float r = (float) Math.random() * 0.3F;
        float g = 0.75F + (float) Math.random() * 0.2F;
        float b = (float) Math.random() * 0.3F;

        WispParticleData data = WispParticleData.wisp(0.25F + (float) Math.random() * 0.1F, r, g, b, 1);
        level.addParticle(data, partPos.x, partPos.y, partPos.z, 0, -(-0.075F - (float) Math.random() * 0.015F), 0);
        WispParticleData data1 = WispParticleData.wisp(0.4F, r, g, b);
        level.addParticle(data1, partPos.x, partPos.y, partPos.z, (float) mot.x, (float) mot.y, (float) mot.z);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    private void freshSpeed() {
        if (MULTIBLOCK2.get().validate(level, getBlockPos()) != null) {
            speed = 9;
            for (BlockPos poolPos : POOL_LOCATIONS) {
                if (level.getBlockEntity(getBlockPos().offset(poolPos)) instanceof ManaPool pool) {
                    if (pool.getCurrentMana() >= 10) {
                        pool.receiveMana(-10);
                        speed += 2;
                    }
                }
            }
        } else if (MULTIBLOCK.get().validate(level, getBlockPos()) != null) {
            speed = 4;
        } else {
            speed = 0;
        }
    }

    @Override
    public void readPacketNBT(CompoundTag tag) {
        super.readPacketNBT(tag);
        speed = tag.getInt(TAG_SPEED);
        energy = tag.getInt(TAG_ENERGY);
    }

    @Override
    public void writePacketNBT(CompoundTag tag) {
        super.writePacketNBT(tag);
        tag.putInt(TAG_SPEED, speed);
        tag.putInt(TAG_ENERGY, energy);
    }
}
