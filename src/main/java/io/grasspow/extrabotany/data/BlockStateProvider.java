package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, LibMisc.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        List.of(
                ExtraBotanyBlocks.PHOTONIUM_BLOCK,
                ExtraBotanyBlocks.SHADOWIUM_BLOCK,
                ExtraBotanyBlocks.AERIALITE_BLOCK,
                ExtraBotanyBlocks.ORICHALCOS_BLOCK,
                ExtraBotanyBlocks.DIMENSION_CATALYST
        ).forEach(this::normal);
        List.of(
                ExtraBotanyBlocks.PEDESTAL,
                ExtraBotanyBlocks.MANA_BUFFER,
                ExtraBotanyBlocks.QUANTUM_MANA_BUFFER,
                ExtraBotanyBlocks.TROPHY,
                ExtraBotanyBlocks.LIVINGROCK_BARREL,
                ExtraBotanyBlocks.POWER_FRAME
        ).forEach(this::custom);
    }

    /**
     * generate block and item
     */
    private void normal(RegistryObject<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    /**
     * generate item with custom block
     */
    private void custom(RegistryObject<Block> block) {
        String path = block.getId().getPath();
        simpleBlockWithItem(block.get(), models().withExistingParent(path, resId("block/" + path + "_default")));
    }
}
