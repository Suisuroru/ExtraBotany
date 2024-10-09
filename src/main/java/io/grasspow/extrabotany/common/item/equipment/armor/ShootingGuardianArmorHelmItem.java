package io.grasspow.extrabotany.common.item.equipment.armor;

import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ShootingGuardianArmorHelmItem extends ShootingGuardianArmorItem {
    public ShootingGuardianArmorHelmItem(Properties props) {
        super(props, Type.HELMET);
    }

    @Override
    public String getArmorTextureAfterInk(ItemStack stack, EquipmentSlot slot) {
        return LibMisc.MOD_ID + ":textures/model/shooting_guardian_armor_helmet.png";
    }
}
