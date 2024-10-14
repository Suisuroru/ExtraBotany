package io.grasspow.extrabotany.forge.mixin.client;

import io.grasspow.extrabotany.client.model.armor.ArmorModels;
import io.grasspow.extrabotany.common.item.equipment.armor.MikuArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Consumer;

@Mixin(MikuArmorItem.class)
public abstract class ItemMikuArmorForgeMixin extends Item {
    private ItemMikuArmorForgeMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                return ArmorModels.get(stack);
            }
        });
    }
}
