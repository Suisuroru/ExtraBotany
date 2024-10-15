package io.grasspow.extrabotany.common.advancements;

import vazkii.botania.mixin.CriteriaTriggersAccessor;

public class ExtraBotanyCriteriaTriggers {
    public static void init() {
        CriteriaTriggersAccessor.botania_register(TinkleUseTrigger.INSTANCE);
    }
}
