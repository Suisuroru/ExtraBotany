package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import vazkii.botania.common.item.BotaniaItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class AdvancementProvider extends ForgeAdvancementProvider {
    public AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ModAdvancementGenerator()));
    }

    public static class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            Advancement root = Advancement.Builder.advancement()
                    .display(rootDisplay(ExtraBotanyItems.PYLON.get(), "advancement.extrabotany.root.title",
                            "advancement.extrabotany.root.desc", new ResourceLocation("botania:textures/block/livingwood_log.png")))
                    .addCriterion("lexicon", InventoryChangeTrigger.TriggerInstance.hasItems(BotaniaItems.lexicon))
                    .save(consumer, mainId(LibAdvancementNames.ROOT));

            Advancement nightmare_fuel_eat = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.NIGHTMARE_FUEL.get(), LibAdvancementNames.NIGHTMARE_FUEL_EAT, FrameType.TASK))
                    .parent(root)
                    .addCriterion(LibAdvancementNames.NIGHTMARE_FUEL_EAT, eatItem(ExtraBotanyItems.NIGHTMARE_FUEL.get()))
                    .save(consumer, mainId(LibAdvancementNames.NIGHTMARE_FUEL_EAT));
        }
    }

    protected static ConsumeItemTrigger.TriggerInstance eatItem(Item items) {
        return ConsumeItemTrigger.TriggerInstance.usedItem(items);
    }

    protected static InventoryChangeTrigger.TriggerInstance onPickup(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(tag).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance onPickup(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(matchItems(items));
    }

    protected static ItemPredicate matchItems(ItemLike... items) {
        return ItemPredicate.Builder.item().of(items).build();
    }

    protected static DisplayInfo simple(ItemLike icon, String name, FrameType frameType) {
        String expandedName = "advancement.extrabotany." + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
                Component.translatable(expandedName + ".title"),
                Component.translatable(expandedName + ".desc"),
                null, frameType, true, true, false);
    }

    protected static DisplayInfo hidden(ItemLike icon, String name, FrameType frameType) {
        String expandedName = "advancement.extrabotany." + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
                Component.translatable(expandedName + ".title"),
                Component.translatable(expandedName + ".desc"),
                null, frameType, true, true, true);
    }

    protected static DisplayInfo rootDisplay(ItemLike icon, String titleKey, String descKey, ResourceLocation background) {
        return new DisplayInfo(new ItemStack(icon.asItem()),
                Component.translatable(titleKey),
                Component.translatable(descKey),
                background, FrameType.TASK, false, true, false);
    }

    private static String mainId(String name) {
        return resId("main/" + name).toString();
    }
}
