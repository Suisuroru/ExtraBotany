package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.common.block.*;
import io.grasspow.extrabotany.common.libs.LibBlockNames;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.forge.block.ForgeSpecialFlowerBlock;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;

public class ExtraBotanyBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

    public static DeferredRegister<Block> getBlocks() {
        return BLOCKS;
    }

    public static final ArrayList<RegistryObject<Block>> MOD_BLOCKS = new ArrayList<>();
    public static final ArrayList<RegistryObject<Block>> MOD_FLOWERS = new ArrayList<>();

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

    //flowers
    public static final RegistryObject<Block> ANNOYING_FLOWER = regDefFlower(LibBlockNames.ANNOYING_FLOWER, MobEffects.HUNGER, 360, ExtraBotanyEntities.Blocks.Flowers.ANNOYING_FLOWER::get);
    public static final RegistryObject<Block> FLOATING_ANNOYING_FLOWER = regDefFlowerFloat(LibBlockNames.ANNOYING_FLOWER, ExtraBotanyEntities.Blocks.Flowers.ANNOYING_FLOWER::get);
    public static final RegistryObject<Block> SERENITIAN = regDefFlower(LibBlockNames.SERENITIAN, MobEffects.HERO_OF_THE_VILLAGE, 360, ExtraBotanyEntities.Blocks.Flowers.SERENITIAN::get);
    public static final RegistryObject<Block> FLOATING_SERENITIAN = regDefFlowerFloat(LibBlockNames.SERENITIAN, ExtraBotanyEntities.Blocks.Flowers.SERENITIAN::get);
    public static final RegistryObject<Block> BELL_FLOWER = regDefFlower(LibBlockNames.BELL_FLOWER, MobEffects.MOVEMENT_SPEED, 360, ExtraBotanyEntities.Blocks.Flowers.BELL_FLOWER::get);
    public static final RegistryObject<Block> FLOATING_BELL_FLOWER = regDefFlowerFloat(LibBlockNames.BELL_FLOWER, ExtraBotanyEntities.Blocks.Flowers.BELL_FLOWER::get);
    public static final RegistryObject<Block> EDELWEISS = regDefFlower(LibBlockNames.EDELWEISS, MobEffects.MOVEMENT_SLOWDOWN, 80, ExtraBotanyEntities.Blocks.Flowers.EDELWEISS::get);
    public static final RegistryObject<Block> FLOATING_EDELWEISS = regDefFlowerFloat(LibBlockNames.EDELWEISS, ExtraBotanyEntities.Blocks.Flowers.EDELWEISS::get);
    public static final RegistryObject<Block> GEMINI_ORCHID = regDefFlower(LibBlockNames.GEMINI_ORCHID, MobEffects.GLOWING, 1600, ExtraBotanyEntities.Blocks.Flowers.GEMINI_ORCHID::get);
    public static final RegistryObject<Block> FLOATING_GEMINI_ORCHID = regDefFlowerFloat(LibBlockNames.GEMINI_ORCHID, ExtraBotanyEntities.Blocks.Flowers.GEMINI_ORCHID::get);
    public static final RegistryObject<Block> SUN_BLESS = regDefFlower(LibBlockNames.SUN_BLESS, MobEffects.LUCK, 1600, ExtraBotanyEntities.Blocks.Flowers.SUN_BLESS::get);
    public static final RegistryObject<Block> FLOATING_SUN_BLESS = regDefFlowerFloat(LibBlockNames.SUN_BLESS, ExtraBotanyEntities.Blocks.Flowers.SUN_BLESS::get);
    public static final RegistryObject<Block> MOON_BLESS = regDefFlower(LibBlockNames.MOON_BLESS, MobEffects.UNLUCK, 1600, ExtraBotanyEntities.Blocks.Flowers.MOON_BLESS::get);
    public static final RegistryObject<Block> FLOATING_MOON_BLESS = regDefFlowerFloat(LibBlockNames.MOON_BLESS, ExtraBotanyEntities.Blocks.Flowers.MOON_BLESS::get);
    public static final RegistryObject<Block> OMNI_VIOLET = regDefFlower(LibBlockNames.OMNI_VIOLET, MobEffects.REGENERATION, 360, ExtraBotanyEntities.Blocks.Flowers.OMNI_VIOLET::get);
    public static final RegistryObject<Block> FLOATING_OMNI_VIOLET = regDefFlowerFloat(LibBlockNames.OMNI_VIOLET, ExtraBotanyEntities.Blocks.Flowers.OMNI_VIOLET::get);
    public static final RegistryObject<Block> REIKAR_LILY = regDefFlower(LibBlockNames.REIKAR_LILY, MobEffects.JUMP, 1600, ExtraBotanyEntities.Blocks.Flowers.REIKAR_LILY::get);
    public static final RegistryObject<Block> FLOATING_REIKAR_LILY = regDefFlowerFloat(LibBlockNames.REIKAR_LILY, ExtraBotanyEntities.Blocks.Flowers.REIKAR_LILY::get);
    public static final RegistryObject<Block> TINKLE_FLOWER = regDefFlower(LibBlockNames.TINKLE_FLOWER, MobEffects.DIG_SPEED, 360, ExtraBotanyEntities.Blocks.Flowers.TINKLE_FLOWER::get);
    public static final RegistryObject<Block> FLOATING_TINKLE_FLOWER = regDefFlowerFloat(LibBlockNames.TINKLE_FLOWER, ExtraBotanyEntities.Blocks.Flowers.TINKLE_FLOWER::get);

    private static RegistryObject<Block> regDefBlock(String name, BlockBehaviour.Properties props) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new Block(props));
        ExtraBotanyItems.regDefBlockItem(block);
        MOD_BLOCKS.add(block);
        return block;
    }

    private static RegistryObject<Block> regDefBlock(String name, Function<BlockBehaviour.Properties, ? extends Block> func, BlockBehaviour.Properties props) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> func.apply(props));
        ExtraBotanyItems.regDefBlockItem(block);
        MOD_BLOCKS.add(block);
        return block;
    }

    private static RegistryObject<Block> regDefBlock(String name, BiFunction<ManaBufferBlock.Variant, BlockBehaviour.Properties, ? extends Block> func, ManaBufferBlock.Variant variant, BlockBehaviour.Properties props) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> func.apply(variant, props));
        ExtraBotanyItems.regDefBlockItem(block);
        MOD_BLOCKS.add(block);
        return block;
    }

    private static RegistryObject<Block> regDefFlower(String name, MobEffect effect, int duration, Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType) {
        RegistryObject<Block> block = BLOCKS.register(name, () -> new ForgeSpecialFlowerBlock(effect, duration, FLOWER_PROPS, beType));
        ExtraBotanyItems.regFlowerItem(block);
        MOD_FLOWERS.add(block);
        return block;
    }

    private static RegistryObject<Block> regDefFlowerFloat(String name, Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType) {
        RegistryObject<Block> block = BLOCKS.register("floating_" + name, () -> new FloatingSpecialFlowerBlock(FLOATING_PROPS, beType));
        ExtraBotanyItems.regFlowerItem(block);
        MOD_FLOWERS.add(block);
        return block;
    }

    private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);
    private static final BlockBehaviour.Properties FLOATING_PROPS = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRAVEL).lightLevel(s -> 15);

    private static BlockBehaviour.Properties livingrock() {
        return BlockBehaviour.Properties.of().strength(2, 10).sound(SoundType.STONE).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops();
    }
}
