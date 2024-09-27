package io.grasspow.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import io.grasspow.extrabotany.common.libs.ModTags;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import io.grasspow.extrabotany.common.registry.ExtraBotanyRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.data.recipes.BotaniaRecipeProvider;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class PedestalClickProvider extends BotaniaRecipeProvider {
    public PedestalClickProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public @NotNull String getName() {
        return "ExtraBotany pedestal recipes";
    }

    @Override
    protected void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.SPIRIT), new ItemStack(ExtraBotanyItems.SPIRIT.get()), Ingredient.of(ExtraBotanyItems.SPIRIT_FUEL.get()), Ingredient.of(ModTags.Items.HAMMER)));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.GILDED_MASHED_POTATO), new ItemStack(ExtraBotanyItems.GILDED_MASHED_POTATO.get()), Ingredient.of(ExtraBotanyItems.GILDED_POTATO.get()), Ingredient.of(ModTags.Items.HAMMER)));
    }

    private static ResourceLocation idFor(String s) {
        return resId(LibRecipeNames.PEDESTAL_CLICK + "/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack output;
        private final NonNullList<Ingredient> inputItems;

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient... inputItems) {
            this.id = id;
            this.output = output;
            this.inputItems = NonNullList.of(Ingredient.EMPTY, inputItems);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("output", ItemNBTHelper.serializeStack(output));
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputItems) {
                ingredients.add(ingr.toJson());
            }
            json.add("ingredients", ingredients);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ExtraBotanyRecipeTypes.PEDESTAL_CLICK_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
