package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;
import static io.grasspow.extrabotany.common.registry.ExtraBotanySounds.*;

public class SoundDefinitionsProvider extends net.minecraftforge.common.data.SoundDefinitionsProvider {
    public SoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, LibMisc.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(CYCLONE.get(), SoundDefinition.definition().with(sound(resId("cyclone"))).subtitle("extrabotany.subtitle.cyclone"));
        add(RIDEON.get(), SoundDefinition.definition().with(sound(resId("rideon"))).subtitle("extrabotany.subtitle.rideon"));
        add(SHOOT.get(), SoundDefinition.definition().with(sound(resId("shoot"))).subtitle("extrabotany.subtitle.shoot"));
        add(SLASH.get(), SoundDefinition.definition().with(sound(resId("slash"))).subtitle("extrabotany.subtitle.slash"));
        add(FLAMESCIONULT.get(), SoundDefinition.definition().with(sound(resId("flamescionult"))).subtitle("extrabotany.subtitle.flamescionult"));

        add(SWORDLAND.get(), SoundDefinition.definition().with(sound(resId("music/" + "swordland")).stream()));
        add(SALVATION.get(), SoundDefinition.definition().with(sound(resId("music/" + "salvation")).stream()));
    }
}
