package com.avik.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandVC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("utility.admin")) {
            p.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        p.sendMessage(ChatColor.LIGHT_PURPLE + "Voice chat is not available. (Placeholder)");
        return true;
    }
}
