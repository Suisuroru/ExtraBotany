package io.grasspow.extrabotany.common.crafting;


import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
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
import vazkii.botania.common.item.brew.BaseBrewItem;

public class InfiniteWineUpgradeRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<InfiniteWineUpgradeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(InfiniteWineUpgradeRecipe::new);

    public InfiniteWineUpgradeRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        boolean foundBrew = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ExtraBotanyItems.COCKTAIL.get() && !foundBrew) {
                    foundBrew = true;
                } else if (stack.getItem() == ExtraBotanyItems.HERO_MEDAL.get() && !foundItem) {
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
    public ItemStack assemble(CraftingContainer inv, RegistryAccess pRegistryAccess) {
        ItemStack brewstack = ItemStack.EMPTY;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == ExtraBotanyItems.COCKTAIL.get()) {
                brewstack = stack;
                break;
            }
        }

        BrewItem brew = (BrewItem) brewstack.getItem();
        ItemStack infiniteWine = new ItemStack(ExtraBotanyItems.INFINITE_WINE.get());
        BaseBrewItem.setBrew(infiniteWine, brew.getBrew(brewstack));
        int left = ItemNBTHelper.getInt(brewstack, "swigsLeft", 8);
        ItemNBTHelper.setInt(infiniteWine, "swigsLeft", left == 8 ? 12 : ((int) (((float) left / (float) 8) * 12)));
        return infiniteWine;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > 1 || height > 1;
    }

    @NotNull
    @Override
    public RecipeSerializer<InfiniteWineUpgradeRecipe> getSerializer() {
        return SERIALIZER;
    }
}
