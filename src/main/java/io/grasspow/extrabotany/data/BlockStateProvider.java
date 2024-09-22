package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.block.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LibMisc.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ExtraBotanyBlocks.PHOTONIUM_BLOCK.get());
        simpleBlock(ExtraBotanyBlocks.SHADOWIUM_BLOCK.get());
    }
}
