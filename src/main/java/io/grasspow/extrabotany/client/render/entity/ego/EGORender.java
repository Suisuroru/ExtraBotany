package io.grasspow.extrabotany.client.render.entity.ego;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import io.grasspow.extrabotany.client.model.armor.ArmorModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class EGORender extends HumanoidMobRenderer<Monster, HumanoidModel<Monster>> {
    private static final Cache<String, GameProfile> GAME_PROFILE_CACHE = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());
    private static final GameProfile EMPTY_GAME_PROFILE = new GameProfile(null, "EMPTY");

    private static final ResourceLocation TEXTURE_ALEX = new ResourceLocation("textures/entity/alex.png");


    public EGORender(EntityRendererProvider.Context ctx) {
        super(ctx, new Model(ctx.bakeLayer(ModelLayers.PLAYER)), 0F);
        ArmorModels.init(ctx);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@NotNull Monster mob) {
        if (mob.getCustomName() != null)
            return getPlayerSkin(mob.getCustomName().getString());
        else
            return getPlayerSkin("Gold_Chick");
    }


    public static ResourceLocation getPlayerSkin(String name) {
        GameProfile newProfile = null;
        Minecraft minecraft = Minecraft.getInstance();
        try {
            newProfile = GAME_PROFILE_CACHE.get(name, () ->
            {
                THREAD_POOL.submit(() ->
                {
                    GameProfile profile = new GameProfile(null, name);
                    SkullBlockEntity.updateGameprofile(profile, profileNew ->
                    {
                        {
                            if (profileNew != null) {
                                GAME_PROFILE_CACHE.put(name, profileNew);
                            }
                        }
                    });
                });
                return EMPTY_GAME_PROFILE;
            });
        } catch (ExecutionException ignore) {
        }

        if (newProfile != null) {
            Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(newProfile);
            if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                return minecraft.getSkinManager().registerTexture(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            } else {
                UUID uuid = newProfile.getId();
                if (uuid == null) {
                    uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + newProfile.getName()).getBytes(StandardCharsets.UTF_8));
                }
                return DefaultPlayerSkin.getDefaultSkin(uuid);
            }
        }
        return TEXTURE_ALEX;
    }

    private static class Model extends HumanoidModel<Monster> {
        Model(ModelPart root) {
            super(root, RenderType::entityCutoutNoCull);
        }
    }
}
