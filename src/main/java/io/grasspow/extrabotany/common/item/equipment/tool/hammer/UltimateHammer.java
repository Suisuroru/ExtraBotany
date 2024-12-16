package io.grasspow.extrabotany.common.item.equipment.tool.hammer;

import io.grasspow.extrabotany.common.item.equipment.tool.ModTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.SequentialBreaker;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.annotations.SoftImplement;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.lib.BotaniaTags;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;
import java.util.function.Predicate;

public class UltimateHammer extends ManasteelHammer implements SequentialBreaker {
    private static final String TAG_ENABLED = "enabled";
    private static final String TAG_REPAIR_UPGRADE = "repair";
    private static final String TAG_DAMAGE_UPGRADE = "attack";
    private static final String TAG_RANGE_UPGRADE = "range";
    private static final String TAG_MANA = "mana";
    private static final int MANA_PER_DAMAGE = 80;
    private static final int MAX_MANA = Integer.MAX_VALUE - 1;

    public UltimateHammer(Properties props) {
        super(ModTiers.TERRASTEEL, props, -2.8F);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        if (getAttack(stack) > 0) {
            list.add(Component.translatable("extrabotany.upgrade.attack" + getAttack(stack)));
        }
        if (getRepair(stack) > 0) {
            list.add(Component.translatable("extrabotany.upgrade.repair" + getAttack(stack)));
        }
        if (hasRange(stack)) {
            list.add(Component.translatable("extrabotany.upgrade.range." + (isEnabled(stack) ? "on" : "off")));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity.tickCount % (40 - getRepair(stack) * 10) == 0 && entity instanceof Player player && ManaItemHandler.instance().requestManaExact(stack, player, (MANA_PER_DAMAGE - getRepair(stack) * 5), true) && stack.getDamageValue() > 0) {
            stack.setDamageValue(stack.getDamageValue() - 1);
        }
        if (isEnabled(stack)) {
            if (!hasRange(stack) || getMana_(stack) == 0) {
                setEnabled(stack, false);
            } else if (entity instanceof Player player && !player.swinging) {
                var manaItem = XplatAbstractions.INSTANCE.findManaItem(stack);
                manaItem.addMana(-MANA_PER_DAMAGE);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isSecondaryUseActive() && hand == InteractionHand.MAIN_HAND) {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
            if (blockhitresult.getType() != HitResult.Type.BLOCK && hasRange(stack) && player.isShiftKeyDown()) {
                setEnabled(stack, !isEnabled(stack));
                if (!level.isClientSide) {
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), BotaniaSounds.terraPickMode, SoundSource.PLAYERS, 1F, 1F);
                }
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (getAttack(stack) > 0 && ManaItemHandler.instance().requestManaExact(stack, (Player) attacker, (80 * getAttack(stack) - getRepair(stack) * 15), true)) {
            if (getAttack(stack) > 1)
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
            if (getAttack(stack) > 3)
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 1));
            if (getAttack(stack) > 5)
                target.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 100, 2));
            if (getAttack(stack) > 7)
                target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 2));
            if (getAttack(stack) > 9)
                attacker.heal(4F);
            target.hurt(target.damageSources().playerAttack((Player) attacker), 2F * getAttack(stack));
        }
        return true;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return toolAction == ToolActions.PICKAXE_DIG ||
                toolAction == ToolActions.AXE_DIG ||
                toolAction == ToolActions.SHOVEL_DIG;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) ||
                state.is(BlockTags.MINEABLE_WITH_AXE) ||
                state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return this.speed;
        }
        return 1.0F;
    }

    @SoftImplement("IForgeItem")
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        BlockHitResult raycast = ToolCommons.raytraceFromEntity(player, 10, false);
        if (!player.level().isClientSide && raycast.getType() == HitResult.Type.BLOCK) {
            Direction face = raycast.getDirection();
            breakOtherBlock(player, stack, pos, pos, face);
            if (player.isSecondaryUseActive()) {
                BotaniaAPI.instance().breakOnAllCursors(player, stack, pos, face);
            }
        }
        return false;
    }

    @Override
    public void breakOtherBlock(Player player, ItemStack stack, BlockPos pos, BlockPos originPos, Direction side) {
        if (!isEnabled(stack)) {
            return;
        }

        Level world = player.level();
        Predicate<BlockState> canMine = state -> {
            boolean rightToolForDrops = !state.requiresCorrectToolForDrops() || stack.isCorrectToolForDrops(state);
            boolean rightToolForSpeed = stack.getDestroySpeed(state) > 1
                    || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                    || state.is(BlockTags.MINEABLE_WITH_HOE);
            return rightToolForDrops && rightToolForSpeed;
        };

        BlockState targetState = world.getBlockState(pos);
        if (!canMine.test(targetState)) {
            return;
        }

        if (world.isEmptyBlock(pos)) {
            return;
        }

        boolean doX = side.getStepX() == 0;
        boolean doY = side.getStepY() == 0;
        boolean doZ = side.getStepZ() == 0;

        Vec3i beginDiff = new Vec3i(doX ? -1 : 0, doY ? -1 : 0, doZ ? -1 : 0);
        Vec3i endDiff = new Vec3i(doX ? 1 : 0, doY ? 1 : 0, doZ ? 1 : 0);
        ToolCommons.removeBlocksInIteration(player, stack, world, pos, beginDiff, endDiff, canMine);
    }

    public static boolean isEnabled(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_ENABLED, false);
    }

    private static void setEnabled(ItemStack stack, boolean enabled) {
        ItemNBTHelper.setBoolean(stack, TAG_ENABLED, enabled);
    }

    public static void setRepair(ItemStack stack, int repair) {
        ItemNBTHelper.setInt(stack, TAG_REPAIR_UPGRADE, repair);
    }

    public static int getRepair(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_REPAIR_UPGRADE, 0);
    }

    public static void setAttack(ItemStack stack, int attack) {
        ItemNBTHelper.setInt(stack, TAG_DAMAGE_UPGRADE, attack);
    }

    public static int getAttack(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_DAMAGE_UPGRADE, 0);
    }


    public static boolean hasRange(ItemStack stack) {
        return ItemNBTHelper.getBoolean(stack, TAG_RANGE_UPGRADE, false);
    }

    public static void setRange(ItemStack stack, boolean enabled) {
        ItemNBTHelper.setBoolean(stack, TAG_RANGE_UPGRADE, enabled);
    }

    protected static void setMana(ItemStack stack, int mana) {
        if (mana > 0) {
            ItemNBTHelper.setInt(stack, TAG_MANA, mana);
        } else {
            ItemNBTHelper.removeEntry(stack, TAG_MANA);
        }
    }

    public static int getMana_(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_MANA, 0);
    }


    public static class ManaItemImpl implements ManaItem {
        private final ItemStack stack;

        public ManaItemImpl(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int getMana() {
            return getMana_(stack) * stack.getCount();
        }

        @Override
        public int getMaxMana() {
            return MAX_MANA * stack.getCount();
        }

        @Override
        public void addMana(int mana) {
            setMana(stack, Math.min(getMana() + mana, getMaxMana()) / stack.getCount());
        }

        @Override
        public boolean canReceiveManaFromPool(BlockEntity pool) {
            return true;
        }

        @Override
        public boolean canReceiveManaFromItem(ItemStack otherStack) {
            return !otherStack.is(BotaniaTags.Items.TERRA_PICK_BLACKLIST);
        }

        @Override
        public boolean canExportManaToPool(BlockEntity pool) {
            return false;
        }

        @Override
        public boolean canExportManaToItem(ItemStack otherStack) {
            return false;
        }

        @Override
        public boolean isNoExport() {
            return true;
        }
    }

    @NotNull
    @Override
    public Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.EPIC;
    }
}
