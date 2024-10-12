package io.grasspow.extrabotany.data.recipes;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.ExtraBotanyTags;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Consumer;

public class PetalApothecaryProvider extends vazkii.botania.data.recipes.PetalApothecaryProvider {
    public PetalApothecaryProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        Ingredient white = tagIngr("petals/white");
        Ingredient orange = tagIngr("petals/orange");
        Ingredient magenta = tagIngr("petals/magenta");
        Ingredient lightBlue = tagIngr("petals/light_blue");
        Ingredient yellow = tagIngr("petals/yellow");
        Ingredient lime = tagIngr("petals/lime");
        Ingredient pink = tagIngr("petals/pink");
        Ingredient gray = tagIngr("petals/gray");
        Ingredient lightGray = tagIngr("petals/light_gray");
        Ingredient cyan = tagIngr("petals/cyan");
        Ingredient purple = tagIngr("petals/purple");
        Ingredient blue = tagIngr("petals/blue");
        Ingredient brown = tagIngr("petals/brown");
        Ingredient green = tagIngr("petals/green");
        Ingredient red = tagIngr("petals/red");
        Ingredient black = tagIngr("petals/black");
        Ingredient runeWater = Ingredient.of(ExtraBotanyTags.Items.RUNES_WATER);
        Ingredient runeFire = Ingredient.of(ExtraBotanyTags.Items.RUNES_FIRE);
        Ingredient runeEarth = Ingredient.of(ExtraBotanyTags.Items.RUNES_EARTH);
        Ingredient runeAir = Ingredient.of(ExtraBotanyTags.Items.RUNES_AIR);
        Ingredient runeSpring = Ingredient.of(ExtraBotanyTags.Items.RUNES_SPRING);
        Ingredient runeSummer = Ingredient.of(ExtraBotanyTags.Items.RUNES_SUMMER);
        Ingredient runeAutumn = Ingredient.of(ExtraBotanyTags.Items.RUNES_AUTUMN);
        Ingredient runeWinter = Ingredient.of(ExtraBotanyTags.Items.RUNES_WINTER);
        Ingredient runeMana = Ingredient.of(ExtraBotanyTags.Items.RUNES_MANA);
        Ingredient runeLust = Ingredient.of(ExtraBotanyTags.Items.RUNES_LUST);
        Ingredient runeGluttony = Ingredient.of(ExtraBotanyTags.Items.RUNES_GLUTTONY);
        Ingredient runeGreed = Ingredient.of(ExtraBotanyTags.Items.RUNES_GREED);
        Ingredient runeSloth = Ingredient.of(ExtraBotanyTags.Items.RUNES_SLOTH);
        Ingredient runeWrath = Ingredient.of(ExtraBotanyTags.Items.RUNES_WRATH);
        Ingredient runeEnvy = Ingredient.of(ExtraBotanyTags.Items.RUNES_ENVY);
        Ingredient runePride = Ingredient.of(ExtraBotanyTags.Items.RUNES_PRIDE);

        Ingredient redstoneRoot = Ingredient.of(BotaniaItems.redstoneRoot);
        Ingredient pixieDust = Ingredient.of(BotaniaItems.pixieDust);
        Ingredient gaiaSpirit = Ingredient.of(BotaniaItems.lifeEssence);
        Ingredient spirit = Ingredient.of(ExtraBotanyItems.SPIRIT.get());

        consumer.accept(make(ExtraBotanyBlocks.ANNOYING_FLOWER.get(), green, pink, pink, white, white, runeMana, spirit));
        consumer.accept(make(ExtraBotanyBlocks.SERENITIAN.get(), blue, blue, purple, purple, runeMana, runeSloth, runeGreed, gaiaSpirit, Ingredient.of(Items.WITHER_ROSE)));
        consumer.accept(make(ExtraBotanyBlocks.BELL_FLOWER.get(), lime, lime, yellow, yellow, yellow, yellow));
        consumer.accept(make(ExtraBotanyBlocks.EDELWEISS.get(), white, white, white, blue, blue, runeWinter, runeMana));
        consumer.accept(make(ExtraBotanyBlocks.GEMINI_ORCHID.get(), yellow, yellow, yellow, orange, orange, orange));
        consumer.accept(make(ExtraBotanyBlocks.SUN_BLESS.get(), yellow, yellow, yellow, white));
        consumer.accept(make(ExtraBotanyBlocks.MOON_BLESS.get(), purple, purple, purple, white));
        consumer.accept(make(ExtraBotanyBlocks.OMNI_VIOLET.get(), blue, blue, purple, purple, runeSpring, runeMana, runeLust));
        consumer.accept(make(ExtraBotanyBlocks.REIKAR_LILY.get(), lightBlue, lightBlue, cyan, cyan, blue, runePride, runeSloth, runeEnvy, gaiaSpirit));
        consumer.accept(make(ExtraBotanyBlocks.TINKLE_FLOWER.get(), yellow, red, green, blue, runeEarth, runeWater));
    }
}
