package com.avik.utility;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandRTP implements CommandExecutor {

    private final UltimateUtility plugin;

    public CommandRTP(UltimateUtility plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use /rtp.");
            return true;
        }

        Player player = (Player) sender;

        FileConfiguration config = plugin.getConfig();

        // Fetch messages from config
        String searchingMsg = ChatColor.translateAlternateColorCodes('&',
                config.getString("rtp.searching-message", "&7Searching for a safe location..."));
        String successMsg = ChatColor.translateAlternateColorCodes('&',
                config.getString("rtp.success-message", "&aTeleported randomly!"));

        player.sendMessage(searchingMsg);

        // Random teleport logic
        Random random = new Random();
        World world = player.getWorld();
        int x = random.nextInt(2000) - 1000;
        int z = random.nextInt(2000) - 1000;
        int y = world.getHighestBlockYAt(x, z) + 1;

        Location randomLocation = new Location(world, x + 0.5, y, z + 0.5);

        // Play sound and teleport
        player.teleport(randomLocation);
        player.sendMessage(successMsg);
        player.playSound(randomLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

        return true;
    }
}