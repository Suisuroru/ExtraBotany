package io.grasspow.extrabotany.data.lang;

import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import static io.grasspow.extrabotany.data.lang.LanguageHelper.en_us;

public class EnUsProvider extends LanguageProvider {
    public EnUsProvider(PackOutput output) {
        super(output, LibMisc.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        en_us.forEach(this::add);
    }
}
