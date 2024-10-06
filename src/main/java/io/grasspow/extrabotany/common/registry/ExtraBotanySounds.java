package io.grasspow.extrabotany.common.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static io.grasspow.extrabotany.common.libs.LibMisc.MOD_ID;
import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public final class ExtraBotanySounds {
    public static final List<RegistryObject<SoundEvent>> SOUND_EVENTS = new ArrayList<>();
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static final RegistryObject<SoundEvent> CYCLONE = reg("cyclone");
    public static final RegistryObject<SoundEvent> RIDEON = reg("rideon");
    public static final RegistryObject<SoundEvent> SHOOT = reg("shoot");
    public static final RegistryObject<SoundEvent> SLASH = reg("slash");
    public static final RegistryObject<SoundEvent> FLAMESCIONULT = reg("flamescionult");

    public static final RegistryObject<SoundEvent> SWORDLAND = reg("music.ego");
    public static final RegistryObject<SoundEvent> SALVATION = reg("music.herrscher");

    private static RegistryObject<SoundEvent> reg(String name) {
        RegistryObject<SoundEvent> sound = SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(resId(name)));
        SOUND_EVENTS.add(sound);
        return sound;
    }

    public static DeferredRegister<SoundEvent> getSounds() {
        return SOUNDS;
    }
}
