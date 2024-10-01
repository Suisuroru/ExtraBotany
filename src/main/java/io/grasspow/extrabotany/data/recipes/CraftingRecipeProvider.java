package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.crafting.CocktailUpgradeRecipe;
import io.grasspow.extrabotany.common.crafting.GoldClothWipeRelicRecipe;
import io.grasspow.extrabotany.common.crafting.InfiniteWineUpgradeRecipe;
import io.grasspow.extrabotany.common.crafting.SplashGrenadeRecipe;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class CraftingRecipeProvider extends vazkii.botania.data.recipes.CraftingRecipeProvider {
    public CraftingRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany crafting recipes";
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        genCraftTableRecipes(consumer);
    }

    private void genCraftTableRecipes(Consumer<FinishedRecipe> consumer) {
        buildSpecialCraftingRecipes(consumer);
        buildCommonCraftingRecipes(consumer);
        ingotStorage(ExtraBotanyBlocks.PHOTONIUM_BLOCK, ExtraBotanyItems.PHOTONIUM, consumer);
        ingotStorage(ExtraBotanyBlocks.SHADOWIUM_BLOCK, ExtraBotanyItems.SHADOWIUM, consumer);
        ingotStorage(ExtraBotanyBlocks.ORICHALCOS_BLOCK, ExtraBotanyItems.ORICHALCOS, consumer);
    }

    private void buildCommonCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ExtraBotanyBlocks.PEDESTAL.get())
                .define('G', Tags.Items.NUGGETS_GOLD)
                .define('L', BotaniaBlocks.livingrock)
                .pattern("LGL")
                .pattern(" L ")
                .pattern("LLL")
                .unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.livingrock))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.MANASTEEL_HAMMER.get())
                .define('M', BotaniaTags.Items.INGOTS_MANASTEEL)
                .define('W', BotaniaItems.livingwoodTwig)
                .pattern("MMM")
                .pattern("MMM")
                .pattern(" W ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_MANASTEEL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.ELEMENTIUM_HAMMER.get())
                .define('M', BotaniaTags.Items.INGOTS_ELEMENTIUM)
                .define('W', BotaniaItems.livingwoodTwig)
                .pattern("MMM")
                .pattern("MMM")
                .pattern(" W ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_ELEMENTIUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.TERRASTEEL_HAMMER.get())
                .define('M', BotaniaTags.Items.INGOTS_ELEMENTIUM)
                .define('W', BotaniaItems.livingwoodTwig)
                .pattern("MMM")
                .pattern("MMM")
                .pattern(" W ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_TERRASTEEL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.PYLON.get())
                .define('G', BotaniaTags.Items.PETALS_GREEN)
                .define('S', ExtraBotanyItems.SPIRIT.get())
                .pattern("GGG")
                .pattern("GSG")
                .pattern("GGG")
                .unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.SPIRIT.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.EMPTY_BOTTLE.get())
                .define('G', BotaniaBlocks.manaGlass)
                .pattern("G G")
                .pattern("G G")
                .pattern(" G ")
                .unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.manaGlass))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ExtraBotanyBlocks.LIVINGROCK_BARREL.get())
                .define('G', BotaniaBlocks.livingrock)
                .pattern("G G")
                .pattern("G G")
                .pattern("GGG")
                .unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.livingrock))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ExtraBotanyBlocks.MANA_BUFFER.get())
                .define('P', BotaniaBlocks.fabulousPool)
                .define('L', BotaniaItems.lensNormal)
                .define('I', BotaniaItems.gaiaIngot)
                .pattern("PLP")
                .pattern("PIP")
                .pattern("PLP")
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.gaiaIngot))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ExtraBotanyBlocks.QUANTUM_MANA_BUFFER.get())
                .define('B', ExtraBotanyBlocks.MANA_BUFFER.get())
                .define('O', ExtraBotanyItems.ORICHALCOS.get())
                .pattern("BBB")
                .pattern("BOB")
                .pattern("BBB")
                .unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.ORICHALCOS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.MANA_READER.get())
                .define('D', BotaniaTags.Items.GEMS_MANA_DIAMOND)
                .define('T', BotaniaTags.Items.DUSTS_MANA)
                .define('S', BotaniaItems.livingwoodTwig)
                .pattern(" TD")
                .pattern(" ST")
                .pattern("S  ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.GEMS_MANA_DIAMOND))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ExtraBotanyBlocks.DIMENSION_CATALYST.get())
                .define('L', BotaniaBlocks.livingrock)
                .define('E', Items.ENDER_EYE)
                .define('N', ExtraBotanyItems.NIGHTMARE_FUEL.get())
                .define('A', BotaniaBlocks.alchemyCatalyst)
                .pattern("LEL")
                .pattern("NAN")
                .pattern("LNL")
                .unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.alchemyCatalyst))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.GOLD_CLOTH.get(), 4)
                .define('L', BotaniaItems.lifeEssence)
                .define('C', BotaniaItems.manaweaveCloth)
                .define('G', Tags.Items.INGOTS_GOLD)
                .pattern("LCL")
                .pattern("CGC")
                .pattern("LCL")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.GEMS_MANA_DIAMOND))
                .save(consumer);
    }

    protected void buildSpecialCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SpecialRecipeBuilder.special(CocktailUpgradeRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.COCKTAIL_UPGRADE);
        SpecialRecipeBuilder.special(SplashGrenadeRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.SPLASH_GRENADE_UPGRADE);
        SpecialRecipeBuilder.special(InfiniteWineUpgradeRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.INFINITE_WINE_UPGRADE);
        SpecialRecipeBuilder.special(GoldClothWipeRelicRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.GOLD_CLOTH_WIPE_RELIC);
    }

    private void ingotStorage(RegistryObject<Block> block, RegistryObject<Item> item, Consumer<FinishedRecipe> consumer) {
        compression(block.get(), item.get()).save(consumer);
        deconstruct(consumer, block.get(), item.get(), block.getId().getPath() + "_deconstruct");
    }

    @Override
    protected ResourceLocation prefix(String path) {
        return resId(path);
    }
}
