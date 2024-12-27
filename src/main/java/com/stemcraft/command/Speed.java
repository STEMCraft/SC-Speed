package com.stemcraft.command;

import com.stemcraft.STEMCraftCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Speed extends STEMCraftCommand {
    @Override
    public void execute(CommandSender sender, String command, List<String> args) {
        String usage = "Usage: /speed (walk|fly) [speed] (player)";

        if (!sender.hasPermission("stemcraft.speed")) {
            message(sender, "You do not have permission to use this command");
            return;
        }

        if (args.isEmpty()) {
            message(sender, usage);
            return;
        }

        boolean reset = false;
        Boolean flying = null;
        float speed = 1;
        Player target;

        int argNum = 1;
        String arg = args.getFirst().toLowerCase();
        switch (arg) {
            case "fly" -> {
                flying = true;
                argNum = 2;
            }
            case "walk" -> {
                flying = false;
                argNum = 2;
            }
            case "reset" -> {
                reset = true;
                argNum = 2;
            }
        }

        if (!reset) {
            try {
                speed = Float.parseFloat(args.get(argNum));
                argNum++;
            } catch (NumberFormatException e) {
                message(sender, usage);
                return;
            }
        }

        if (args.size() > argNum) {
            target = Bukkit.getPlayerExact(args.get(argNum));
            if (target == null) {
                message(sender, "The player {player} was not found or online", "player", args.get(argNum));
                return;
            }
        } else if (!(sender instanceof Player)) {
            message(sender, "A player name is required when using this command from the console");
            return;
        } else {
            target = (Player) sender;
        }

        if (reset) {
            plugin.resetPlayerSpeed(target);
            message(sender, "The speed for player {player} has been reset", "player", target.getName());
        } else {
            if (speed < 0.1f) {
                speed = 0.1f;
            } else if (speed > 10f) {
                speed = 10f;
            }

            if (flying == null) {
                flying = target.isFlying();
            }

            plugin.setPlayerSpeed(target, speed, flying);
            message(sender, "The {type} speed for player {player} has been changed to {speed}", "type", (flying ? "flying" : "walking"), "player", target.getName(), "speed", String.valueOf(speed));
        }
    }
}
