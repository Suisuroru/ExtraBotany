package io.grasspow.extrabotany.data.recipes;

import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import io.grasspow.extrabotany.common.registry.ModRecipeTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
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
    public String getName() {
        return "ExtraBotany pedestal recipes";
    }

    @Override
    protected void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
//        consumer.accept(new FinishedRecipe(idFor(LibItemNames.SPIRIT),Ingredient.of(ExtraBotanyItems.SPIRIT_FUEL.get()),Ingredient.of(ExtraBotanyItems.SPIRIT_FUEL.get()), new ItemStack(ExtraBotanyItems.SPIRIT.get())));
    }

    private static ResourceLocation idFor(String s) {
        return resId(LibRecipeNames.PEDESTAL_CLICK + "/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient inputItem;
        private final Ingredient clickTool;
        private final ItemStack output;

        public FinishedRecipe(ResourceLocation id, Ingredient inputItem, Ingredient clickTool, ItemStack output) {
            this.id = id;
            this.inputItem = inputItem;
            this.clickTool = clickTool;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("inputItem", inputItem.toJson());
            json.add("clickTool", clickTool.toJson());
            json.add("output", ItemNBTHelper.serializeStack(output));
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeTypes.PEDESTAL_CLICK_SERIALIZER.get();
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
