package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandDenyDuel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player denier = (Player) sender;

        if (!CommandAcceptDuel.duelRequests.containsKey(denier.getUniqueId())) {
            denier.sendMessage(ChatColor.RED + "You have no pending duel requests to deny.");
            return true;
        }

        UUID challengerUUID = CommandAcceptDuel.duelRequests.remove(denier.getUniqueId());
        Player challenger = Bukkit.getPlayer(challengerUUID);

        denier.sendMessage(ChatColor.YELLOW + "You denied the duel request.");

        if (challenger != null && challenger.isOnline()) {
            challenger.sendMessage(ChatColor.RED + denier.getName() + " has denied your duel request.");
        }

        return true;
    }
}