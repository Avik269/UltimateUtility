package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandAdminList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(ChatColor.GOLD + "=== Online Admins ===");

        boolean found = false;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                sender.sendMessage(ChatColor.AQUA + "- " + player.getName());
                found = true;
            }
        }

        if (!found) {
            sender.sendMessage(ChatColor.GRAY + "No admins are currently online.");
        }

        return true;
    }
}