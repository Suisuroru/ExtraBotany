package io.grasspow.extrabotany.common.registry;

import com.mojang.datafixers.DSL;
import io.grasspow.extrabotany.common.entity.block.LivingrockBarrelBlockEntity;
import io.grasspow.extrabotany.common.entity.block.ManaBufferBlockEntity;
import io.grasspow.extrabotany.common.entity.block.PedestalBlockEntity;
import io.grasspow.extrabotany.common.entity.item.brew.EntitySplashGrenade;
import io.grasspow.extrabotany.common.libs.LibBlockNames;
import io.grasspow.extrabotany.common.libs.LibEntityNames;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

import static io.grasspow.extrabotany.common.libs.ResourceLocationHelper.resId;

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

        public static DeferredRegister<BlockEntityType<?>> getBlockEntityTypes() {
            return BLOCK_ENTITY_TYPES;
        }
    }

    public static class Brews {
        public static final EntityType<EntitySplashGrenade> SPLASH_GRENADE = EntityType.Builder
                .<EntitySplashGrenade>of(EntitySplashGrenade::new, MobCategory.MISC)
                .sized(0.05F, 0.05F)
                .setUpdateInterval(10)
                .setTrackingRange(64)
                .setShouldReceiveVelocityUpdates(true)
                .build(resId(LibEntityNames.SPLASH_GRENADE).toString());

        public static void registerEntities(BiConsumer<EntityType<?>, ResourceLocation> r) {
            r.accept(SPLASH_GRENADE, resId(LibEntityNames.SPLASH_GRENADE));
        }
    }
}
