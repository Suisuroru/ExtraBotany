/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package io.grasspow.extrabotany.data.tag;

import io.grasspow.extrabotany.common.registry.ExtraBotanyEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.Tags;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.concurrent.CompletableFuture;

public class EntityTagProvider extends IntrinsicHolderTagsProvider<EntityType<?>> {
    public EntityTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.ENTITY_TYPE, lookupProvider, (entityType) -> entityType.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BotaniaTags.Entities.SHADED_MESA_BLACKLIST).add(
                ExtraBotanyEntities.EGO.get()
        );
        tag(BotaniaTags.Entities.KEY_IMMUNE).add(EntityType.ITEM, EntityType.ITEM_FRAME, EntityType.PAINTING, EntityType.EXPERIENCE_ORB);
        tag(Tags.EntityTypes.BOSSES).add(ExtraBotanyEntities.EGO.get());
    }
}
