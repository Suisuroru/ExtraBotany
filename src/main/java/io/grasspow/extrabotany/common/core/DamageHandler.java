package io.grasspow.extrabotany.common.core;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class DamageHandler {
    public static float calcDamage(float orig, Player player) {
        if (player == null)
            return orig;
        double value = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return (float) (orig + value);
    }
}
