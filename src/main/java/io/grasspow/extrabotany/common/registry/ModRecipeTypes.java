package io.grasspow.extrabotany.common.registry;

import io.grasspow.extrabotany.common.crafting.PedestalClickRecipe;
import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, LibMisc.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, LibMisc.MOD_ID);

    public static final RegistryObject<RecipeType<PedestalClickRecipe>> PEDESTAL_CLICK = RECIPE_TYPES.register(LibRecipeNames.PEDESTAL_CLICK, () -> registerRecipeType(LibRecipeNames.PEDESTAL_CLICK));
    public static final RegistryObject<RecipeSerializer<PedestalClickRecipe>> PEDESTAL_CLICK_SERIALIZER = RECIPE_SERIALIZERS.register(LibRecipeNames.PEDESTAL_CLICK, PedestalClickRecipe.Serializer::new);

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>() {
            public String toString() {
                return LibMisc.MOD_ID + ":" + identifier;
            }
        };
    }

    public static void init(IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}
