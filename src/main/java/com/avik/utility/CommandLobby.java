package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandLobby implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        Location lobby = new Location(Bukkit.getWorld("world"), 0.5, 100, 0.5);
        p.teleport(lobby);
        p.sendMessage(ChatColor.GREEN + "Teleported to lobby!");

        return true;
    }
}
