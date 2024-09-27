package io.grasspow.extrabotany.data.loot;

import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks.*;

public class BlockLootTableProvider extends BlockLootSubProvider {
    public BlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        Stream.of(
                PHOTONIUM_BLOCK, SHADOWIUM_BLOCK, PEDESTAL
        ).map(RegistryObject::get).forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ExtraBotanyBlocks.MOD_BLOCKS.stream().map(RegistryObject::get).collect(Collectors.toSet());
    }
}
