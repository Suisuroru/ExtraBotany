package io.grasspow.extrabotany.common.item.equipment.bauble;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.grasspow.extrabotany.api.capability.IAdvancementRequirement;
import io.grasspow.extrabotany.client.handler.MiscellaneousModels;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.client.render.AccessoryRenderRegistry;
import vazkii.botania.client.render.AccessoryRenderer;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.CustomCreativeTabContents;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.common.proxy.Proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoreGodItem extends RelicBaubleItem implements CustomCreativeTabContents, IAdvancementRequirement {
    private static final String TAG_VARIANT = "variant";

    private static final List<String> playersWithFlight = Collections.synchronizedList(new ArrayList<>());
    private static final int COST = 35;

    private static final int SUBTYPES = 4;

    public CoreGodItem(Item.Properties props) {
        super(props);
        MinecraftForge.EVENT_BUS.addListener(this::updatePlayerFlyStatus);
        Proxy.INSTANCE.runOnClient(() -> () -> AccessoryRenderRegistry.register(this, new Renderer()));
    }

    @Override
    public void addToCreativeTab(Item me, CreativeModeTab.Output output) {
        for (int i = 0; i < SUBTYPES; i++) {
            ItemStack stack = new ItemStack(this);
            ItemNBTHelper.setInt(stack, TAG_VARIANT, i);
            output.accept(stack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        tooltip.add(Component.translatable("extrabotany.wings" + getVariant(stack)).withStyle(ChatFormatting.GRAY));
    }

    private void updatePlayerFlyStatus(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack tiara = EquipmentHandler.findOrEmpty(this, player);

            if (playersWithFlight.contains(playerStr(player))) {
                if (shouldPlayerHaveFlight(player)) {
                    player.getAbilities().mayfly = true;
                    if (player.getAbilities().flying) {
                        if (!player.level().isClientSide) {
                            ManaItemHandler.instance().requestManaExact(tiara, player, COST, true);
                        } else if (Math.abs(player.getDeltaMovement().x()) > 0.1 || Math.abs(player.getDeltaMovement().z()) > 0.1) {
                            double x = event.getEntity().getX() - 0.5;
                            double y = event.getEntity().getY() - 0.5;
                            double z = event.getEntity().getZ() - 0.5;

                            float r = 114F;
                            float g = 114F;
                            float b = 114F;

                            int variant = getVariant(tiara);

                            switch (variant) {
                                case 0 -> {
                                    r = 1F;
                                    g = 0.55F;
                                    b = 0F;
                                }
                                case 1 -> {
                                    r = new float[]{0.4F, 0.98F, 0.98F, 0.98F, 0.6F, 0F, 0.15F}[player.level().random.nextInt(7)];
                                    g = new float[]{0.82F, 0.84F, 0.52F, 0.12F, 0.21F, 0.4F, 0.98F}[player.level().random.nextInt(7)];
                                    b = new float[]{0F, 0.18F, 0.18F, 0F, 0.98F, 0.81F, 0.82F}[player.level().random.nextInt(7)];
                                }
                                case 2 -> {
                                    r = 0.52F;
                                    g = 0.8F;
                                    b = 0.85F;
                                }
                                case 3 -> {
                                    r = 0.95F;
                                    g = 0.7F;
                                    b = 0.38F;
                                }
                            }

                            for (int i = 0; i < 2; i++) {
                                SparkleParticleData data = SparkleParticleData.sparkle(2F * (float) Math.random(), r, g, b, 20);
                                player.level().addParticle(data,
                                        x + Math.random() * event.getEntity().getBbWidth(), y + Math.random() * 0.4, z + Math.random() * event.getEntity().getBbWidth(),
                                        0, 0, 0);
                            }
                        }
                    }
                } else {
                    if (!player.isSpectator() && !player.isCreative()) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.getAbilities().invulnerable = false;
                    }
                    playersWithFlight.remove(playerStr(player));
                }
            } else if (shouldPlayerHaveFlight(player)) {
                playersWithFlight.add(playerStr(player));
                player.getAbilities().flying = true;
            }
        }
    }

    public static void playerLoggedOut(ServerPlayer player) {
        String username = player.getGameProfile().getName();
        playersWithFlight.remove(username + ":false");
        playersWithFlight.remove(username + ":true");
    }

    private static String playerStr(Player player) {
        return player.getGameProfile().getName() + ":" + player.level().isClientSide;
    }

    private boolean shouldPlayerHaveFlight(Player player) {
        ItemStack armor = EquipmentHandler.findOrEmpty(this, player);
        return !armor.isEmpty() && ManaItemHandler.instance().requestManaExact(armor, player, COST, false);
    }

    @Override
    public boolean hasRender(ItemStack stack, LivingEntity living) {
        return super.hasRender(stack, living) && living instanceof Player;
    }

    @Override
    public String getAdvancementName() {
        return LibAdvancementNames.EGO_DEFEAT;
    }

    public static class Renderer implements AccessoryRenderer {
        @Override
        public void doRender(HumanoidModel<?> bipedModel, ItemStack stack, LivingEntity living, PoseStack ms, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            int meta = getVariant(stack);
            if (meta < 0 || meta >= MiscellaneousModels.INSTANCE.coreGodWingsModel.length + 1) {
                return;
            }

            var model = MiscellaneousModels.INSTANCE.coreGodWingsModel[meta];
            if (model != null) {
                boolean flying = living instanceof Player && ((Player) living).getAbilities().flying;
                float flap = 12F + (float) ((Math.sin((double) (living.tickCount + partialTicks) * (flying ? 0.2F : 0.12F)) + 0.4F) * (flying ? 30F : 5F));

                switch (meta) {
                    case 0 -> renderHerrscher(bipedModel, model, stack, ms, buffers, flap);
                    case 1 -> renderBasic(bipedModel, model, stack, ms, buffers, light, flap * 0.25F);
                    case 2, 3 -> renderBasic(bipedModel, model, stack, ms, buffers, light, flap);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderHerrscher(HumanoidModel<?> bipedModel, BakedModel model, ItemStack stack, PoseStack ms, MultiBufferSource buffers, float flap) {
        ms.pushPose();
        bipedModel.body.translateAndRotate(ms);
        ms.translate(0, -0.2, 0.3);


        for (int i = 0; i < 3; i++) {
            ms.pushPose();
            ms.mulPose(Axis.YP.rotationDegrees(flap * 0.25F));
            ms.mulPose(Axis.ZP.rotationDegrees(-35F * i));
            BakedModel model_ = MiscellaneousModels.INSTANCE.coreGodModel;
            ms.translate(-1.2, -0.1F * i, 0);

            ms.scale(1.9F, -1.9F, -1.9F);
            Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE,
                    false, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY, model_);
            ms.popPose();
        }

        ms.pushPose();
        ms.mulPose(Axis.YP.rotationDegrees(180 - flap * 0.25F));

        ms.translate(-1.2, 0, 0);

        ms.scale(1.7F, -1.7F, -1.7F);
        Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE,
                false, ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY, model);
        ms.popPose();


        ms.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    private static void renderBasic(HumanoidModel<?> bipedModel, BakedModel model, ItemStack stack, PoseStack ms, MultiBufferSource buffers, int light, float flap) {
        ms.pushPose();

        // attach to body
        bipedModel.body.translateAndRotate(ms);

        // position on body
        ms.translate(0, 0.2, 0.2);

        for (int i = 0; i < 2; i++) {
            ms.pushPose();
            ms.mulPose(Axis.YP.rotationDegrees(i == 0 ? flap : 180 - flap));

            // move so flapping about the edge instead of center of texture
            ms.translate(-1, 0, 0);

            ms.scale(1.5F, -1.5F, -1.5F);
            Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.NONE,
                    false, ms, buffers, light, OverlayTexture.NO_OVERLAY, model);
            ms.popPose();
        }

        ms.popPose();
    }

    public static int getVariant(ItemStack stack) {
        return ItemNBTHelper.getInt(stack, TAG_VARIANT, 0);
    }

    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null);
    }

}
