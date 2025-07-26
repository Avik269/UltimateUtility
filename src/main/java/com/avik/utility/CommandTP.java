package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandTP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (args.length < 1) {
            p.sendMessage(ChatColor.RED + "Usage: /tp <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            p.teleport(target);
            p.sendMessage(ChatColor.YELLOW + "Teleported to " + target.getName());
        } else {
            p.sendMessage(ChatColor.RED + "Player not found.");
        }
        return true;
    }
}
