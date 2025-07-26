package com.avik.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class CommandMute implements CommandExecutor {

    public static HashSet<UUID> mutedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /mute <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (mutedPlayers.contains(target.getUniqueId())) {
            mutedPlayers.remove(target.getUniqueId());
            sender.sendMessage(ChatColor.YELLOW + target.getName() + " has been unmuted.");
        } else {
            mutedPlayers.add(target.getUniqueId());
            sender.sendMessage(ChatColor.YELLOW + target.getName() + " has been muted.");
        }

        return true;
    }
}