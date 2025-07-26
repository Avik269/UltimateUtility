package com.avik.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHelp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "==== UltimateUtility Commands ====");
        sender.sendMessage(ChatColor.YELLOW + "/tp <player> " + ChatColor.GRAY + "- Teleport to a player");
        sender.sendMessage(ChatColor.YELLOW + "/tpa <player> " + ChatColor.GRAY + "- Request teleport to a player");
        sender.sendMessage(ChatColor.YELLOW + "/rtp " + ChatColor.GRAY + "- Random teleport");
        sender.sendMessage(ChatColor.YELLOW + "/afk " + ChatColor.GRAY + "- Toggle AFK status");
        sender.sendMessage(ChatColor.YELLOW + "/lobby " + ChatColor.GRAY + "- Go to the main lobby");
        sender.sendMessage(ChatColor.YELLOW + "/vc " + ChatColor.GRAY + "- Voice chat toggle");
        sender.sendMessage(ChatColor.YELLOW + "/fly " + ChatColor.GRAY + "- Toggle flight (admin only)");
        sender.sendMessage(ChatColor.YELLOW + "/god_mod " + ChatColor.GRAY + "- Toggle god mode (admin only)");
        sender.sendMessage(ChatColor.YELLOW + "/showinventory <player> " + ChatColor.GRAY + "- See player inventory (admin)");
        sender.sendMessage(ChatColor.YELLOW + "/reload_chunk " + ChatColor.GRAY + "- Reload current chunk");
        sender.sendMessage(ChatColor.YELLOW + "/duels <player> " + ChatColor.GRAY + "- Challenge a player to a duel");
        return true;
    }
}