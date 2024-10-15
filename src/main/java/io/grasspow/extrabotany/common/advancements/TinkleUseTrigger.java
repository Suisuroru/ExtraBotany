package io.grasspow.extrabotany.common.advancements;

import com.google.gson.JsonObject;
import io.grasspow.extrabotany.common.libs.LibAdvancementNames;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import static io.grasspow.extrabotany.common.libs.CommonHelper.resId;

public class TinkleUseTrigger extends SimpleCriterionTrigger<TinkleUseTrigger.Instance> {
    public static final ResourceLocation ID = resId(LibAdvancementNames.TINKLE_USE);
    public static final TinkleUseTrigger INSTANCE = new TinkleUseTrigger();

    private TinkleUseTrigger() {
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @NotNull
    @Override
    public Instance createInstance(@NotNull JsonObject json, ContextAwarePredicate playerPred, DeserializationContext conditions) {
        return new Instance(playerPred, LocationPredicate.fromJson(json.get("location")));
    }

    public void trigger(ServerPlayer player, ServerLevel world, BlockPos pos) {
        trigger(player, instance -> instance.test(world, pos));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final LocationPredicate pos;

        public Instance(ContextAwarePredicate playerPred, LocationPredicate pos) {
            super(ID, playerPred);
            this.pos = pos;
        }

        @NotNull
        @Override
        public ResourceLocation getCriterion() {
            return ID;
        }

        boolean test(ServerLevel world, BlockPos pos) {
            return this.pos.matches(world, pos.getX(), pos.getY(), pos.getZ());
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            if (pos != LocationPredicate.ANY) {
                json.add("location", pos.serializeToJson());
            }
            return json;
        }

        public LocationPredicate getPos() {
            return this.pos;
        }
    }
}
