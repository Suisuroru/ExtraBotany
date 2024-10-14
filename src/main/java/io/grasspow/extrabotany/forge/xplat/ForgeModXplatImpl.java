package io.grasspow.extrabotany.forge.xplat;

import io.grasspow.extrabotany.api.capability.ExtraBotanyCapabilities;
import io.grasspow.extrabotany.api.capability.INatureOrb;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.xplat.ModXplatAbstractions;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgeModXplatImpl implements ModXplatAbstractions {
    @Override
    public boolean isForge() {
        return true;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public INatureOrb findNatureOrbItem(ItemStack stack) {
        return stack.getCapability(ExtraBotanyCapabilities.NATURE_ORB).orElse(null);
    }

    @Override
    public String getExtraBotanyVersion() {
        return ModList.get().getModContainerById(LibMisc.MOD_ID).get().getModInfo().getVersion().toString();
    }
}
