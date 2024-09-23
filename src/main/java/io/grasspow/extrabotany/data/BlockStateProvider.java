package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.block.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LibMisc.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        normal(ExtraBotanyBlocks.PHOTONIUM_BLOCK);
        normal(ExtraBotanyBlocks.SHADOWIUM_BLOCK);
        simpleBlock(ExtraBotanyBlocks.PEDESTAL.get());
    }

    private void normal(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
