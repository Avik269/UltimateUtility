package com.avik.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("utility.admin")) {
            p.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        boolean canFly = p.getAllowFlight();
        p.setAllowFlight(!canFly);
        p.sendMessage(ChatColor.YELLOW + "Flight mode " + (canFly ? "disabled" : "enabled"));

        return true;
    }
}
