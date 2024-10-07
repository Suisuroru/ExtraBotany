package io.grasspow.extrabotany.common.impl;

import io.grasspow.extrabotany.api.ExtraBotanyAPI;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class ExtraBotanyApiImpl implements ExtraBotanyAPI {
    @Override
    public int apiVersion() {
        return 1;
    }

    public float calcDamage(float orig, Player player) {
        if (player == null)
            return orig;
        double value = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return (float) (orig + value);
    }
}
