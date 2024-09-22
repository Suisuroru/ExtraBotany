package io.grasspow.extrabotany.common.libs;

import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {

    public static ResourceLocation resId(String path) {
        return new ResourceLocation(LibMisc.MOD_ID, path);
    }
}
