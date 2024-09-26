package io.grasspow.extrabotany.common.item;

import io.grasspow.extrabotany.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.block.mana.ManaPoolBlock;

public class EmptyBottleItem extends Item {
    public EmptyBottleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block instanceof ManaPoolBlock && world.getBlockEntity(pos) instanceof ManaPoolBlockEntity entity) {
            entity.receiveMana(-10000);
            stack.shrink(1);
            ctx.getPlayer().getInventory().placeItemBackInInventory(new ItemStack(ModItems.MANA_DRINK.get()));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
