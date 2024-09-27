package io.grasspow.extrabotany.common.crafting;


import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.brew.BrewItem;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.brew.BaseBrewItem;

public class SplashGrenadeRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<SplashGrenadeRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(SplashGrenadeRecipe::new);

    public SplashGrenadeRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
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
                } else if (stack.getItem() == Items.POPPED_CHORUS_FRUIT && !foundItem) {
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
            if (!stack.isEmpty() && stack.getItem() == ExtraBotanyItems.COCKTAIL.get()) {
                nonnulllist.set(i, new ItemStack(ExtraBotanyItems.EMPTY_BOTTLE.get()));
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
            if (!stack.isEmpty() && stack.getItem() == ExtraBotanyItems.COCKTAIL.get()) {
                brewstack = stack;
                break;
            }
        }

        BrewItem brew = (BrewItem) brewstack.getItem();
        ItemStack splashGrenade = new ItemStack(ExtraBotanyItems.SPLASH_GRENADE.get());
        BaseBrewItem.setBrew(splashGrenade, brew.getBrew(brewstack));
        splashGrenade.setCount(ItemNBTHelper.getInt(brewstack, "swigsLeft", 8));
        return splashGrenade;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > 1 || height > 1;
    }

    @NotNull
    @Override
    public RecipeSerializer<SplashGrenadeRecipe> getSerializer() {
        return SERIALIZER;
    }
}
