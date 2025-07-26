package com.avik.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * UltimateUtility
 *
 * A comprehensive plugin combining multiple utility commands and features,
 * including player teleportation, moderation commands, player state management,
 * admin utilities, and placeholders for Bedrock crossplay.
 *
 * Author: Avik (you)
 * Version: 1.0
 */
public class UltimateUtility extends JavaPlugin implements Listener {

    private static UltimateUtility instance;

    // Player states
    private final Set<UUID> mutedPlayers = new HashSet<>();
    private final Set<UUID> afkPlayers = new HashSet<>();
    private final Set<UUID> godModePlayers = new HashSet<>();
    private final Set<UUID> flyingPlayers = new HashSet<>();

    // Teleport request map: requester UUID -> target UUID
    private final Map<UUID, UUID> teleportRequests = new HashMap<>();

    // Store vanished players if vanish feature added later
    // private final Set<UUID> vanishedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Register commands
        registerCommands();

        // Register event listeners
        getServer().getPluginManager().registerEvents(this, this);

        // Schedule periodic tasks (like AFK check or data saving) if needed
        schedulePeriodicTasks();

        getLogger().log(Level.INFO, "UltimateUtility plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "UltimateUtility plugin disabled!");
    }

    public static UltimateUtility getInstance() {
        return instance;
    }

    // COMMAND REGISTRATION
    private void registerCommands() {
        getCommand("rtp").setExecutor(new CommandRTP(this));
        getCommand("tp").setExecutor(new CommandTP(this));
        getCommand("tpa").setExecutor(new CommandTPA(this));
        getCommand("afk").setExecutor(new CommandAFK(this));
        getCommand("lobby").setExecutor(new CommandLobby(this));
        getCommand("reload_chunk").setExecutor(new CommandReloadChunk(this));

        getCommand("ban").setExecutor(new CommandBan(this));
        getCommand("unban").setExecutor(new CommandUnban(this));
        getCommand("mute").setExecutor(new CommandMute(this));

        getCommand("op").setExecutor(new CommandOP(this));
        getCommand("fly").setExecutor(new CommandFly(this));
        getCommand("god_mod").setExecutor(new CommandGodMod(this));
        getCommand("vc").setExecutor(new CommandVC(this));
        getCommand("showinventory").setExecutor(new CommandShowInventory(this));
        getCommand("adminlist").setExecutor(new CommandAdminList(this));

        getCommand("duels").setExecutor(new CommandDuels(this));
        getCommand("acceptduel").setExecutor(new CommandAcceptDuel(this));
        getCommand("denyduel").setExecutor(new CommandDenyDuel(this));
        getCommand("help").setExecutor(new CommandHelp(this));

        getCommand("ban").setTabCompleter(new TabCompleterBan());
        getCommand("mute").setTabCompleter(new TabCompleterMute());

        // Add more as needed
    }

    // Schedule periodic tasks for plugin features
    private void schedulePeriodicTasks() {
        // Example: AFK status clearing after inactivity could be added here
    }

    // WELCOME MESSAGE ON PLAYER JOIN
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String welcomeMessage = getConfig().getString("welcome-message", "&aWelcome to the server, &e%player%&a!");
        welcomeMessage = ChatColor.translateAlternateColorCodes('&', welcomeMessage.replace("%player%", player.getName()));

        player.sendMessage(welcomeMessage);

        // Optionally show admin list on join
        if (player.isOp()) {
            player.sendMessage(ChatColor.GOLD + "Online admins: " + ChatColor.YELLOW + getOnlineAdmins());
        }
    }

    // MUTE CHECK FOR CHAT MESSAGES
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (mutedPlayers.contains(player.getUniqueId())) {
            event.setCancelled(true);
            String mutedMsg = getConfig().getString("mute.muted-message", "&cYou are muted and cannot speak.");
            mutedMsg = ChatColor.translateAlternateColorCodes('&', mutedMsg);
            player.sendMessage(mutedMsg);
        }
    }

    // Handle AFK toggle when player moves
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (afkPlayers.contains(player.getUniqueId())) {
            afkPlayers.remove(player.getUniqueId());
            broadcastMessage(ChatColor.YELLOW + player.getName() + " is no longer AFK.");
        }
    }

    // Handle Player Quit - remove states
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        mutedPlayers.remove(uuid);
        afkPlayers.remove(uuid);
        godModePlayers.remove(uuid);
        flyingPlayers.remove(uuid);
        teleportRequests.remove(uuid);
    }

    // UTILITIES

    public boolean isPlayerMuted(UUID uuid) {
        return mutedPlayers.contains(uuid);
    }

    public boolean isPlayerAFK(UUID uuid) {
        return afkPlayers.contains(uuid);
    }

    public boolean isPlayerGodMode(UUID uuid) {
        return godModePlayers.contains(uuid);
    }

    public boolean isPlayerFlying(UUID uuid) {
        return flyingPlayers.contains(uuid);
    }

    public void setPlayerMuted(UUID uuid, boolean muted) {
        if (muted) mutedPlayers.add(uuid);
        else mutedPlayers.remove(uuid);
    }

    public void setPlayerAFK(UUID uuid, boolean afk) {
        if (afk) afkPlayers.add(uuid);
        else afkPlayers.remove(uuid);
    }

    public void setPlayerGodMode(UUID uuid, boolean god) {
        if (god) godModePlayers.add(uuid);
        else godModePlayers.remove(uuid);
    }

    public void setPlayerFlying(UUID uuid, boolean fly) {
        if (fly) flyingPlayers.add(uuid);
        else flyingPlayers.remove(uuid);
    }

    // Broadcast utility
    public void broadcastMessage(String message) {
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    // Get all online admins
    public String getOnlineAdmins() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(Player::isOp)
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    // Example random safe teleport method
    public void safeRandomTeleport(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = player.getWorld();
                Random random = new Random();
                Location loc;
                int attempts = 0;

                do {
                    int x = random.nextInt(2000) - 1000;
                    int z = random.nextInt(2000) - 1000;
                    int y = world.getHighestBlockYAt(x, z) + 1;
                    loc = new Location(world, x + 0.5, y, z + 0.5);
                    attempts++;
                    if (attempts > 20) break; // fail safe
                } while (!isLocationSafe(loc));

                Bukkit.getScheduler().runTask(UltimateUtility.this, () -> {
                    player.teleport(loc);
                    player.sendMessage(ChatColor.GREEN + "Teleported to a random safe location!");
                    player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                });
            }
        }.runTaskAsynchronously(this);
    }

    // Safety check for location
    private boolean isLocationSafe(Location loc) {
        return loc.getBlock().getType().isSolid() == false
                && loc.clone().add(0, 1, 0).getBlock().getType().isSolid() == false;
    }

    // Reload config helper
    public void reloadPluginConfig(CommandSender sender) {
        reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "UltimateUtility config reloaded!");
    }

    // Placeholder for teleport requests
    public void addTeleportRequest(UUID requester, UUID target) {
        teleportRequests.put(requester, target);
    }

    public UUID getTeleportRequestTarget(UUID requester) {
        return teleportRequests.get(requester);
    }

    public void removeTeleportRequest(UUID requester) {
        teleportRequests.remove(requester);
    }

    // Placeholder for crossplay integration note
    public void setupCrossplaySupport() {
        // Complex feature, usually handled by proxy plugins like GeyserMC.
        getLogger().info("Crossplay support must be enabled via GeyserMC or similar proxy.");
    }
}