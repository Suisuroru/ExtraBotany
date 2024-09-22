package io.grasspow.extrabotany.common.handler;

import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandlerModifiable;
import top.theillusivec4.curios.api.CuriosApi;
import vazkii.botania.common.helper.PlayerHelper;

import java.util.Map;

@Mod.EventBusSubscriber
public class AdvancementHandler {
    public static final AdvancementHandler INSTANCE = new AdvancementHandler();

    public void grantAdvancement(ServerPlayer player, String id) {
        PlayerHelper.grantCriterion(player, new ResourceLocation(LibMisc.MOD_ID, "main/" + id), "code_triggered");
    }

    private static ResourceLocation tryCreateResourceLocation(String string) {
        try {
            return new ResourceLocation(string);
        } catch (ResourceLocationException resourcelocationexception) {
            return null;
        }
    }

    public static boolean checkAdvancement(Player player, String modid, String advancement) {
        ResourceLocation id = tryCreateResourceLocation(modid + ":main/" + advancement);
        if (id != null) {
            if (player instanceof ServerPlayer) {
                ServerAdvancementManager manager = player.getServer().getAdvancements();
                PlayerAdvancements advancements = ((ServerPlayer) player).getAdvancements();
                Advancement adv = manager.getAdvancement(id);
                if (adv != null)
                    return advancements.getOrStartProgress(adv).isDone();
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static Advancement getSideAdvancement(String modid, String advancement) {
        ResourceLocation id = tryCreateResourceLocation(modid + ":main/" + advancement);
        if (id != null) {
            ClientPacketListener connection = Minecraft.getInstance().player.connection;
            ClientAdvancements manager = connection.getAdvancements();
            return manager.getAdvancements().get(id);
        }
        return null;
    }

    public static boolean hasDone(String modid, String advancement) {
        ResourceLocation id = tryCreateResourceLocation(modid + ":main/" + advancement);
        if (id != null) {
            ClientPacketListener conn = Minecraft.getInstance().getConnection();
            if (conn != null) {
                ClientAdvancements cm = conn.getAdvancements();
                Advancement adv = cm.getAdvancements().get(id);
                if (adv != null) {
                    Map<Advancement, AdvancementProgress> progressMap = ObfuscationReflectionHelper.getPrivateValue(ClientAdvancements.class, cm, "field_192803_d");
                    AdvancementProgress progress = progressMap.get(adv);
                    return progress != null && progress.isDone();
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (event.isCancelable() && !event.getEntity().isCreative()) {
            if (event.getItemStack().getItem() instanceof IAdvancementRequirement r) {
                if (!checkAdvancement(event.getEntity(), LibMisc.MOD_ID, r.getAdvancementName()))
                    event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof IAdvancementRequirement r) {
            LocalPlayer playerSP = Minecraft.getInstance().player;
            if (playerSP != null) {
                if (!hasDone(LibMisc.MOD_ID, r.getAdvancementName()))
                    event.getToolTip()
                            .add(Component.translatable(
                                    "extrabotanymisc.description",
                                    Component.translatable("extrabotany." + r.getAdvancementName() + ".title")
                            ).withStyle(style -> style.withColor(ChatFormatting.RED)));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof Player player && event.getEntity().getCommandSenderWorld().isClientSide()) {
            if (player.isCreative()) {
                return;
            }

            CuriosApi.getCuriosInventory(player).ifPresent(curios->{
                IItemHandlerModifiable handler = curios.getEquippedCurios();
                for (int i = 0; i < handler.getSlots(); i++) {
                    final ItemStack stack = handler.getStackInSlot(i);
                    if (stack.getItem() instanceof IAdvancementRequirement r) {
                        if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                            handler.setStackInSlot(i, ItemStack.EMPTY);
                            player.drop(stack, false);
                        }
                    }
                }
            });

            for (final EquipmentSlot slot : EquipmentSlot.values()) {
                final ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof IAdvancementRequirement r) {
                    if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                        player.setItemSlot(slot, ItemStack.EMPTY);
                        player.drop(stack, false);
                    }
                }
            }
        }

    }
}
