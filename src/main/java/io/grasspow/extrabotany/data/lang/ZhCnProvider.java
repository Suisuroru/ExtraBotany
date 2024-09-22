package io.grasspow.extrabotany.data.lang;

import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import static io.grasspow.extrabotany.data.LanguageHelper.zh_cn;

public class ZhCnProvider extends LanguageProvider {
    public ZhCnProvider(PackOutput output) {
        super(output, LibMisc.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        zh_cn.forEach(this::add);
    }
}
