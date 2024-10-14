package io.grasspow.extrabotany.common.item.equipment.armor;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.ArrayList;
import java.util.List;

import static io.grasspow.extrabotany.common.libs.CommonHelper.clearPotions;

public class MaidArmorHelmetItem extends MaidArmorItem {
    public List<ResourceKey<DamageType>> source = new ArrayList<>();

    public MaidArmorHelmetItem(Properties props) {
        this(ExtraBotanyAPI.instance().getGoblinSlayerArmorMaterial(), props, Type.HELMET);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityAttacked);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerAttacked);
        source.add(DamageTypes.FALLING_ANVIL);
        source.add(DamageTypes.CACTUS);
        source.add(DamageTypes.DROWN);
        source.add(DamageTypes.FALL);
        source.add(DamageTypes.FALLING_BLOCK);
        source.add(DamageTypes.IN_FIRE);
        source.add(DamageTypes.LAVA);
        source.add(DamageTypes.ON_FIRE);
        source.add(DamageTypes.LIGHTNING_BOLT);
        source.add(DamageTypes.FLY_INTO_WALL);
        source.add(DamageTypes.HOT_FLOOR);
        source.add(DamageTypes.SWEET_BERRY_BUSH);
    }

    public MaidArmorHelmetItem(ArmorMaterial mat, Properties props, Type type) {
        super(mat, props, type);
        setArmorTexture(LibMisc.MOD_ID + ":textures/model/maid_armor.png");
    }

    @SubscribeEvent
    public void onEntityAttacked(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        LivingEntity target = event.getEntity();
        if (attacker instanceof Player player && target != null && target != player) {
            if (hasArmorSet(player)) {
                if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty()
                        && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, 200, true))
                    event.setAmount(event.getAmount() + 8F);
                if (player.isHurt()
                        && ManaItemHandler.instance().requestManaExactForTool(new ItemStack(this), player, 80, true))
                    player.heal(event.getAmount() / 10F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlayerAttacked(LivingHurtEvent event) {
        Entity target = event.getEntity();
        if (target instanceof Player player) {
            if (hasArmorSet(player)) {
                event.getSource().is(DamageTypes.FALL);
                if (!source.stream().filter(event.getSource()::is).toList().isEmpty()) {
                    event.setAmount(0F);
                }
                if (event.getSource().is(DamageTypes.MAGIC)) {
                    event.setAmount(event.getAmount() * 0.75F);
                }
            }
        }
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (hasArmorSet(player) && !player.level().isClientSide()) {
            ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
            if (player.isHurt() && player.tickCount % 40 == 0
                    && ManaItemHandler.instance().requestManaExactForTool(stack, player, 20, true))
                player.heal(1F);
            if (player.tickCount % 40 == 0 && ManaItemHandler.instance().requestManaExactForTool(stack, player, 20, true))
                clearPotions(stack, player);
        }
    }
}
