package com.avik.utility;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandGodMod implements CommandExecutor {

    private final Set<String> godPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (!p.hasPermission("utility.admin")) {
            p.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        if (godPlayers.contains(p.getName())) {
            godPlayers.remove(p.getName());
            p.setInvulnerable(false);
            p.sendMessage(ChatColor.RED + "God mode disabled.");
        } else {
            godPlayers.add(p.getName());
            p.setInvulnerable(true);
            p.sendMessage(ChatColor.GREEN + "God mode enabled.");
        }

        return true;
    }
}
