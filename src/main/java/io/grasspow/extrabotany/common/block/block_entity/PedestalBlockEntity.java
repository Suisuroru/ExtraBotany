package io.grasspow.extrabotany.common.block.block_entity;

import io.grasspow.extrabotany.common.crafting.PedestalClickRecipe;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlockEntities;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import io.grasspow.extrabotany.common.registry.ExtraBotanyRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;

public class PedestalBlockEntity extends ModBlockEntity {
    private Random rand = new Random();
    public boolean processing = false;

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ExtraBotanyBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), pPos, pBlockState);
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
}
