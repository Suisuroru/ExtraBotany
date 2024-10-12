package io.grasspow.extrabotany.common.item.equipment.weapon;

import io.grasspow.extrabotany.api.item.IItemWithLeftClick;
import io.grasspow.extrabotany.common.network.server.LeftClickPack;
import io.grasspow.extrabotany.xplat.client.ClientModXplatAbstractions;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.item.relic.RelicImpl;

import java.util.List;
import java.util.function.Consumer;

public abstract class RelicSwordItem extends SwordItem implements IItemWithLeftClick {
    public RelicSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        MinecraftForge.EVENT_BUS.addListener(this::leftClick);
        MinecraftForge.EVENT_BUS.addListener(this::leftClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::attackEntity);
    }

    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntity().level().isClientSide()) {
            onLeftClick(evt.getEntity(), evt.getTarget());
        }
    }

    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            ClientModXplatAbstractions.INSTANCE.sendToServer(new LeftClickPack(evt.getItemStack()));
        }
    }

    public void leftClickBlock(PlayerInteractEvent.LeftClickBlock evt) {
        if (evt.getEntity().level().isClientSide() && !evt.getItemStack().isEmpty() && evt.getItemStack().getItem() == this) {
            ClientModXplatAbstractions.INSTANCE.sendToServer(new LeftClickPack(evt.getItemStack()));
        }
    }

    @Override
    public int getEntityLifespan(ItemStack itemStack, Level level) {
        return Integer.MAX_VALUE;
    }

    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null) {
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        RelicImpl.addDefaultTooltip(stack, tooltip);
    }

    public static BlockHitResult raytraceFromEntity(Entity e, double distance, boolean fluids) {
        return (BlockHitResult) e.pick(distance, 1, fluids);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ToolCommons.damageItemIfPossible(stack, amount, entity, getManaPerDamage());
    }

    @Override
    public void onLeftClick(Player player, Entity target) {
        if (!player.level().isClientSide() && !player.getMainHandItem().isEmpty()
                && player.getMainHandItem().getItem() == this
                && player.getAttackStrengthScale(0) == 1
                && ManaItemHandler.instance().requestManaExactForTool(player.getMainHandItem(), player, getManaPerDamage(), true)) {
            attack(player, target);
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public void attack(LivingEntity player, Entity target) {
        attack(player, target, 1, 1D, 1F);
    }

    public abstract int getManaPerDamage();

    public abstract void attack(LivingEntity player, Entity target, int times, double speedTime, float damageTime);
}
