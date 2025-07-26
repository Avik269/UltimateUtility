package com.avik.utility.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.avik.utility.UltimateUtility;

import java.util.List;

public class CommandMakeAdmin implements CommandExecutor {

    private final UltimateUtility plugin;

    public CommandMakeAdmin(UltimateUtility plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ultimateutility.makeadmin")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /makeadmin <player>");
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);

        // Load admin list from config
        List<String> admins = plugin.getConfig().getStringList("admins");

        if (admins.contains(targetName)) {
            sender.sendMessage(ChatColor.RED + targetName + " is already an admin.");
            return true;
        }

        admins.add(targetName);
        plugin.getConfig().set("admins", admins);
        plugin.saveConfig();

        sender.sendMessage(ChatColor.GREEN + targetName + " has been made an admin!");

        if (target != null && target.isOnline()) {
            target.sendMessage(ChatColor.GREEN + "You have been made an admin!");
        }

        return true;
    }
}