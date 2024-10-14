package io.grasspow.extrabotany.common.item.misc;

import io.grasspow.extrabotany.common.entity.ego.EGO;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ChallengeTicketItem extends Item {
    public ChallengeTicketItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        Level level = ctx.getLevel();
        BlockPos clickedPos = ctx.getClickedPos();
        return EGO.spawn(ctx.getPlayer(), stack, level, clickedPos) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }
}
