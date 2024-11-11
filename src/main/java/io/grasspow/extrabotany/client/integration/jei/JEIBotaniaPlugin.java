package io.grasspow.extrabotany.client.integration.jei;

import io.grasspow.extrabotany.client.integration.jei.crafting.*;
import io.grasspow.extrabotany.common.crafting.*;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.item.brew.BaseBrewItemEX;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import vazkii.botania.client.integration.jei.RunicAltarRecipeCategory;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.brew.BaseBrewItem;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

@JeiPlugin
public class JEIBotaniaPlugin implements IModPlugin {
    private static final ResourceLocation ID = resId("main");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registry) {
        IIngredientSubtypeInterpreter<ItemStack> interpreter = (stack, ctx) -> BaseBrewItemEX.getSubtype(stack);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.COCKTAIL.get(), interpreter);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.SPLASH_GRENADE.get(), interpreter);
        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.INFINITE_WINE.get(), interpreter);

        registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.MANA_DRIVE_RING.get(), (stack, ctx) -> String.valueOf(XplatAbstractions.INSTANCE.findManaItem(stack).getMana()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new PedestalClickRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addCategoryExtension(CocktailUpgradeRecipe.class, CocktailUpgradeRecipeWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(InfiniteWineUpgradeRecipe.class, InfiniteWineUpgradeRecipeWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(SplashGrenadeRecipe.class, SplashGrenadeUpgradeWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(GoldClothWipeRelicRecipe.class, GoldClothWipeRelicRecipeWrapper::new);
        registration.getCraftingCategory().addCategoryExtension(PotionLensBindBrewRecipe.class, PotionLensBindBrewRecipeWrapper::new);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        registry.addRecipes(PedestalClickRecipeCategory.TYPE, sortRecipes(ExtraBotanyRecipeTypes.PEDESTAL_CLICK.get(), BY_ID));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ExtraBotanyBlocks.PEDESTAL.get()), PedestalClickRecipeCategory.TYPE);
    }

    private static <T extends Recipe<C>, C extends Container> List<T> sortRecipes(RecipeType<T> type, Comparator<? super T> comparator) {
        @SuppressWarnings("unchecked")
        Collection<T> recipes = (Collection<T>) BotaniaRecipeTypes.getRecipes(Minecraft.getInstance().level, type).values();
        List<T> list = new ArrayList<>(recipes);
        list.sort(comparator);
        return list;
    }

    private static final Comparator<Recipe<?>> BY_ID = Comparator.comparing(Recipe::getId);
}
