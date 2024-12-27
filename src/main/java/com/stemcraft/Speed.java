package com.stemcraft;

import com.stemcraft.listener.PlayerWorldChangeListener;
import com.stemcraft.util.SCTabCompletion;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class Speed extends STEMCraftPlugin {
    @Override
    public void onEnable() {
        super.onEnable();

        SCTabCompletion.register("speedtype", "fly", "walk");
        SCTabCompletion.register("speed", "1", "1.5", "1.75", "2");

        List<String[]> tabCompletions = new ArrayList<>();
        tabCompletions.add(new String[]{"{speedtype}", "{speed}", "{player}"});
        tabCompletions.add(new String[]{"{speed}", "{player}"});
        tabCompletions.add(new String[]{"reset", "{player}"});

        registerEvents(new PlayerWorldChangeListener());

        registerCommand(new com.stemcraft.command.Speed(), "speed", null, tabCompletions);
    }

    public static void setPlayerSpeed(Player player, float speed, boolean flying) {
        if (flying) {
            player.setFlySpeed(getRealSpeed(speed, true));
        } else {
            player.setWalkSpeed(getRealSpeed(speed, false));
        }
    }

    public static void resetPlayerSpeed(Player player) {
        player.setFlySpeed(getDefaultSpeed(true));
        player.setWalkSpeed(getDefaultSpeed(false));
    }

    private static float getDefaultSpeed(final boolean isFly) {
        return isFly ? 0.1f : 0.2f;
    }

    private static float getRealSpeed(final float speed, final boolean isFly) {
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
