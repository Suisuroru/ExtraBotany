package io.grasspow.extrabotany.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public interface ExtraBotanyDamageTypes {
    ResourceKey<DamageType> GENERAL_ARMOR_PIERCING = ResourceKey.create(Registries.DAMAGE_TYPE, resId("general_armor_piercing"));
    ResourceKey<DamageType> MAGIC_ARMOR_PIERCING = ResourceKey.create(Registries.DAMAGE_TYPE, resId("magic_armor_piercing"));
    ResourceKey<DamageType> CRITICAL = ResourceKey.create(Registries.DAMAGE_TYPE, resId("critical"));

    static void bootstrap(BootstapContext<DamageType> pContext) {
        pContext.register(GENERAL_ARMOR_PIERCING, new DamageType("general_armor_piercing", DamageScaling.ALWAYS, 0F, DamageEffects.HURT, DeathMessageType.DEFAULT));
        pContext.register(MAGIC_ARMOR_PIERCING, new DamageType("magic_armor_piercing", DamageScaling.ALWAYS, 0F, DamageEffects.HURT, DeathMessageType.DEFAULT));
        pContext.register(CRITICAL, new DamageType("critical", DamageScaling.ALWAYS, 0F, DamageEffects.HURT, DeathMessageType.DEFAULT));
    }

    class Sources {
        private static Holder.Reference<DamageType> getHolder(RegistryAccess ra, ResourceKey<DamageType> key) {
            return ra.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
        }

        private static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey) {
            return new DamageSource(getHolder(ra, resourceKey));
        }

        private static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey, @Nullable Entity entity) {
            return new DamageSource(getHolder(ra, resourceKey), entity);
        }

        private static DamageSource source(RegistryAccess ra, ResourceKey<DamageType> resourceKey, @Nullable Entity entity, @Nullable Entity entity2) {
            return new DamageSource(getHolder(ra, resourceKey), entity, entity2);
        }

        public static DamageSource generalArmorPiercing(RegistryAccess ra, Entity entity) {
            return source(ra, GENERAL_ARMOR_PIERCING, entity);
        }

        public static DamageSource magicArmorPiercing(RegistryAccess ra, Entity entity) {
            return source(ra, MAGIC_ARMOR_PIERCING, entity);
        }

        public static DamageSource critical(RegistryAccess ra, Entity entity) {
            return source(ra, CRITICAL, entity);
        }
    }
}
