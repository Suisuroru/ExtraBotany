/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package io.grasspow.extrabotany.client.integration.patchouli;

import com.google.common.collect.ImmutableList;
import io.grasspow.extrabotany.common.crafting.PedestalClickRecipe;
import io.grasspow.extrabotany.common.registry.ExtraBotanyRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import vazkii.botania.client.patchouli.PatchouliUtils;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;
import java.util.stream.Collectors;

public class PedestalClickProcessor implements IComponentProcessor {
    private List<PedestalClickRecipe> recipes;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        ImmutableList.Builder<PedestalClickRecipe> builder = ImmutableList.builder();
        for (IVariable s : variables.get("recipes").asListOrSingleton()) {
            PedestalClickRecipe recipe = PatchouliUtils.getRecipe(level, ExtraBotanyRecipeTypes.PEDESTAL_CLICK.get(), new ResourceLocation(s.asString()));
            if (recipe != null) {
                builder.add(recipe);
            }
        }

        this.recipes = builder.build();
    }

    @Override
    public IVariable process(Level level, String key) {
        if (recipes.isEmpty()) {
            return null;
        }
        return switch (key) {
            case "heading" -> IVariable.from(recipes.get(0).getResultItem(level.registryAccess()).getHoverName());
            case "input" ->
                    PatchouliUtils.interweaveIngredients(recipes.stream().map(r -> r.getIngredients().get(0)).collect(Collectors.toList()));
            case "tool" ->
                    PatchouliUtils.interweaveIngredients(recipes.stream().map(r -> r.getIngredients().get(1)).collect(Collectors.toList()));
            case "output" ->
                    IVariable.wrapList(recipes.stream().map(r -> r.getResultItem(level.registryAccess())).map(IVariable::from).collect(Collectors.toList()));
            default -> null;
        };
    }
}
