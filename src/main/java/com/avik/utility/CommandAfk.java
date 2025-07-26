package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandAFK implements CommandExecutor {

    private final Set<String> afkPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use /afk");
            return true;
        }

        Player player = (Player) sender;

        if (afkPlayers.contains(player.getName())) {
            afkPlayers.remove(player.getName());
            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " is no longer AFK.");
        } else {
            afkPlayers.add(player.getName());
            Bukkit.broadcastMessage(ChatColor.GRAY + player.getName() + " is now AFK.");
        }

        return true;
    }
}