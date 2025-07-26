package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandAcceptDuel implements CommandExecutor {

    // Store duel requests where key = challenged player, value = challenger
    public static HashMap<UUID, UUID> duelRequests = new HashMap<>();

    public static void addDuelRequest(Player challenger, Player target) {
        duelRequests.put(target.getUniqueId(), challenger.getUniqueId());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player accepter = (Player) sender;

        if (!duelRequests.containsKey(accepter.getUniqueId())) {
            accepter.sendMessage(ChatColor.RED + "You have no pending duel requests.");
            return true;
        }

        UUID challengerUUID = duelRequests.remove(accepter.getUniqueId());
        Player challenger = Bukkit.getPlayer(challengerUUID);

        if (challenger == null || !challenger.isOnline()) {
            accepter.sendMessage(ChatColor.RED + "The challenger is no longer online.");
            return true;
        }

        accepter.sendMessage(ChatColor.GREEN + "You have accepted the duel with " + challenger.getName());
        challenger.sendMessage(ChatColor.GREEN + accepter.getName() + " has accepted your duel!");

        // Start duel logic (teleport to arena, etc.)
        // For now, just send a message
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + accepter.getName() + " and " + challenger.getName() + " are now dueling!");

        return true;
    }
}