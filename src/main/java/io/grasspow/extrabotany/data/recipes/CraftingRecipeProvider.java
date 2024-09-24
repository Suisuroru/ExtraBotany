package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.registry.ModBlocks;
import io.grasspow.extrabotany.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
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
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MANASTEEL_HAMMER.get())
                .define('M', BotaniaTags.Items.INGOTS_MANASTEEL)
                .define('W', BotaniaItems.livingwoodTwig)
                .pattern("MMM")
                .pattern("MMM")
                .pattern(" W ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_MANASTEEL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ELEMENTIUM_HAMMER.get())
                .define('M', BotaniaTags.Items.INGOTS_ELEMENTIUM)
                .define('W', BotaniaItems.livingwoodTwig)
                .pattern("MMM")
                .pattern("MMM")
                .pattern(" W ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_ELEMENTIUM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TERRASTEEL_HAMMER.get())
                .define('M', BotaniaTags.Items.INGOTS_ELEMENTIUM)
                .define('W', BotaniaItems.livingwoodTwig)
                .pattern("MMM")
                .pattern("MMM")
                .pattern(" W ")
                .unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_TERRASTEEL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PYLON.get())
                .define('G', BotaniaTags.Items.PETALS_GREEN)
                .define('S', ModItems.SPIRIT.get())
                .pattern("GGG")
                .pattern("GSG")
                .pattern("GGG")
                .unlockedBy("has_item", conditionsFromItem(ModItems.SPIRIT.get()))
                .save(consumer);
        compression(ModBlocks.PHOTONIUM_BLOCK, ModItems.PHOTONIUM).save(consumer);
        compression(ModBlocks.SHADOWIUM_BLOCK, ModItems.SHADOWIUM).save(consumer);
    }

    private ShapedRecipeBuilder compression(RegistryObject<Block> block, RegistryObject<Item> item) {
        return compression(block.get(),item.get());
    }

    @Override
    protected ResourceLocation prefix(String path) {
        return resId(path);
    }
}
