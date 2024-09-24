package io.grasspow.extrabotany.common.crafting;

import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.registry.ModRecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PedestalRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final Ingredient inputItem;
    private final ItemStack clickTool;
    private final ItemStack output;

    public PedestalRecipe(ResourceLocation id, Ingredient inputItem, ItemStack clickTool, ItemStack output) {
        this.id = id;
        this.inputItem = inputItem;
        this.clickTool = clickTool;
        this.output = output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(Container input, Level level) {
        return this.inputItem.test(input.getItem(0));
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
        return BuiltInRegistries.RECIPE_TYPE.get(getId());
    }

    public ItemStack getClickTool() {
        return clickTool;
    }

    public static class Serializer implements RecipeSerializer<PedestalRecipe> {
        @NotNull
        @Override
        public PedestalRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            Ingredient inputItem = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "inputItem"));
            ItemStack clickTool = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "clickTool"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new PedestalRecipe(id, inputItem, clickTool, output);
        }

        @Override
        public PedestalRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient inputItem = Ingredient.fromNetwork(buf);
            ItemStack clickTool = buf.readItem();
            ItemStack output = buf.readItem();
            return new PedestalRecipe(id, inputItem, clickTool, output);
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
