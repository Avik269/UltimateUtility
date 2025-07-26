package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandTPA implements CommandExecutor {

    private static final Set<String> tpaRequests = new HashSet<>();

    public static boolean acceptRequest(Player accepter) {
        for (String req : new HashSet<>(tpaRequests)) {
            String[] parts = req.split(":");
            if (parts[1].equals(accepter.getName())) {
                Player requester = Bukkit.getPlayer(parts[0]);
                if (requester != null) {
                    requester.teleport(accepter);
                    requester.sendMessage("Teleporting...");
                    tpaRequests.remove(req);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean denyRequest(Player denier) {
        return tpaRequests.removeIf(req -> req.endsWith(":" + denier.getName()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (args.length < 1) return false;
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            target.sendMessage(ChatColor.AQUA + p.getName() + " has requested to teleport to you. Type /tpaccept or /tpdeny");
            tpaRequests.add(p.getName() + ":" + target.getName());
            p.sendMessage("Teleport request sent.");
        } else {
            p.sendMessage("Player not found.");
        }
        return true;
    }
}
