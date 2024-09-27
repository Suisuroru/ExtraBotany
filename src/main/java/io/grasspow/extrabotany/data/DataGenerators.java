package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import io.grasspow.extrabotany.data.lang.EnUsProvider;
import io.grasspow.extrabotany.data.lang.ZhCnProvider;
import io.grasspow.extrabotany.data.recipes.CraftingRecipeProvider;
import io.grasspow.extrabotany.data.recipes.ManaInfusionProvider;
import io.grasspow.extrabotany.data.recipes.PedestalClickProvider;
import io.grasspow.extrabotany.data.recipes.RunicAltarProvider;
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

        generator.addProvider(event.includeClient(), new BlockStateProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new ItemModelProvider(output, fileHelper));

        LanguageHelper.init();
        generator.addProvider(event.includeClient(), new EnUsProvider(output));
        generator.addProvider(event.includeClient(), new ZhCnProvider(output));

        generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(output, fileHelper));

        generator.addProvider(event.includeServer(), new AdvancementProvider(output, lookupProvider, fileHelper));

        var blockTagProvider = new BlockTagProvider(output, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(event.includeServer(),
                new ItemTagProvider(output, lookupProvider, blockTagProvider.contentsGetter(), fileHelper));

        generator.addProvider(event.includeServer(), new LootTableProvider(output));

        generator.addProvider(event.includeServer(), new CraftingRecipeProvider(output));
        generator.addProvider(event.includeServer(), new ManaInfusionProvider(output));
        generator.addProvider(event.includeServer(), new RunicAltarProvider(output));
        generator.addProvider(event.includeServer(), new PedestalClickProvider(output));

    }
}
