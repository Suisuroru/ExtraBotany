package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.common.block.*;
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
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;


public class ExtraBotanyBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final ArrayList<RegistryObject<? extends Block>> MOD_BLOCKS = new ArrayList<>();

    public static final RegistryObject<Block> PHOTONIUM_BLOCK = regDefBlock(LibBlockNames.PHOTONIUM_BLOCK, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    public static final RegistryObject<Block> SHADOWIUM_BLOCK = regDefBlock(LibBlockNames.SHADOWIUM_BLOCK, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    public static final RegistryObject<Block> AERIALITE_BLOCK = regDefBlock(LibBlockNames.AERIALITE_BLOCK, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    public static final RegistryObject<Block> ORICHALCOS_BLOCK = regDefBlock(LibBlockNames.ORICHALCOS_BLOCK, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK));
    public static final RegistryObject<Block> PEDESTAL = regDefBlock(LibBlockNames.PEDESTAL, PedestalBlock::new, livingrock());
    public static final RegistryObject<Block> MANA_BUFFER = regDefBlock(LibBlockNames.MANA_BUFFER, ManaBufferBlock::new, ManaBufferBlock.Variant.DEFAULT, livingrock());
    public static final RegistryObject<Block> QUANTUM_MANA_BUFFER = regDefBlock(LibBlockNames.QUANTUM_MANA_BUFFER, ManaBufferBlock::new, ManaBufferBlock.Variant.QUANTUM, livingrock());
    public static final RegistryObject<Block> TROPHY = regDefBlock(LibBlockNames.TROPHY, TrophyBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL));
    public static final RegistryObject<Block> LIVINGROCK_BARREL = regDefBlock(LibBlockNames.LIVINGROCK_BARREL, LivingrockBarrelBlock::new, livingrock());
    public static final RegistryObject<Block> DIMENSION_CATALYST = regDefBlock(LibBlockNames.DIMENSION_CATALYST, DimensionCatalystBlock::new, livingrock());
    public static final RegistryObject<Block> POWER_FRAME = regDefBlock(LibBlockNames.POWER_FRAME, PowerFrameBlock::new, livingrock().noOcclusion());

    private static RegistryObject<Block> regDefBlock(String name, BlockBehaviour.Properties props) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Block(props));
        MOD_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> regDefBlock(String name, Function<BlockBehaviour.Properties, ? extends B> func, BlockBehaviour.Properties props) {
        RegistryObject<B> block = BLOCKS.register(name, () -> func.apply(props));
        MOD_BLOCKS.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> regDefBlock(String name, BiFunction<ManaBufferBlock.Variant, BlockBehaviour.Properties, ? extends B> func, ManaBufferBlock.Variant variant, BlockBehaviour.Properties props) {
        RegistryObject<B> block = BLOCKS.register(name, () -> func.apply(variant, props));
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
