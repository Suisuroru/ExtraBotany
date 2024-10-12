package io.grasspow.extrabotany.common.crafting;


import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.brew.BrewItem;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.brew.BaseBrewItem;

public class CocktailUpgradeRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<CocktailUpgradeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(CocktailUpgradeRecipe::new);

    public CocktailUpgradeRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        boolean foundBrew = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == BotaniaItems.brewFlask && !foundBrew) {
                    foundBrew = true;
                } else if (stack.getItem() == ExtraBotanyItems.MANA_DRINK.get() && !foundItem) {
                    foundItem = true;
                } else {
                    return false;
                }
            }
        }
        return foundBrew && foundItem;
    }

    @NotNull
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == BotaniaItems.brewFlask) {
                nonnulllist.set(i, new ItemStack(BotaniaItems.flask));
                break;
            }
        }
        return nonnulllist;
    }

    @NotNull
    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess pRegistryAccess) {
        ItemStack brewstack = ItemStack.EMPTY;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == BotaniaItems.brewFlask) {
                brewstack = stack;
                break;
            }
        }

        BrewItem brew = (BrewItem) brewstack.getItem();
        ItemStack cocktail = new ItemStack(ExtraBotanyItems.COCKTAIL.get());
        BaseBrewItem.setBrew(cocktail, brew.getBrew(brewstack));
        int left = ItemNBTHelper.getInt(brewstack, "swigsLeft", 6);
        ItemNBTHelper.setInt(cocktail, "swigsLeft", left == 6 ? 8 : ((int) (((float) left / (float) 6)) * 8));
        return cocktail;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > 1 || height > 1;
    }

    @NotNull
    @Override
    public RecipeSerializer<CocktailUpgradeRecipe> getSerializer() {
        return SERIALIZER;
    }
}
