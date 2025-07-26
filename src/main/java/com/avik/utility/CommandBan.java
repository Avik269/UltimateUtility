package com.avik.utility;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Date;

public class CommandBan implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /ban <player>");
            return true;
        }

        String targetName = args[0];
        Bukkit.getBanList(BanList.Type.NAME).addBan(targetName, "Banned by admin", null, sender.getName());
        Player target = Bukkit.getPlayerExact(targetName);

        if (target != null) {
            target.kickPlayer("You have been banned.");
        }

        Bukkit.broadcastMessage(ChatColor.RED + targetName + " has been banned.");
        return true;
    }
}