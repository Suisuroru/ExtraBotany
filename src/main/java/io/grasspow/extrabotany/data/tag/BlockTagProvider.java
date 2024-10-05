package io.grasspow.extrabotany.data.tag;

import io.grasspow.extrabotany.common.libs.ExtraBotanyTags;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.Comparator;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks.*;


public class BlockTagProvider extends IntrinsicHolderTagsProvider<Block> {
    public BlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider,
                            ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, provider, (block) -> block.builtInRegistryHolder().key(), LibMisc.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "ExtraBotany block tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        registerVanillaTag();
        registerBotaniaTag();
        tag(ExtraBotanyTags.Blocks.PHOTONIUM_BLOCK).add(PHOTONIUM_BLOCK.get());
        tag(ExtraBotanyTags.Blocks.SHADOWIUM_BLOCK).add(SHADOWIUM_BLOCK.get());
        tag(ExtraBotanyTags.Blocks.AERIALITE_BLOCK).add(AERIALITE_BLOCK.get());
        tag(ExtraBotanyTags.Blocks.ORICHALCOS_BLOCK).add(ORICHALCOS_BLOCK.get());
    }

    private void registerVanillaTag() {
        Stream.of(
                PHOTONIUM_BLOCK, SHADOWIUM_BLOCK, ORICHALCOS_BLOCK, AERIALITE_BLOCK
        ).map(RegistryObject::get).forEach(tag(BlockTags.BEACON_BASE_BLOCKS)::add);
        Stream.of(
                PHOTONIUM_BLOCK, SHADOWIUM_BLOCK, AERIALITE_BLOCK, ORICHALCOS_BLOCK,
                PEDESTAL, MANA_BUFFER, QUANTUM_MANA_BUFFER, LIVINGROCK_BARREL,
                DIMENSION_CATALYST, POWER_FRAME
        ).map(RegistryObject::get).forEach(tag(BlockTags.MINEABLE_WITH_PICKAXE)::add);
    }

    private void registerBotaniaTag() {
        Stream.of(
                ANNOYING_FLOWER, FLOATING_ANNOYING_FLOWER
        ).map(RegistryObject::get).forEach(tag(BotaniaTags.Blocks.FUNCTIONAL_SPECIAL_FLOWERS)::add);
        Stream.of(
                SERENITIAN, FLOATING_SERENITIAN
        ).map(RegistryObject::get).forEach(tag(BotaniaTags.Blocks.MISC_SPECIAL_FLOWERS)::add);
        Stream.of(
                BELL_FLOWER, FLOATING_BELL_FLOWER,
                EDELWEISS, FLOATING_EDELWEISS,
                GEMINI_ORCHID, FLOATING_GEMINI_ORCHID,
                SUN_BLESS, FLOATING_SUN_BLESS,
                MOON_BLESS, FLOATING_MOON_BLESS,
                OMNIVIOLET, FLOATING_OMNIVIOLET,
                REIKAR_LILY, FLOATING_REIKAR_LILY,
                TINKLE_FLOWER, FLOATING_TINKLE_FLOWER
        ).map(RegistryObject::get).forEach(tag(BotaniaTags.Blocks.GENERATING_SPECIAL_FLOWERS)::add);
        tag(BotaniaTags.Blocks.SPECIAL_FLOATING_FLOWERS).add(
                MOD_FLOWERS
                        .stream()
                        .map(RegistryObject::get)
                        .filter(b -> b instanceof FloatingSpecialFlowerBlock)
                        .sorted(Comparator.comparing(BuiltInRegistries.BLOCK::getKey))
                        .toArray(Block[]::new)
        );
    }
}
