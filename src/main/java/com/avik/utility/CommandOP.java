package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandOP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /op <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            target.setOp(true);
            sender.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " is now OP.");
        } else {
            sender.sendMessage(ChatColor.RED + "Player not found.");
        }

        return true;
    }
}
