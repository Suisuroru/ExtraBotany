package io.grasspow.extrabotany.common.item.equipment.bauble;

import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.mana.ManaDiscountEvent;
import vazkii.botania.common.handler.EquipmentHandler;

public class AquaStoneItem extends BaubleItem {

    public AquaStoneItem(Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::manaDiscount);
    }

    @SubscribeEvent
    public void manaDiscount(ManaDiscountEvent event) {
        Player player = event.getEntityPlayer();
        if (!EquipmentHandler.findOrEmpty(this, player).isEmpty() || !EquipmentHandler.findOrEmpty(ExtraBotanyItems.THE_COMMUNITY.get(), player).isEmpty()) {
            event.setDiscount(event.getDiscount() + 0.1F);
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, LivingEntity entity) {
        return EquipmentHandler.findOrEmpty(this, entity).isEmpty()
                && EquipmentHandler.findOrEmpty(ExtraBotanyItems.THE_COMMUNITY.get(), entity).isEmpty();
    }

}
