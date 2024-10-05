package io.grasspow.extrabotany.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {
    public LootTableProvider(PackOutput pOutput) {
        super(pOutput, Set.of(), List.of(
                new SubProviderEntry(BlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
