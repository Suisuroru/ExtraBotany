package io.grasspow.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.effect.brew.ExtraBotanyBrews;
import io.grasspow.extrabotany.common.libs.LibBrewNames;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;


public class BrewProvider extends vazkii.botania.data.recipes.BrewProvider {
    public BrewProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany Brew recipes";
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(idFor(LibBrewNames.ALL_MIGHTY), ExtraBotanyBrews.all_mighty, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GOLDEN_CARROT), Ingredient.of(Items.GHAST_TEAR), Ingredient.of(Items.GLOWSTONE_DUST)));
        consumer.accept(new FinishedRecipe(idFor(LibBrewNames.SHELL), ExtraBotanyBrews.shell, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.GOLDEN_APPLE), Ingredient.of(Items.SCUTE), Ingredient.of(Items.OBSIDIAN)));
        consumer.accept(new FinishedRecipe(idFor(LibBrewNames.REVOLUTION), ExtraBotanyBrews.revolution, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.SUGAR), Ingredient.of(Items.IRON_PICKAXE)));
        consumer.accept(new FinishedRecipe(idFor(LibBrewNames.DEADPOOL), ExtraBotanyBrews.deadpool, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(Items.BONE), Ingredient.of(Items.BLAZE_POWDER)));
        consumer.accept(new FinishedRecipe(idFor(LibBrewNames.FLOATING), ExtraBotanyBrews.floating, Ingredient.of(Items.NETHER_WART), Ingredient.of(Items.SUGAR), Ingredient.of(Items.CHORUS_FRUIT)));
    }

    private static ResourceLocation idFor(String s) {
        return resId("brew/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final Brew brew;
        private final Ingredient[] inputs;

        private FinishedRecipe(ResourceLocation id, Brew brew, Ingredient... inputs) {
            this.id = id;
            this.brew = brew;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("brew", BotaniaAPI.instance().getBrewRegistry().getKey(brew).toString());
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
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
            return BotaniaRecipeTypes.BREW_SERIALIZER;
        }

        @org.jetbrains.annotations.Nullable
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
