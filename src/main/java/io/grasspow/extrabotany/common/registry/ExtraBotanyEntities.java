package io.grasspow.extrabotany.common.registry;

import com.mojang.datafixers.DSL;
import io.grasspow.extrabotany.common.entity.block.LivingrockBarrelBlockEntity;
import io.grasspow.extrabotany.common.entity.block.ManaBufferBlockEntity;
import io.grasspow.extrabotany.common.entity.block.PedestalBlockEntity;
import io.grasspow.extrabotany.common.entity.block.PowerFrameBlockEntity;
import io.grasspow.extrabotany.common.entity.block.flower.AnnoyingFlowerBlockEntity;
import io.grasspow.extrabotany.common.entity.block.flower.SerenitianBlockEntity;
import io.grasspow.extrabotany.common.entity.block.flower.generating.*;
import io.grasspow.extrabotany.common.entity.ego.EGO;
import io.grasspow.extrabotany.common.entity.ego.EGOLandmine;
import io.grasspow.extrabotany.common.entity.ego.EGOMinion;
import io.grasspow.extrabotany.common.entity.item.brew.SplashGrenadeEntity;
import io.grasspow.extrabotany.common.entity.projectile.*;
import io.grasspow.extrabotany.common.libs.LibBlockNames;
import io.grasspow.extrabotany.common.libs.LibEntityNames;
import io.grasspow.extrabotany.common.libs.LibItemNames;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class ExtraBotanyEntities {
    public static class Blocks {
        private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LibMisc.MOD_ID);

        public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(LibBlockNames.PEDESTAL,
                () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ExtraBotanyBlocks.PEDESTAL.get()).build(DSL.remainderType()));
        public static final RegistryObject<BlockEntityType<ManaBufferBlockEntity>> MANA_BUFFER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(LibBlockNames.MANA_BUFFER,
                () -> BlockEntityType.Builder.of(ManaBufferBlockEntity::new,
                        ExtraBotanyBlocks.MANA_BUFFER.get(),
                        ExtraBotanyBlocks.QUANTUM_MANA_BUFFER.get()
                ).build(DSL.remainderType()));
        public static final RegistryObject<BlockEntityType<LivingrockBarrelBlockEntity>> LIVINGROCK_BARREL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(LibBlockNames.LIVINGROCK_BARREL,
                () -> BlockEntityType.Builder.of(LivingrockBarrelBlockEntity::new, ExtraBotanyBlocks.LIVINGROCK_BARREL.get()).build(DSL.remainderType()));
        public static final RegistryObject<BlockEntityType<PowerFrameBlockEntity>> POWER_FRAME_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(LibBlockNames.POWER_FRAME,
                () -> BlockEntityType.Builder.of(PowerFrameBlockEntity::new, ExtraBotanyBlocks.POWER_FRAME.get()).build(DSL.remainderType()));

        public static DeferredRegister<BlockEntityType<?>> getBlockEntityTypes() {
            return BLOCK_ENTITY_TYPES;
        }

        public static class Flowers {
            public static final RegistryObject<BlockEntityType<AnnoyingFlowerBlockEntity>> ANNOYING_FLOWER = BLOCK_ENTITY_TYPES.register(LibBlockNames.ANNOYING_FLOWER,
                    () -> BlockEntityType.Builder.of(AnnoyingFlowerBlockEntity::new,
                            ExtraBotanyBlocks.ANNOYING_FLOWER.get(),
                            ExtraBotanyBlocks.FLOATING_ANNOYING_FLOWER.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<SerenitianBlockEntity>> SERENITIAN = BLOCK_ENTITY_TYPES.register(LibBlockNames.SERENITIAN,
                    () -> BlockEntityType.Builder.of(SerenitianBlockEntity::new,
                            ExtraBotanyBlocks.SERENITIAN.get(),
                            ExtraBotanyBlocks.FLOATING_SERENITIAN.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<BellFlowerBlockEntity>> BELL_FLOWER = BLOCK_ENTITY_TYPES.register(LibBlockNames.BELL_FLOWER,
                    () -> BlockEntityType.Builder.of(BellFlowerBlockEntity::new,
                            ExtraBotanyBlocks.BELL_FLOWER.get(),
                            ExtraBotanyBlocks.FLOATING_BELL_FLOWER.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<EdelweissBlockEntity>> EDELWEISS = BLOCK_ENTITY_TYPES.register(LibBlockNames.EDELWEISS,
                    () -> BlockEntityType.Builder.of(EdelweissBlockEntity::new,
                            ExtraBotanyBlocks.EDELWEISS.get(),
                            ExtraBotanyBlocks.FLOATING_EDELWEISS.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<GeminiOrchidBlockEntity>> GEMINI_ORCHID = BLOCK_ENTITY_TYPES.register(LibBlockNames.GEMINI_ORCHID,
                    () -> BlockEntityType.Builder.of(GeminiOrchidBlockEntity::new,
                            ExtraBotanyBlocks.GEMINI_ORCHID.get(),
                            ExtraBotanyBlocks.FLOATING_GEMINI_ORCHID.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<SunBlessBlockEntity>> SUN_BLESS = BLOCK_ENTITY_TYPES.register(LibBlockNames.SUN_BLESS,
                    () -> BlockEntityType.Builder.of(SunBlessBlockEntity::new,
                            ExtraBotanyBlocks.SUN_BLESS.get(),
                            ExtraBotanyBlocks.FLOATING_SUN_BLESS.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<MoonBlessBlockEntity>> MOON_BLESS = BLOCK_ENTITY_TYPES.register(LibBlockNames.MOON_BLESS,
                    () -> BlockEntityType.Builder.of(MoonBlessBlockEntity::new,
                            ExtraBotanyBlocks.MOON_BLESS.get(),
                            ExtraBotanyBlocks.FLOATING_MOON_BLESS.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<OmniVioletBlockEntity>> OMNI_VIOLET = BLOCK_ENTITY_TYPES.register(LibBlockNames.OMNI_VIOLET,
                    () -> BlockEntityType.Builder.of(OmniVioletBlockEntity::new,
                            ExtraBotanyBlocks.OMNI_VIOLET.get(),
                            ExtraBotanyBlocks.FLOATING_OMNI_VIOLET.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<ReikarLilyBlockEntity>> REIKAR_LILY = BLOCK_ENTITY_TYPES.register(LibBlockNames.REIKAR_LILY,
                    () -> BlockEntityType.Builder.of(ReikarLilyBlockEntity::new,
                            ExtraBotanyBlocks.REIKAR_LILY.get(),
                            ExtraBotanyBlocks.FLOATING_REIKAR_LILY.get()
                    ).build(DSL.remainderType()));
            public static final RegistryObject<BlockEntityType<TinkleFlowerBlockEntity>> TINKLE_FLOWER = BLOCK_ENTITY_TYPES.register(LibBlockNames.TINKLE_FLOWER,
                    () -> BlockEntityType.Builder.of(TinkleFlowerBlockEntity::new,
                            ExtraBotanyBlocks.TINKLE_FLOWER.get(),
                            ExtraBotanyBlocks.FLOATING_TINKLE_FLOWER.get()
                    ).build(DSL.remainderType()));
        }
    }

    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LibMisc.MOD_ID);

    public static DeferredRegister<EntityType<?>> getEntityTypes() {
        return ENTITY_TYPES;
    }

    public static final RegistryObject<EntityType<SplashGrenadeEntity>> SPLASH_GRENADE = ENTITY_TYPES.register(LibItemNames.SPLASH_GRENADE,
            () -> EntityType.Builder
                    .<SplashGrenadeEntity>of(SplashGrenadeEntity::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.SPLASH_GRENADE).toString()));

    public static final RegistryObject<EntityType<AuraFireProjectile>> AURA_FILE = ENTITY_TYPES.register(LibEntityNames.AURA_FIRE,
            () -> EntityType.Builder
                    .<AuraFireProjectile>of(AuraFireProjectile::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.AURA_FIRE).toString()));

    public static final RegistryObject<EntityType<InfluxWaverProjectile>> INFLUX_WAVER_PROJECTILE = ENTITY_TYPES.register(LibEntityNames.INFLUX_WAVER_PROJECTILE,
            () -> EntityType.Builder
                    .<InfluxWaverProjectile>of(InfluxWaverProjectile::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.INFLUX_WAVER_PROJECTILE).toString()));

    public static final RegistryObject<EntityType<TrueShadowKatanaProjectile>> TRUE_SHADOW_KATANA_PROJECTILE = ENTITY_TYPES.register(LibEntityNames.TRUE_SHADOW_KATANA_PROJECTILE,
            () -> EntityType.Builder
                    .<TrueShadowKatanaProjectile>of(TrueShadowKatanaProjectile::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.TRUE_SHADOW_KATANA_PROJECTILE).toString()));

    public static final RegistryObject<EntityType<TrueTerraBladeProjectile>> TRUE_TERRA_BLADE_PROJECTILE = ENTITY_TYPES.register(LibEntityNames.TRUE_TERRA_BLADE_PROJECTILE,
            () -> EntityType.Builder
                    .<TrueTerraBladeProjectile>of(TrueTerraBladeProjectile::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.TRUE_TERRA_BLADE_PROJECTILE).toString()));

    public static final RegistryObject<EntityType<PhantomSwordProjectile>> PHANTOM_SWORD_PROJECTILE = ENTITY_TYPES.register(LibEntityNames.PHANTOM_SWORD_PROJECTILE,
            () -> EntityType.Builder
                    .<PhantomSwordProjectile>of(PhantomSwordProjectile::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.PHANTOM_SWORD_PROJECTILE).toString()));

    public static final RegistryObject<EntityType<MagicArrowProjectile>> MAGIC_ARROW_PROJECTILE = ENTITY_TYPES.register(LibEntityNames.MAGIC_ARROW_PROJECTILE,
            () -> EntityType.Builder
                    .<MagicArrowProjectile>of(MagicArrowProjectile::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F)
                    .setUpdateInterval(10)
                    .setTrackingRange(64)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.PHANTOM_SWORD_PROJECTILE).toString()));

    //ego
    public static final RegistryObject<EntityType<EGO>> EGO = ENTITY_TYPES.register(LibEntityNames.EGO,
            () -> EntityType.Builder
                    .of(EGO::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .build(resId(LibEntityNames.PHANTOM_SWORD_PROJECTILE).toString()));
    public static final RegistryObject<EntityType<EGOMinion>> EGO_MINION = ENTITY_TYPES.register(LibEntityNames.EGO_MINION,
            () -> EntityType.Builder
                    .<EGOMinion>of(EGOMinion::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .setUpdateInterval(2)
                    .setTrackingRange(128)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.EGO_MINION).toString()));
    public static final RegistryObject<EntityType<EGOLandmine>> EGO_LANDMINE = ENTITY_TYPES.register(LibEntityNames.EGO_LANDMINE,
            () -> EntityType.Builder
                    .<EGOLandmine>of(EGOLandmine::new, MobCategory.MISC)
                    .sized(3F, 0.1F)
                    .setUpdateInterval(2)
                    .setTrackingRange(128)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(resId(LibEntityNames.EGO_LANDMINE).toString()));

    public static void registerAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> consumer) {
        consumer.accept(ExtraBotanyEntities.EGO.get(), Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.55)
                .add(Attributes.MAX_HEALTH, io.grasspow.extrabotany.common.entity.ego.EGO.MAX_HP)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ARMOR, 20)
                .add(Attributes.FOLLOW_RANGE, 35)
                .add(Attributes.ATTACK_DAMAGE, 8)
        );

        consumer.accept(ExtraBotanyEntities.EGO_MINION.get(), Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.FOLLOW_RANGE, 35)
                .add(Attributes.ATTACK_DAMAGE, 7)
        );

    }
}
