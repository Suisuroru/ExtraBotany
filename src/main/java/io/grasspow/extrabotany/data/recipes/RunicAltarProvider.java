package io.grasspow.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.libs.ExtraBotanyTags;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;


public class RunicAltarProvider extends vazkii.botania.data.recipes.RunicAltarProvider {
    public RunicAltarProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany rune altar recipes";
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        Ingredient spirit = ingr(ExtraBotanyItems.SPIRIT.get());
        Ingredient nightmareFuel = ingr(ExtraBotanyItems.NIGHTMARE_FUEL.get());
        Ingredient heroMedal = ingr(ExtraBotanyItems.HERO_MEDAL.get());
        Ingredient gaiaIngot = ingr(BotaniaItems.gaiaIngot);
        Ingredient lifeEssence = ingr(BotaniaItems.lifeEssence);
        Ingredient gilded_mashed_potato = ingr(ExtraBotanyItems.GILDED_MASHED_POTATO.get());
        Ingredient quartz = ingr(Items.QUARTZ);
        Ingredient lapis = ingr(Items.LAPIS_LAZULI);
        Ingredient manaDiamond = ingr(BotaniaItems.manaDiamond);
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.ULTIMATE_HAMMER), new ItemStack(ExtraBotanyItems.ULTIMATE_HAMMER.get()), 100000, gilded_mashed_potato, gilded_mashed_potato, gilded_mashed_potato, ingr(Items.GOLD_BLOCK), ingr(ExtraBotanyItems.TERRASTEEL_HAMMER.get())));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.PHOTONIUM), new ItemStack(ExtraBotanyItems.PHOTONIUM.get()), 4200, ingr(BotaniaItems.elementium), gilded_mashed_potato, spirit, spirit, spirit));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.SHADOWIUM), new ItemStack(ExtraBotanyItems.SHADOWIUM.get()), 4200, ingr(BotaniaItems.elementium), gilded_mashed_potato, nightmareFuel, nightmareFuel, nightmareFuel));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.GILDED_POTATO), new ItemStack(ExtraBotanyItems.GILDED_POTATO.get()), 800, ingr(Items.POTATO), ingr(Items.GOLD_NUGGET)));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.ORICHALCOS), new ItemStack(ExtraBotanyItems.ORICHALCOS.get()), 1000000, gaiaIngot, gaiaIngot, gaiaIngot, gaiaIngot, lifeEssence, lifeEssence, gilded_mashed_potato, heroMedal));

        consumer.accept(new FinishedRecipe(idFor(LibItemNames.AERO_STONE), new ItemStack(ExtraBotanyItems.AERO_STONE.get()), 2000, ingr(ExtraBotanyTags.Items.RUNES_AIR), ingr(ExtraBotanyTags.Items.RUNES_AIR), gaiaIngot, quartz, lapis, manaDiamond));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.AQUA_STONE), new ItemStack(ExtraBotanyItems.AQUA_STONE.get()), 2000, ingr(ExtraBotanyTags.Items.RUNES_WATER), ingr(ExtraBotanyTags.Items.RUNES_WATER), gaiaIngot, quartz, lapis, manaDiamond));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.EARTH_STONE), new ItemStack(ExtraBotanyItems.EARTH_STONE.get()), 2000, ingr(ExtraBotanyTags.Items.RUNES_EARTH), ingr(ExtraBotanyTags.Items.RUNES_EARTH), gaiaIngot, quartz, lapis, manaDiamond));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.IGNIS_STONE), new ItemStack(ExtraBotanyItems.IGNIS_STONE.get()), 2000, ingr(ExtraBotanyTags.Items.RUNES_FIRE), ingr(ExtraBotanyTags.Items.RUNES_FIRE), gaiaIngot, quartz, lapis, manaDiamond));
        consumer.accept(new FinishedRecipe(idFor(LibItemNames.THE_COMMUNITY), new ItemStack(ExtraBotanyItems.THE_COMMUNITY.get()), 10000, ingr(ExtraBotanyItems.AERO_STONE.get()), ingr(ExtraBotanyItems.AQUA_STONE.get()), gaiaIngot, ingr(ExtraBotanyItems.EARTH_STONE.get()), ingr(ExtraBotanyItems.IGNIS_STONE.get()), ingr(ExtraBotanyItems.THE_CHAOS.get())));
    }

    private Ingredient ingr(TagKey<Item> i) {
        return Ingredient.of(i);
    }

    protected static Ingredient ingr(ItemLike i) {
        return Ingredient.of(i);
    }

    private static ResourceLocation idFor(String s) {
        return resId("runic_altar/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack output;
        private final int mana;
        private final Ingredient[] inputs;

        protected FinishedRecipe(ResourceLocation id, ItemStack output, int mana, Ingredient... inputs) {
            this.id = id;
            this.output = output;
            this.mana = mana;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("output", ItemNBTHelper.serializeStack(output));
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
                ingredients.add(ingr.toJson());
            }
            json.addProperty("mana", mana);
            json.add("ingredients", ingredients);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BotaniaRecipeTypes.RUNE_SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
