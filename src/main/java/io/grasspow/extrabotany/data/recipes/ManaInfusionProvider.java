package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.StateIngredientHelper;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class ManaInfusionProvider extends vazkii.botania.data.recipes.ManaInfusionProvider {
    public ManaInfusionProvider(PackOutput packOutput) {
        super(packOutput);
    }

    private static final StateIngredient CONJURATION = StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst);
    private static final StateIngredient ALCHEMY = StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst);
    private static final StateIngredient DIMENSION = StateIngredientHelper.of(ExtraBotanyBlocks.DIMENSION_CATALYST.get());
    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(id(LibItemNames.NIGHTMARE_FUEL), new ItemStack(ExtraBotanyItems.NIGHTMARE_FUEL.get()), Ingredient.of(Items.COAL), 2000));
        consumer.accept(new FinishedRecipe(id(LibItemNames.FRIED_CHICKEN), new ItemStack(ExtraBotanyItems.FRIED_CHICKEN.get()), Ingredient.of(Items.COOKED_CHICKEN), 600));
        consumer.accept(dimension(id("enderpearl"), new ItemStack(Items.ENDER_PEARL), ingr(Items.DIAMOND), 20000));
        consumer.accept(dimension(id("shulker_shell"), new ItemStack(Items.SHULKER_SHELL), ingr(Items.DIAMOND_HORSE_ARMOR), 20000));
        consumer.accept(dimension(id("chorus_fruit"), new ItemStack(Items.CHORUS_FRUIT), ingr(Items.APPLE), 500));
        consumer.accept(dimension(id("end_stone"), new ItemStack(Items.END_STONE), ingr(Items.STONE), 500));
        consumer.accept(dimension(id("nether_rack"), new ItemStack(Items.NETHERRACK), ingr(Items.COBBLESTONE), 500));
        consumer.accept(dimension(id("soul_sand"), new ItemStack(Items.SOUL_SAND), ingr(Items.SAND), 500));
        consumer.accept(dimension(id("quartz_ore"), new ItemStack(Items.NETHER_QUARTZ_ORE), ingr(Items.IRON_ORE), 2000));
        consumer.accept(dimension(id("blaze_rod"), new ItemStack(Items.BLAZE_ROD, 2), ingr(Items.BLAZE_ROD), 20000));
        consumer.accept(dimension(id("totem_of_undying"), new ItemStack(Items.TOTEM_OF_UNDYING), ingr(Items.NETHER_STAR), 50000));
//        consumer.accept(dimension(id("elytra"), new ItemStack(Items.ELYTRA), ingr(ModItems.theorigin), 50000));
    }

    private FinishedRecipe dimension(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
        return new FinishedRecipe(id, output, input, mana, "", DIMENSION);
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
