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
    private static final List<RegistryObject<SoundEvent>> SOUND_EVENTS = new ArrayList<>();
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

//    public static final RegistryObject<SoundEvent> PEDESTAL_PICK = reg(LibSoundEventNames.PEDESTAL_PICK);
//    public static final RegistryObject<SoundEvent> PEDESTAL_PUT = reg(LibSoundEventNames.PEDESTAL_PUT);

    private static RegistryObject<SoundEvent> reg(String name) {
        RegistryObject<SoundEvent> sound = SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(resId(name)));
        SOUND_EVENTS.add(sound);
        return sound;
    }

    public static DeferredRegister<SoundEvent> getSounds() {
        return SOUNDS;
    }
}
