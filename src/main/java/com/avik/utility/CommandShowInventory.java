package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandShowInventory implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player viewer = (Player) sender;

        if (!viewer.hasPermission("utility.admin")) {
            viewer.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        if (args.length != 1) {
            viewer.sendMessage(ChatColor.RED + "Usage: /showinventory <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            Inventory inv = Bukkit.createInventory(null, 54, target.getName() + "'s Inventory");
            inv.setContents(target.getInventory().getContents());
            viewer.openInventory(inv);
            viewer.sendMessage(ChatColor.GREEN + "Opening inventory of " + target.getName());
        } else {
            viewer.sendMessage(ChatColor.RED + "Player not found.");
        }

        return true;
    }
}
