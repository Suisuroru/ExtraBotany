package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.crafting.CocktailRecipe;
import io.grasspow.extrabotany.common.crafting.InfiniteWineRecipe;
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
import net.minecraft.world.level.block.Block;
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
        compression(ExtraBotanyBlocks.PHOTONIUM_BLOCK, ExtraBotanyItems.PHOTONIUM).save(consumer);
        compression(ExtraBotanyBlocks.SHADOWIUM_BLOCK, ExtraBotanyItems.SHADOWIUM).save(consumer);
    }

    protected void buildSpecialCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SpecialRecipeBuilder.special(CocktailRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.COCKTAIL_UPGRADE);
        SpecialRecipeBuilder.special(SplashGrenadeRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.SPLASH_GRENADE_UPGRADE);
        SpecialRecipeBuilder.special(InfiniteWineRecipe.SERIALIZER)
                .save(consumer, "dynamic/" + LibRecipeNames.INFINITE_WINE_UPGRADE);
    }

    private ShapedRecipeBuilder compression(RegistryObject<Block> block, RegistryObject<Item> item) {
        return compression(block.get(),item.get());
    }

    @Override
    protected ResourceLocation prefix(String path) {
        return resId(path);
    }
}
