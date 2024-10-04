package io.grasspow.extrabotany.data.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks.MOD_BLOCKS;
import static io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks.MOD_FLOWERS;

public class BlockLootTableProvider extends BlockLootSubProvider {
    public BlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        MOD_BLOCKS.stream().map(RegistryObject::get).forEach(this::dropSelf);
        MOD_FLOWERS.stream().map(RegistryObject::get).forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        var set = new HashSet<RegistryObject<Block>>();
        set.addAll(MOD_BLOCKS);
        set.addAll(MOD_FLOWERS);
        return set.stream().map(RegistryObject::get).collect(Collectors.toSet());
    }
}
