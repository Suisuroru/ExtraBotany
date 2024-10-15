package io.grasspow.extrabotany.data;

import io.grasspow.extrabotany.common.advancements.TinkleUseTrigger;
import io.grasspow.extrabotany.common.item.ExtraBotanyItems;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import io.grasspow.extrabotany.common.registry.ExtraBotanyBlocks;
import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
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
            Advancement ROOT = Advancement.Builder.advancement()
                    .display(rootDisplay(ExtraBotanyItems.PYLON.get(), "advancement.extrabotany.root.title",
                            "advancement.extrabotany.root.desc", new ResourceLocation("botania:textures/block/livingwood_log.png")))
                    .addCriterion("lexicon", InventoryChangeTrigger.TriggerInstance.hasItems(BotaniaItems.lexicon))
                    .save(consumer, mainId(LibAdvancementNames.ROOT));

            Advancement nightmare_fuel_eat = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.NIGHTMARE_FUEL.get(), LibAdvancementNames.NIGHTMARE_FUEL_EAT, FrameType.TASK))
                    .parent(ROOT)
                    .addCriterion(LibAdvancementNames.NIGHTMARE_FUEL_EAT, eatItem(ExtraBotanyItems.NIGHTMARE_FUEL.get()))
                    .save(consumer, mainId(LibAdvancementNames.NIGHTMARE_FUEL_EAT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyBlocks.POWER_FRAME.get(), LibAdvancementNames.POWER_FRAME_CRAFT, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.POWER_FRAME_CRAFT, onPickup(ExtraBotanyBlocks.POWER_FRAME.get()))
                    .save(consumer, mainId(LibAdvancementNames.POWER_FRAME_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.MIKU_HELM.get(), LibAdvancementNames.ARMORSET_MIKU, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.ARMORSET_MIKU, hasItems(ExtraBotanyItems.MIKU_HELM.get(), ExtraBotanyItems.MIKU_CHEST.get(), ExtraBotanyItems.MIKU_LEGS.get(), ExtraBotanyItems.MIKU_BOOTS.get()))
                    .save(consumer, mainId(LibAdvancementNames.ARMORSET_MIKU));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyBlocks.TINKLE_FLOWER.get().asItem(), LibAdvancementNames.TINKLE_USE, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.TINKLE_USE, new TinkleUseTrigger.Instance(ContextAwarePredicate.ANY, LocationPredicate.ANY))
                    .save(consumer, mainId(LibAdvancementNames.TINKLE_USE));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyBlocks.MANA_BUFFER.get().asItem(), LibAdvancementNames.MANA_BUFFER_CRAFT, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.MANA_BUFFER_CRAFT, hasItems(ExtraBotanyBlocks.MANA_BUFFER.get()))
                    .save(consumer, mainId(LibAdvancementNames.MANA_BUFFER_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.MANA_READER.get(), LibAdvancementNames.MANA_READER_CRAFT, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.MANA_READER_CRAFT, hasItems(ExtraBotanyItems.MANA_READER.get()))
                    .save(consumer, mainId(LibAdvancementNames.MANA_READER_CRAFT));

            Advancement SPIRIT_CRAFT = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.SPIRIT.get(), LibAdvancementNames.SPIRIT_CRAFT, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.SPIRIT_CRAFT, onPickup(ExtraBotanyItems.SPIRIT.get()))
                    .save(consumer, mainId(LibAdvancementNames.SPIRIT_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.GOBLINS_LAYER_HELM.get(), LibAdvancementNames.ARMORSET_GOBLINS_LAYER, FrameType.TASK))
                    .parent(SPIRIT_CRAFT)
                    .addCriterion(LibAdvancementNames.ARMORSET_GOBLINS_LAYER, hasItems(ExtraBotanyItems.GOBLINS_LAYER_HELM.get(), ExtraBotanyItems.GOBLINS_LAYER_CHEST.get(), ExtraBotanyItems.GOBLINS_LAYER_LEGS.get(), ExtraBotanyItems.GOBLINS_LAYER_BOOTS.get()))
                    .save(consumer, mainId(LibAdvancementNames.ARMORSET_GOBLINS_LAYER));
            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.SHADOW_WARRIOR_HELM.get(), LibAdvancementNames.ARMORSET_SHADOW_WARRIOR, FrameType.TASK))
                    .parent(SPIRIT_CRAFT)
                    .addCriterion(LibAdvancementNames.ARMORSET_SHADOW_WARRIOR, hasItems(ExtraBotanyItems.SHADOW_WARRIOR_HELM.get(), ExtraBotanyItems.SHADOW_WARRIOR_CHEST.get(), ExtraBotanyItems.SHADOW_WARRIOR_LEGS.get(), ExtraBotanyItems.SHADOW_WARRIOR_BOOTS.get()))
                    .save(consumer, mainId(LibAdvancementNames.ARMORSET_SHADOW_WARRIOR));
            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.SHOOTING_GUARDIAN_HELM.get(), LibAdvancementNames.ARMORSET_SHOOTING_GUARDIAN, FrameType.TASK))
                    .parent(SPIRIT_CRAFT)
                    .addCriterion(LibAdvancementNames.ARMORSET_SHOOTING_GUARDIAN, hasItems(ExtraBotanyItems.SHOOTING_GUARDIAN_HELM.get(), ExtraBotanyItems.SHOOTING_GUARDIAN_CHEST.get(), ExtraBotanyItems.SHOOTING_GUARDIAN_LEGS.get(), ExtraBotanyItems.SHOOTING_GUARDIAN_BOOTS.get()))
                    .save(consumer, mainId(LibAdvancementNames.ARMORSET_SHOOTING_GUARDIAN));
            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.MAID_HELM.get(), LibAdvancementNames.ARMORSET_MAID, FrameType.TASK))
                    .parent(SPIRIT_CRAFT)
                    .addCriterion(LibAdvancementNames.ARMORSET_MAID, hasItems(ExtraBotanyItems.MAID_HELM.get(), ExtraBotanyItems.MAID_CHEST.get(), ExtraBotanyItems.MAID_LEGS.get(), ExtraBotanyItems.MAID_BOOTS.get()))
                    .save(consumer, mainId(LibAdvancementNames.ARMORSET_MAID));

            Advancement NATURE_ORB_CRAFT = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.NATURE_ORB.get(), LibAdvancementNames.NATURE_ORB_CRAFT, FrameType.TASK))
                    .parent(nightmare_fuel_eat)
                    .addCriterion(LibAdvancementNames.NATURE_ORB_CRAFT, onPickup(ExtraBotanyItems.NATURE_ORB.get()))
                    .save(consumer, mainId(LibAdvancementNames.NATURE_ORB_CRAFT));

            Advancement EGO_DEFEAT = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.HERO_MEDAL.get(), LibAdvancementNames.EGO_DEFEAT, FrameType.TASK))
                    .parent(NATURE_ORB_CRAFT)
                    .addCriterion(LibAdvancementNames.EGO_DEFEAT, KilledTrigger.TriggerInstance
                            .playerKilledEntity(EntityPredicate.Builder.entity().of(ExtraBotanyEntities.EGO.get())))
                    .save(consumer, mainId(LibAdvancementNames.EGO_DEFEAT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.SUN_RING.get(), LibAdvancementNames.SUN_RING_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.SUN_RING_CRAFT, hasItems(ExtraBotanyItems.SUN_RING.get()))
                    .save(consumer, mainId(LibAdvancementNames.SUN_RING_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.MOON_PENDANT.get(), LibAdvancementNames.MOON_PENDANT_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.MOON_PENDANT_CRAFT, hasItems(ExtraBotanyItems.MOON_PENDANT.get()))
                    .save(consumer, mainId(LibAdvancementNames.MOON_PENDANT_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.FAILNAUGHT.get(), LibAdvancementNames.FAILNAUGHT_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.FAILNAUGHT_CRAFT, hasItems(ExtraBotanyItems.FAILNAUGHT.get()))
                    .save(consumer, mainId(LibAdvancementNames.FAILNAUGHT_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.CAMERA.get(), LibAdvancementNames.CAMERA_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.CAMERA_CRAFT, hasItems(ExtraBotanyItems.CAMERA.get()))
                    .save(consumer, mainId(LibAdvancementNames.CAMERA_CRAFT));

            Advancement THE_UNIVERSE_CRAFT = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.THE_UNIVERSE.get(), LibAdvancementNames.THE_UNIVERSE_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.THE_UNIVERSE_CRAFT, hasItems(ExtraBotanyItems.THE_UNIVERSE.get()))
                    .save(consumer, mainId(LibAdvancementNames.THE_UNIVERSE_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.FIRST_FRACTAL.get(), LibAdvancementNames.FIRST_FRACTAL_CRAFT, FrameType.CHALLENGE))
                    .parent(THE_UNIVERSE_CRAFT)
                    .addCriterion(LibAdvancementNames.FIRST_FRACTAL_CRAFT, hasItems(ExtraBotanyItems.FIRST_FRACTAL.get()))
                    .save(consumer, mainId(LibAdvancementNames.FIRST_FRACTAL_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.CORE_GOD.get(), LibAdvancementNames.CORE_GOD_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.CORE_GOD_CRAFT, hasItems(ExtraBotanyItems.CORE_GOD.get()))
                    .save(consumer, mainId(LibAdvancementNames.CORE_GOD_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.INFINITE_WINE.get(), LibAdvancementNames.INFINITE_WINE_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.INFINITE_WINE_CRAFT, hasItems(ExtraBotanyItems.INFINITE_WINE.get()))
                    .save(consumer, mainId(LibAdvancementNames.INFINITE_WINE_CRAFT));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.BUDDHIST_RELICS.get(), LibAdvancementNames.BUDDHIST_RELICS_OBTAIN, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.BUDDHIST_RELICS_OBTAIN, hasItems(ExtraBotanyItems.BUDDHIST_RELICS.get()))
                    .save(consumer, mainId(LibAdvancementNames.BUDDHIST_RELICS_OBTAIN));

            Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.EXCALIBER.get(), LibAdvancementNames.EXCALIBER_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.EXCALIBER_CRAFT, hasItems(ExtraBotanyItems.EXCALIBER.get()))
                    .save(consumer, mainId(LibAdvancementNames.EXCALIBER_CRAFT));

            Advancement SAGES_MANA_RING_CRAFT = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.SAGES_MANA_RING.get(), LibAdvancementNames.SAGES_MANA_RING_CRAFT, FrameType.CHALLENGE))
                    .parent(EGO_DEFEAT)
                    .addCriterion(LibAdvancementNames.SAGES_MANA_RING_CRAFT, hasItems(ExtraBotanyItems.SAGES_MANA_RING.get()))
                    .save(consumer, mainId(LibAdvancementNames.SAGES_MANA_RING_CRAFT));

            Advancement.Builder.advancement()
                    .display(hidden(ExtraBotanyItems.SAGES_MANA_RING.get(), LibAdvancementNames.SAGES_MANA_RING_FILL, FrameType.CHALLENGE))
                    .parent(SAGES_MANA_RING_CRAFT)
                    .addCriterion(LibAdvancementNames.SAGES_MANA_RING_FILL, hasItems(ExtraBotanyItems.SAGES_MANA_RING.get()))
                    .save(consumer, mainId(LibAdvancementNames.SAGES_MANA_RING_FILL));
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

    protected static InventoryChangeTrigger.TriggerInstance hasItems(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
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
