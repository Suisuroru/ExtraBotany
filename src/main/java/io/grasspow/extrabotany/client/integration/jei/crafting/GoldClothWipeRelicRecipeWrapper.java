package io.grasspow.extrabotany.client.integration.jei.crafting;

import io.grasspow.extrabotany.common.crafting.GoldClothWipeRelicRecipe;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoldClothWipeRelicRecipeWrapper implements ICraftingCategoryExtension {
    private final ResourceLocation name;

    public GoldClothWipeRelicRecipeWrapper(GoldClothWipeRelicRecipe recipe) {
        this.name = recipe.getId();
    }

    public ResourceLocation getName() {
        return name;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper helper, IFocusGroup focusGroup) {
        var foci = focusGroup.getFocuses(VanillaTypes.ITEM_STACK, RecipeIngredientRole.INPUT)
                .filter(f -> XplatAbstractions.INSTANCE.findRelic(f.getTypedValue().getIngredient()) != null)
                .map(f -> f.getTypedValue().getIngredient())
                .toList();

        var willStacks = !foci.isEmpty() ? foci : List.of(
                new ItemStack(BotaniaItems.dice),
                new ItemStack(BotaniaItems.flugelEye),
                new ItemStack(BotaniaItems.infiniteFruit),
                new ItemStack(BotaniaItems.kingKey),
                new ItemStack(BotaniaItems.lokiRing),
                new ItemStack(BotaniaItems.odinRing),
                new ItemStack(BotaniaItems.thorRing),
                new ItemStack(ExtraBotanyItems.INFINITE_WINE.get())
        );

        var outputStacks = new ArrayList<ItemStack>();
        for (var will : !foci.isEmpty() ? foci : willStacks) {
            ItemStack stack = will.copy();
            String uuid = ItemNBTHelper.getString(will, "soulbindUUID", "soulbindUUID");
            if (!uuid.equals("soulbindUUID")) {
                ItemNBTHelper.setString(stack, "soulbindUUID", uuid);
            } else {
                ItemNBTHelper.removeEntry(stack, "soulbindUUID");
            }
            outputStacks.add(stack);
        }

        helper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK,
                List.of(Collections.singletonList(new ItemStack(ExtraBotanyItems.GOLD_CLOTH.get())), willStacks), 0, 0);
        helper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, outputStacks);
    }
}
