package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.data.lang.EnUsProvider;
import io.grasspow.extrabotany.data.lang.LanguageHelper;
import io.grasspow.extrabotany.data.lang.ZhCnProvider;
import io.grasspow.extrabotany.data.loot.LootTableProvider;
import io.grasspow.extrabotany.data.model.BlockModelProvider;
import io.grasspow.extrabotany.data.model.BlockstateProvider;
import io.grasspow.extrabotany.data.model.FloatingFlowerModelProvider;
import io.grasspow.extrabotany.data.model.ItemModelProvider;
import io.grasspow.extrabotany.data.recipes.*;
import io.grasspow.extrabotany.data.tag.BlockTagProvider;
import io.grasspow.extrabotany.data.tag.EntityTagProvider;
import io.grasspow.extrabotany.data.tag.ItemTagProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExtraBotanyAPI.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();

        var output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BlockstateProvider(output));
        generator.addProvider(event.includeClient(), new BlockModelProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new ItemModelProvider(output));
        generator.addProvider(event.includeClient(), new FloatingFlowerModelProvider(output));

        LanguageHelper.init();
        generator.addProvider(event.includeClient(), new EnUsProvider(output));
        generator.addProvider(event.includeClient(), new ZhCnProvider(output));

        generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(output, fileHelper));

        generator.addProvider(event.includeServer(), new AdvancementProvider(output, lookupProvider, fileHelper));

        generator.addProvider(event.includeServer(), new EntityTagProvider(output, lookupProvider));
        var blockTagProvider = new BlockTagProvider(output, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(),
                new ItemTagProvider(output, lookupProvider, blockTagProvider.contentsGetter(), fileHelper));

        generator.addProvider(event.includeServer(), new LootTableProvider(output));

        generator.addProvider(event.includeServer(), new CraftingRecipeProvider(output));
        generator.addProvider(event.includeServer(), new PetalApothecaryProvider(output));
        generator.addProvider(event.includeServer(), new ManaInfusionProvider(output));
        generator.addProvider(event.includeServer(), new RunicAltarProvider(output));
        generator.addProvider(event.includeServer(), new PedestalClickProvider(output));
        generator.addProvider(event.includeServer(), new BrewProvider(output));
        generator.addProvider(event.includeServer(), new TerrestrialAgglomerationProvider(output));

    }
}
