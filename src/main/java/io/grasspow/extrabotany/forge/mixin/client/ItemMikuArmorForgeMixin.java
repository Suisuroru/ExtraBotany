package io.grasspow.extrabotany.forge.mixin.client;

import net.minecraft.world.item.Item;

//@Mixin(MikuArmorItem.class)
public abstract class ItemMikuArmorForgeMixin extends Item {
    private ItemMikuArmorForgeMixin(Properties properties) {
        super(properties);
    }
//
//    @Override
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            @Override
//            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
//                return ArmorModels.get(stack);
//            }
//        });
//    }
}
