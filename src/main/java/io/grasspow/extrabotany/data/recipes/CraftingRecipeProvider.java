package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.crafting.CocktailUpgradeRecipe;
import io.grasspow.extrabotany.common.crafting.GoldClothWipeRelicRecipe;
import io.grasspow.extrabotany.common.crafting.InfiniteWineUpgradeRecipe;
import io.grasspow.extrabotany.common.crafting.SplashGrenadeRecipe;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;
import vazkii.botania.mixin.RecipeProviderAccessor;

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
        specialCrafting(CocktailUpgradeRecipe.SERIALIZER, LibRecipeNames.COCKTAIL_UPGRADE, consumer);
        specialCrafting(SplashGrenadeRecipe.SERIALIZER, LibRecipeNames.SPLASH_GRENADE_UPGRADE, consumer);
        specialCrafting(InfiniteWineUpgradeRecipe.SERIALIZER, LibRecipeNames.INFINITE_WINE_UPGRADE, consumer);
        specialCrafting(GoldClothWipeRelicRecipe.SERIALIZER, LibRecipeNames.GOLD_CLOTH_WIPE_RELIC, consumer);

        ingotStorage(ExtraBotanyBlocks.PHOTONIUM_BLOCK, ExtraBotanyItems.PHOTONIUM, consumer);
        ingotStorage(ExtraBotanyBlocks.SHADOWIUM_BLOCK, ExtraBotanyItems.SHADOWIUM, consumer);
        ingotStorage(ExtraBotanyBlocks.AERIALITE_BLOCK, ExtraBotanyItems.AERIALITE, consumer);
        ingotStorage(ExtraBotanyBlocks.ORICHALCOS_BLOCK, ExtraBotanyItems.ORICHALCOS, consumer);

        buildCommonCraftingRecipes(consumer);
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
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ExtraBotanyBlocks.POWER_FRAME.get())
                .define('M', BotaniaTags.Items.INGOTS_MANASTEEL)
                .define('P', BotaniaItems.pixieDust)
                .define('L', BotaniaBlocks.livingrock)
                .pattern("MMM")
                .pattern("PLP")
                .pattern("MMM")
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.pixieDust))
                .save(consumer);

        //lens
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.LENS_MANA.get())
                .requires(BotaniaItems.lensNormal)
                .requires(BotaniaItems.runeMana)
                .requires(BotaniaItems.manaPowder)
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.lensNormal))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.UNIVERSAL_PETAL.get(), 8)
                .requires(Ingredient.of(BotaniaTags.Items.PETALS), 8)
                .requires(BotaniaItems.lifeEssence)
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.lifeEssence))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.ELEMENT_RUNE.get(), 8)
                .requires(BotaniaItems.lifeEssence)
                .requires(BotaniaItems.runeAir)
                .requires(BotaniaItems.runeEarth)
                .requires(BotaniaItems.runeWater)
                .requires(BotaniaItems.runeFire)
                .requires(BotaniaItems.runeSpring)
                .requires(BotaniaItems.runeSummer)
                .requires(BotaniaItems.runeAutumn)
                .requires(BotaniaItems.runeWinter)
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.lifeEssence))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.SIN_RUNE.get(), 8)
                .requires(BotaniaItems.lifeEssence)
                .requires(BotaniaItems.runeMana)
                .requires(BotaniaItems.runePride)
                .requires(BotaniaItems.runeGluttony)
                .requires(BotaniaItems.runeWrath)
                .requires(BotaniaItems.runeGreed)
                .requires(BotaniaItems.runeEnvy)
                .requires(BotaniaItems.runeLust)
                .requires(BotaniaItems.runeSloth)
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.lifeEssence))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.THE_CHAOS.get())
                .define('L', ExtraBotanyItems.PHOTONIUM.get())
                .define('D', ExtraBotanyItems.SHADOWIUM.get())
                .define('S', ExtraBotanyItems.SPIRIT.get())
                .pattern(" D ")
                .pattern("DSL")
                .pattern(" L ")
                .unlockedBy("has_item", conditionsFromItems(ExtraBotanyItems.PHOTONIUM.get(), ExtraBotanyItems.SHADOWIUM.get(), ExtraBotanyItems.SPIRIT.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.THE_ORIGIN.get())
                .define('L', BotaniaItems.terrasteel)
                .define('D', ExtraBotanyItems.AERIALITE.get())
                .define('S', ExtraBotanyItems.SPIRIT.get())
                .pattern(" D ")
                .pattern("DSL")
                .pattern(" L ")
                .unlockedBy("has_item", conditionsFromItems(BotaniaItems.terrasteel, ExtraBotanyItems.AERIALITE.get(), ExtraBotanyItems.SPIRIT.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.THE_END.get())
                .define('L', ExtraBotanyItems.ORICHALCOS.get())
                .define('D', BotaniaItems.gaiaIngot)
                .define('S', ExtraBotanyItems.SPIRIT.get())
                .pattern(" D ")
                .pattern("DSL")
                .pattern(" L ")
                .unlockedBy("has_item", conditionsFromItems(ExtraBotanyItems.ORICHALCOS.get(), BotaniaItems.gaiaIngot, ExtraBotanyItems.SPIRIT.get()))
                .save(consumer);
    }

    private void ingotStorage(RegistryObject<Block> block, RegistryObject<Item> item, Consumer<FinishedRecipe> consumer) {
        compression(block.get(), item.get()).save(consumer);
        deconstruct(consumer, block.get(), item.get(), block.getId().getPath() + "_deconstruct");
    }

    private void specialCrafting(RecipeSerializer<? extends CraftingRecipe> serializer, String name, Consumer<FinishedRecipe> consumer) {
        SpecialRecipeBuilder.special(serializer).save(consumer, "dynamic/" + name);
    }

    private static InventoryChangeTrigger.TriggerInstance conditionsFromItems(ItemLike... items) {
        ItemPredicate[] preds = new ItemPredicate[items.length];
        for (int i = 0; i < items.length; i++) {
            preds[i] = ItemPredicate.Builder.item().of(items[i]).build();
        }

        return RecipeProviderAccessor.botania_condition(preds);
    }

    @Override
    protected ResourceLocation prefix(String path) {
        return resId(path);
    }
}
