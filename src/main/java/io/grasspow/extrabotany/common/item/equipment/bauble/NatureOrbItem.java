package io.grasspow.extrabotany.common.item.equipment.bauble;

import io.grasspow.extrabotany.api.capability.INatureOrb;
import io.grasspow.extrabotany.common.entity.ego.EGO;
import io.grasspow.extrabotany.xplat.ModXplatAbstractions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.CustomCreativeTabContents;

import javax.annotation.Nonnull;
import java.util.List;

import static io.grasspow.extrabotany.common.libs.CommonHelper.clearPotions;

public class NatureOrbItem extends BaubleItem implements CustomCreativeTabContents {

    protected static final String TAG_NATURE = "nature";
    protected static final int MAX_NATURE = 500000;

    public NatureOrbItem(Properties props) {
        super(props);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        var orb = ModXplatAbstractions.INSTANCE.findNatureOrbItem(stack);
        tooltip.add(Component.translatable("extrabotany.nature_orb", orb.getNature(), orb.getMaxNature()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("extrabotany.nature_orb_effect1").withStyle(orb.getNature() >= 100000 ? ChatFormatting.AQUA : ChatFormatting.GRAY));
        tooltip.add(Component.translatable("extrabotany.nature_orb_effect2").withStyle(orb.getNature() >= 300000 ? ChatFormatting.DARK_RED : ChatFormatting.GRAY));
        tooltip.add(Component.translatable("extrabotany.nature_orb_effect3").withStyle(orb.getNature() >= 400000 ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY));
    }

    @Nonnull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();
        Level level = ctx.getLevel();
        BlockPos clickedPos = ctx.getClickedPos();
        return EGO.spawn(ctx.getPlayer(), stack, level, clickedPos) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
//        return InteractionResult.PASS;
    }

    @Override
    public void addToCreativeTab(Item me, CreativeModeTab.Output output) {
        output.accept(new ItemStack(me));
        ItemStack full = new ItemStack(this);
        var orb = ModXplatAbstractions.INSTANCE.findNatureOrbItem(full);
        orb.addNature(orb.getMaxNature());
        output.accept(full);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var orb = ModXplatAbstractions.INSTANCE.findNatureOrbItem(stack);
        return Math.round((float) orb.getNature() * 13.0F / (float) orb.getMaxNature());
    }

    @Override
    public void onWornTick(ItemStack stack, LivingEntity entity) {
        super.onWornTick(stack, entity);
        if (entity instanceof Player player) {
            if (!player.level().isClientSide()) {
                var orb = ModXplatAbstractions.INSTANCE.findNatureOrbItem(stack);
                if (orb.getNature() > 100000 && player.tickCount % 5 == 0)
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                if (orb.getNature() > 200000 && player.tickCount % 5 == 0)
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                if (orb.getNature() > 300000 && player.tickCount % 5 == 0) {
                    ManaItemHandler.instance().dispatchManaExact(stack, player, 5, true);
                    if (player.tickCount % 60 == 0)
                        player.heal(1F);
                }
                if (orb.getNature() > 400000) {
                    if (player.tickCount % 40 == 0) {
                        clearPotions(stack, player);
                    }
                }
            }
        }
    }

    public static class NatureOrb implements INatureOrb {
        protected final ItemStack stack;

        public NatureOrb(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int getNature() {
            return ItemNBTHelper.getInt(stack, TAG_NATURE, 0) * stack.getCount();
        }

        @Override
        public int getMaxNature() {
            return MAX_NATURE * stack.getCount();
        }

        @Override
        public boolean addNature(int x) {
            if (getNature() + x < 0) return false;
            ItemNBTHelper.setInt(stack, TAG_NATURE, Mth.clamp(getNature() + x, 0, getMaxNature()) / stack.getCount());
            return true;
        }

        @Override
        public void setNature(int x) {
            ItemNBTHelper.setInt(stack, TAG_NATURE, Mth.clamp(x, 0, getMaxNature()) / stack.getCount());
        }

        @Override
        public boolean canReceiveNatureFromNatureAdder(BlockEntity adder) {
            return true;
        }

        @Override
        public boolean canReceiveManaFromItem(ItemStack otherStack) {
            return true;
        }

        @Override
        public boolean canExportManaToPool(BlockEntity pool) {
            return true;
        }

        @Override
        public boolean canExportManaToItem(ItemStack otherStack) {
            return true;
        }

        @Override
        public boolean isNoExport() {
            return false;
        }
    }
}
