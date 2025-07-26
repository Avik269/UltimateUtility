package com.avik.utility;

import org.bukkit.Chunk;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CommandReloadChunk implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        Chunk chunk = p.getLocation().getChunk();
        p.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
        p.sendMessage("Chunk reloaded!");

        return true;
    }
}
