package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SoundDefinitionsProvider extends net.minecraftforge.common.data.SoundDefinitionsProvider {
    protected SoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, LibMisc.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
//        add(PEDESTAL_PICK.get(), SoundDefinition.definition().with(sound(PEDESTAL_PICK.getId())));
    }
}
