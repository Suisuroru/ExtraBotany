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
        if (getItemHandler().getItem(0).isEmpty()) {
            ItemStack stackToAdd = stack.copyWithCount(1);
            getItemHandler().setItem(0, stackToAdd);

            if (player == null || !player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
        }
        return true;
    }


    public boolean processContainItem(ItemStack stack, Player player) {
        if (level == null) return false;
        Optional<PedestalRecipe> matchingRecipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.PEDESTAL_CLICK.get(), getItemHandler(), level);
        matchingRecipe.ifPresent(recipe -> {
            ItemStack result = recipe.assemble(getItemHandler(), getLevel().registryAccess());
            getItemHandler().setItem(0, result.copy());
            if (player != null) {
                stack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else {
                if (stack.hurt(1, level.random, null)) {
                    stack.setCount(0);
                }
            }
        });
        return matchingRecipe.isPresent();
    }
}
