package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.common.crafting.CocktailRecipe;
import io.grasspow.extrabotany.common.crafting.InfiniteWineRecipe;
import io.grasspow.extrabotany.common.crafting.PedestalClickRecipe;
import io.grasspow.extrabotany.common.crafting.SplashGrenadeRecipe;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ExtraBotanyRecipeTypes {
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, LibMisc.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LibMisc.MOD_ID);

    public static final RegistryObject<RecipeType<PedestalClickRecipe>> PEDESTAL_CLICK = RECIPE_TYPES.register(LibRecipeNames.PEDESTAL_CLICK, () -> registerRecipeType(LibRecipeNames.PEDESTAL_CLICK));
    public static final RegistryObject<RecipeSerializer<PedestalClickRecipe>> PEDESTAL_CLICK_SERIALIZER = RECIPE_SERIALIZERS.register(LibRecipeNames.PEDESTAL_CLICK, PedestalClickRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<CocktailRecipe>> COCKTAIL_UPGRADE = RECIPE_TYPES.register(LibRecipeNames.COCKTAIL_UPGRADE, () -> registerRecipeType(LibRecipeNames.COCKTAIL_UPGRADE));
    public static final RegistryObject<RecipeSerializer<CocktailRecipe>> COCKTAIL_UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register(LibRecipeNames.COCKTAIL_UPGRADE, () -> CocktailRecipe.SERIALIZER);

    public static final RegistryObject<RecipeType<SplashGrenadeRecipe>> SPLASH_GRENADE_UPGRADE = RECIPE_TYPES.register(LibRecipeNames.SPLASH_GRENADE_UPGRADE, () -> registerRecipeType(LibRecipeNames.INFINITE_WINE_UPGRADE));
    public static final RegistryObject<RecipeSerializer<SplashGrenadeRecipe>> SPLASH_GRENADE_UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register(LibRecipeNames.SPLASH_GRENADE_UPGRADE, () -> SplashGrenadeRecipe.SERIALIZER);

    public static final RegistryObject<RecipeType<InfiniteWineRecipe>> INFINITE_WINE_UPGRADE = RECIPE_TYPES.register(LibRecipeNames.INFINITE_WINE_UPGRADE, () -> registerRecipeType(LibRecipeNames.INFINITE_WINE_UPGRADE));
    public static final RegistryObject<RecipeSerializer<InfiniteWineRecipe>> INFINITE_WINE_UPGRADE_SERIALIZER = RECIPE_SERIALIZERS.register(LibRecipeNames.INFINITE_WINE_UPGRADE, () -> InfiniteWineRecipe.SERIALIZER);

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return LibMisc.MOD_ID + ":" + identifier;
            }
        };
    }

    public static DeferredRegister<RecipeSerializer<?>> getRecipeSerializers() {
        return RECIPE_SERIALIZERS;
    }

    public static DeferredRegister<RecipeType<?>> getRecipeTypes() {
        return RECIPE_TYPES;
    }
}
