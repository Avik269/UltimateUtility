package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandDUELS implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player challenger = (Player) sender;

        if (args.length != 1) {
            challenger.sendMessage(ChatColor.RED + "Usage: /duels <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target == null || !target.isOnline()) {
            challenger.sendMessage(ChatColor.RED + "Player not found or not online.");
            return true;
        }

        if (target == challenger) {
            challenger.sendMessage(ChatColor.RED + "You cannot duel yourself.");
            return true;
        }

        target.sendMessage(ChatColor.GOLD + challenger.getName() + " has challenged you to a duel!");
        challenger.sendMessage(ChatColor.GREEN + "Duel request sent to " + target.getName());

        // Expand this with actual duel mechanics or arenas
        return true;
    }
}