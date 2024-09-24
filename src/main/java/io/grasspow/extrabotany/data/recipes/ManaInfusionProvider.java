package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

public class ManaInfusionProvider extends vazkii.botania.data.recipes.ManaInfusionProvider {
    public ManaInfusionProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(id("nightmare_fuel"), new ItemStack(ModItems.NIGHTMARE_FUEL.get()), Ingredient.of(Items.COAL), 2000));
    }

    @Override
    public String getName() {
        return "ExtraBotany mana pool recipes";
    }

    @Override
    protected ResourceLocation id(String s) {
        return resId("mana_infusion/" + s);
    }
}
