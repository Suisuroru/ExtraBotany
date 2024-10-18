package io.grasspow.extrabotany.forge.data;


import io.grasspow.extrabotany.common.libs.LibMisc;
import io.grasspow.extrabotany.common.registry.ExtraBotanyDamageTypes;
import io.grasspow.extrabotany.data.AdvancementProvider;
import io.grasspow.extrabotany.data.SoundDefinitionsProvider;
import io.grasspow.extrabotany.data.lang.EnUsProvider;
import io.grasspow.extrabotany.data.lang.LanguageHelper;
import io.grasspow.extrabotany.data.lang.ZhCnProvider;
import io.grasspow.extrabotany.data.loot.LootTableProvider;
import io.grasspow.extrabotany.data.model.BlockstateForgeProvider;
import io.grasspow.extrabotany.data.model.BlockstateProvider;
import io.grasspow.extrabotany.data.model.FloatingFlowerModelProvider;
import io.grasspow.extrabotany.data.model.ItemModelProvider;
import io.grasspow.extrabotany.data.recipes.*;
import io.grasspow.extrabotany.data.tag.BlockTagProvider;
import io.grasspow.extrabotany.data.tag.EntityTagProvider;
import io.grasspow.extrabotany.data.tag.ItemTagProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

import static io.grasspow.extrabotany.api.ExtraBotanyAPI.MOD_ID;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();

        var output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BlockstateProvider(output));
        generator.addProvider(event.includeClient(), new BlockstateForgeProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new ItemModelProvider(output));
        generator.addProvider(event.includeClient(), new FloatingFlowerModelProvider(output));

        LanguageHelper.init();
        generator.addProvider(event.includeClient(), new EnUsProvider(output));
        generator.addProvider(event.includeClient(), new ZhCnProvider(output));

        generator.addProvider(event.includeClient(), new SoundDefinitionsProvider(output, fileHelper));

        generator.addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(output, lookupProvider, new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, ExtraBotanyDamageTypes::bootstrap), Set.of(MOD_ID))
        );
//        generator.addProvider(event.includeServer(), new DamageTypeTagProvider(output, lookupProvider));

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
