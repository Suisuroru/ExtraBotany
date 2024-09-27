package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.common.block.PedestalBlock;
import io.grasspow.extrabotany.common.libs.LibBlockNames;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.function.Function;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;


public class ExtraBotanyBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final ArrayList<RegistryObject<? extends Block>> MOD_BLOCKS = new ArrayList<>();

    public static final RegistryObject<Block> PHOTONIUM_BLOCK = regDefBlock(LibBlockNames.PHOTONIUM_BLOCK, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    public static final RegistryObject<Block> SHADOWIUM_BLOCK = regDefBlock(LibBlockNames.SHADOWIUM_BLOCK, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    public static final RegistryObject<Block> PEDESTAL = regDefBlock(LibBlockNames.PEDESTAL, PedestalBlock::new, livingrock());

    private static RegistryObject<Block> regDefBlock(String name, BlockBehaviour.Properties props) {
        RegistryObject<Block> block = BLOCKS.register(name, ()->new Block(props));
        MOD_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> regDefBlock(String name, Function<BlockBehaviour.Properties, ? extends B> func, BlockBehaviour.Properties props) {
        RegistryObject<B> block = BLOCKS.register(name,()->func.apply(props));
        MOD_BLOCKS.add(block);
        return block;
    }

    public static DeferredRegister<Block> getBlocks() {
        return BLOCKS;
    }

    private static BlockBehaviour.Properties livingrock() {
        return BlockBehaviour.Properties.of().strength(2, 10).sound(SoundType.STONE).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops();
    }
}
