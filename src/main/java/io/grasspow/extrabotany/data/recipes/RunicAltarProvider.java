package io.grasspow.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.helper.ItemNBTHelper;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;


public class RunicAltarProvider extends vazkii.botania.data.recipes.RunicAltarProvider {
    public RunicAltarProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany rune altar recipes";
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        Ingredient GILDED_MASHED_POTATO = Ingredient.of(ModItems.GILDED_MASHED_POTATO.get());
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.ULTIMATE_HAMMER), new ItemStack(ModItems.ULTIMATE_HAMMER.get()), 100000, GILDED_MASHED_POTATO, GILDED_MASHED_POTATO, GILDED_MASHED_POTATO, Ingredient.of(Items.GOLD_BLOCK), Ingredient.of(ModItems.TERRASTEEL_HAMMER.get())));
    }

    private static ResourceLocation idFor(String s) {
        return resId("runic_altar/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack output;
        private final int mana;
        private final Ingredient[] inputs;

        protected FinishedRecipe(ResourceLocation id, ItemStack output, int mana, Ingredient... inputs) {
            this.id = id;
            this.output = output;
            this.mana = mana;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("output", ItemNBTHelper.serializeStack(output));
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
                ingredients.add(ingr.toJson());
            }
            json.addProperty("mana", mana);
            json.add("ingredients", ingredients);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BotaniaRecipeTypes.RUNE_SERIALIZER;
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
