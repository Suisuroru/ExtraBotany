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
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.xplat.XplatAbstractions;

public class GoldClothWipeRelicRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<GoldClothWipeRelicRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(GoldClothWipeRelicRecipe::new);

    public GoldClothWipeRelicRecipe(ResourceLocation pId, CraftingBookCategory pCategory) {
        super(pId, pCategory);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        boolean foundCloth = false;
        boolean foundRelic = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ExtraBotanyItems.GOLD_CLOTH.get() && !foundCloth) {
                    foundCloth = true;
                } else if (XplatAbstractions.INSTANCE.findRelic(stack) != null && ItemNBTHelper.verifyExistance(stack, "soulbindUUID") && !foundRelic) {
                    foundRelic = true;
                } else {
                    return false;
                }
            }
        }
        return foundCloth && foundRelic;
    }


    @NotNull
    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess pRegistryAccess) {
        ItemStack relicStack = ItemStack.EMPTY;
        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && XplatAbstractions.INSTANCE.findRelic(stack) != null) {
                relicStack = stack;
                break;
            }
        }
        ItemStack stack = relicStack.copy();
        boolean soulbindUUID = ItemNBTHelper.verifyExistance(relicStack, "soulbindUUID");
        if (soulbindUUID) {
            ItemNBTHelper.removeEntry(stack, "soulbindUUID");
        }
        return stack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > 1 || height > 1;
    }

    @NotNull
    @Override
    public RecipeSerializer<GoldClothWipeRelicRecipe> getSerializer() {
        return SERIALIZER;
    }
}
