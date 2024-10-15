package io.grasspow.extrabotany.common.handler;

import io.grasspow.extrabotany.api.capability.IAdvancementRequirement;
import io.grasspow.extrabotany.common.libs.LibMisc;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;
import static vazkii.patchouli.client.base.ClientAdvancements.hasDone;

@Mod.EventBusSubscriber
public class AdvancementHandler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() instanceof IAdvancementRequirement) {
            IAdvancementRequirement r = (IAdvancementRequirement) event.getItemStack().getItem();
            Player playerSP = Minecraft.getInstance().player;
            if (playerSP != null) {
                if (!hasDone(resId("main/" + r.getAdvancementName()).toString()))
                    event.getToolTip().add(Component.translatable("advancement.item_disabled.desc", Component.translatable("advancement.extrabotany." + r.getAdvancementName() + ".title").withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.RED));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            if (player.isCreative()) {
                return;
            }
            CuriosApi.getCuriosInventory(player).ifPresent((c) -> {
                for (int i = 0; i < c.getSlots(); i++) {
                    final ItemStack stack = c.getEquippedCurios().getStackInSlot(i);
                    if (stack.getItem() instanceof IAdvancementRequirement) {
                        IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                        if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                            c.getEquippedCurios().setStackInSlot(i, ItemStack.EMPTY);
                            player.drop(stack, false);
                        }
                    }
                }
            });
            if (ConfigHandler.COMMON.doStrictAdvancementChecking.get()) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    final ItemStack stack = player.getInventory().getItem(i);
                    if (stack.getItem() instanceof IAdvancementRequirement) {
                        IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                        if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                            player.getInventory().setItem(i, ItemStack.EMPTY);
                            player.drop(stack, false);
                        }
                    }
                }
            }
            for (final EquipmentSlot slot : EquipmentSlot.values()) {
                final ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof IAdvancementRequirement) {
                    IAdvancementRequirement r = (IAdvancementRequirement) stack.getItem();
                    if (!checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                        player.setItemSlot(slot, ItemStack.EMPTY);
                        player.drop(stack, false);
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void onLivingPickUpItem(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            if (player.isCreative()) {
                return;
            }
            if (event.getItem().getItem().getItem() instanceof IAdvancementRequirement) {
                IAdvancementRequirement r = (IAdvancementRequirement) event.getItem().getItem().getItem();
                if (!event.isCanceled() && !checkAdvancement(player, LibMisc.MOD_ID, r.getAdvancementName())) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static boolean checkAdvancement(Player player, String mod_id, String advancement) {
        ResourceLocation id = ResourceLocation.tryParse(mod_id + ":main/" + advancement);
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
}

