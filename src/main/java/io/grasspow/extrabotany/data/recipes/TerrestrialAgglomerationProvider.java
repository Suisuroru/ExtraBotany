package io.grasspow.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class TerrestrialAgglomerationProvider extends vazkii.botania.data.recipes.TerrestrialAgglomerationProvider {
    public TerrestrialAgglomerationProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany Terra Plate recipes";
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.AERIALITE), ManaPoolBlockEntity.MAX_MANA / 2, new ItemStack(ExtraBotanyItems.AERIALITE.get()),
                        Ingredient.of(BotaniaItems.dragonstone),
                        Ingredient.of(BotaniaItems.enderAirBottle),
                        Ingredient.of(Items.PHANTOM_MEMBRANE)
                )
        );
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.THE_UNIVERSE), ManaPoolBlockEntity.MAX_MANA, new ItemStack(ExtraBotanyItems.THE_UNIVERSE.get()),
                        Ingredient.of(ExtraBotanyItems.THE_CHAOS.get()),
                        Ingredient.of(ExtraBotanyItems.THE_END.get()),
                        Ingredient.of(ExtraBotanyItems.THE_ORIGIN.get())
                )
        );
    }

    private static ResourceLocation idFor(String s) {
        return resId("terra_plate/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final int mana;
        private final ItemStack output;
        private final Ingredient[] inputs;

        public FinishedRecipe(ResourceLocation id, int mana, ItemStack output, Ingredient... inputs) {
            this.id = id;
            this.mana = mana;
            this.output = output;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("mana", mana);
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
                ingredients.add(ingr.toJson());
            }
            json.add("ingredients", ingredients);
            json.add("result", ItemNBTHelper.serializeStack(output));
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BotaniaRecipeTypes.TERRA_PLATE_SERIALIZER;
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
