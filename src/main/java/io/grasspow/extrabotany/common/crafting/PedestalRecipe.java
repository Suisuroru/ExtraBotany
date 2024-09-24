package io.grasspow.extrabotany.common.crafting;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.common.crafting.recipe.RecipeUtils;

import java.util.ArrayList;
import java.util.List;

public class PedestalRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;

    public PedestalRecipe(ResourceLocation id, ItemStack output, Ingredient... inputs) {
        Preconditions.checkArgument(inputs.length <= 2, "Cannot have more than 2 ingredients");
        this.id = id;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        this.output = output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(Container input, Level level) {
        return RecipeUtils.matches(inputs, input, null);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputs;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.PEDESTAL_CLICK_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.PEDESTAL_CLICK.get();
    }

    public ItemStack getClickTool() {
        return inputs.get(1).getItems()[0];
    }

    public static class Serializer implements RecipeSerializer<PedestalRecipe> {
        @NotNull
        @Override
        public PedestalRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            JsonArray ingrs = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputItems = new ArrayList<>();
            for (JsonElement e : ingrs) {
                inputItems.add(Ingredient.fromJson(e));
            }
            return new PedestalRecipe(id, output, inputItems.toArray(new Ingredient[0]));
        }

        @Override
        public PedestalRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient[] inputItems = new Ingredient[buf.readVarInt()];
            for (int i = 0; i < inputItems.length; i++) {
                inputItems[i] = Ingredient.fromNetwork(buf);
            }
            ItemStack output = buf.readItem();
            return new PedestalRecipe(id, output, inputItems);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull PedestalRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(buf);
            }
            buf.writeItem(recipe.output);
        }
    }
}
