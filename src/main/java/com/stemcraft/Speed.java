package com.stemcraft;

import com.stemcraft.common.STEMCraftPlugin;
import org.bukkit.entity.Player;


public class Speed extends STEMCraftPlugin {
    private static Speed instance;

    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;
        registerTabCompletion("speedtype", "fly", "walk");
        registerTabCompletion("speed", "1", "1.5", "1.75", "2");

        registerCommand(new com.stemcraft.commands.Speed(instance));
    }

    public Speed getInstance() {
        return instance;
    }

    public void setPlayerSpeed(Player player, float speed, boolean flying) {
        if (flying) {
            player.setFlySpeed(getRealSpeed(speed, true));
        } else {
            player.setWalkSpeed(getRealSpeed(speed, false));
        }
    }

    public void resetPlayerSpeed(Player player) {
        player.setFlySpeed(getDefaultSpeed(true));
        player.setWalkSpeed(getDefaultSpeed(false));
    }

    private float getDefaultSpeed(final boolean isFly) {
        return isFly ? 0.1f : 0.2f;
    }

    private float getRealSpeed(final float speed, final boolean isFly) {
        final float defaultSpeed = getDefaultSpeed(isFly);
        float maxSpeed = 1f;

        if (speed < 1f) {
            return defaultSpeed * speed;
        } else {
            final float ratio = ((speed - 1) / 9) * (maxSpeed - defaultSpeed);
            return ratio + defaultSpeed;
        }
    }
}
