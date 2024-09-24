package io.grasspow.extrabotany.client.integration.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import io.grasspow.extrabotany.common.crafting.PedestalClickRecipe;
import io.grasspow.extrabotany.common.libs.LibRecipeNames;
import io.grasspow.extrabotany.common.registry.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.lib.LibMisc;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

public class PedestalClickRecipeCategory implements IRecipeCategory<PedestalClickRecipe> {

    private final IDrawable background;
    private final Component localizedName;
    private final IDrawable overlay;
    private final IDrawable icon;
    private final ItemStack renderStack = new ItemStack(ModItems.PEDESTAL_ITEM.get());

    public PedestalClickRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 46);
        localizedName = Component.translatable("block.extrabotany.pedestal");
        overlay = guiHelper.createDrawable(prefix("textures/gui/pure_daisy_overlay.png"),
                0, 0, 64, 46);
        ItemNBTHelper.setBoolean(renderStack, "RenderFull", true);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, renderStack.copy());
    }

    public static final RecipeType<PedestalClickRecipe> TYPE =
            RecipeType.create(LibMisc.MOD_ID, LibRecipeNames.PEDESTAL_CLICK, PedestalClickRecipe.class);

    @Override
    public RecipeType<PedestalClickRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(PedestalClickRecipe recipe, @NotNull IRecipeSlotsView slotsView, @NotNull GuiGraphics gui, double mouseX, double mouseY) {
        RenderSystem.enableBlend();
        overlay.draw(gui, 40, 0);
        RenderSystem.disableBlend();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PedestalClickRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 12).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 12).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 12).addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
    }
}
