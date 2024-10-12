package io.grasspow.extrabotany.client.integration.jei.crafting;

import io.grasspow.extrabotany.common.crafting.PotionLensBindBrewRecipe;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.brew.BaseBrewItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PotionLensBindBrewRecipeWrapper implements ICraftingCategoryExtension {
    private final ResourceLocation name;

    public PotionLensBindBrewRecipeWrapper(PotionLensBindBrewRecipe recipe) {
        this.name = recipe.getId();
    }

    public ResourceLocation getName() {
        return name;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper helper, IFocusGroup focusGroup) {
        var foci = focusGroup.getFocuses(VanillaTypes.ITEM_STACK, RecipeIngredientRole.INPUT)
                .filter(f -> f.getTypedValue().getIngredient().getItem() instanceof BaseBrewItem)
                .map(f -> f.getTypedValue().getIngredient())
                .toList();

        var willStacks = !foci.isEmpty() ? foci : List.of(
                new ItemStack(BotaniaItems.brewVial),
                new ItemStack(BotaniaItems.brewFlask),
                new ItemStack(ExtraBotanyItems.COCKTAIL.get()),
                new ItemStack(ExtraBotanyItems.INFINITE_WINE.get())
        );

        var outputStacks = new ArrayList<ItemStack>();
        for (var will : !foci.isEmpty() ? foci : willStacks) {
            var stack = new ItemStack(ExtraBotanyItems.POTION_LENS.get());
            BaseBrewItem.setBrew(stack, ((BaseBrewItem) will.getItem()).getBrew(will));
            outputStacks.add(stack);
        }

        helper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK,
                List.of(Collections.singletonList(new ItemStack(ExtraBotanyItems.POTION_LENS.get())), willStacks), 0, 0);
        helper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, outputStacks);
    }
}
