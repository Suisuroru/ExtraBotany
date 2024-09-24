package io.grasspow.extrabotany.common.block.block_entity;

import io.grasspow.extrabotany.common.crafting.PedestalRecipe;
import io.grasspow.extrabotany.common.registry.ModBlockEntities;
import io.grasspow.extrabotany.common.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.common.block.block_entity.SimpleInventoryBlockEntity;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.WandOfTheForestItem;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class PedestalBlockEntity extends SimpleInventoryBlockEntity {

    public PedestalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PEDESTAL_BLOCK_ENTITY.get(), pPos, pBlockState);
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

    public boolean addItem(Player player, ItemStack stack) {
        if (stack.getItem() instanceof WandOfTheForestItem || stack.is(BotaniaItems.lexicon)) {
            return false;
        }
        ItemStack stackToAdd = stack.copyWithCount(1);
        getItemHandler().setItem(0, stackToAdd);
        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        return true;
    }


    public boolean processContainItem(ItemStack stack, Player player) {
        if (level == null || stack.isEmpty()) return false;
        SimpleContainer itemHandler = new SimpleContainer(2) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
        itemHandler.setItem(0, getItemHandler().getItem(0));
        itemHandler.setItem(1, stack.copy());
        AtomicBoolean flag = new AtomicBoolean(false);
        Optional<PedestalRecipe> matchingRecipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.PEDESTAL_CLICK.get(), itemHandler, level);
        matchingRecipe.ifPresent(recipe -> {
            if (!recipe.getClickTool().is(stack.getItem())) return;
            ItemStack result = recipe.assemble(itemHandler, getLevel().registryAccess());
            getItemHandler().setItem(0, result.copy());
            if (player != null) {
                stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else {
                if (stack.hurt(1, level.random, null)) {
                    stack.setCount(0);
                }
            }
            flag.set(true);
        });
        return flag.get();
    }
}
