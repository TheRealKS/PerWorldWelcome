package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.PerWorldWelcome;
import com.koens.perworldwelcome.util.ConfigUser;
import com.koens.perworldwelcome.util.WorldGrouping;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import static com.koens.perworldwelcome.util.WorldGrouping.groupWorlds;

public class WorldChangeListener extends ConfigUser implements Listener {

    private String joinMsg;
    private String leaveMsg;
    private String globalMsg;
    private String firstJoinMsg;
    private String firstJoinGlobalMsg;
    private static final char AND = '&';
    private boolean global;
    private boolean broadcastFirstJoin;
    private boolean worldGrouping;
    private boolean suppress;

    private PerWorldWelcome plugin;

    private YamlConfiguration playerConfig;

    public WorldChangeListener(String a, String b, String c, String d, String e, boolean firstjoin, boolean wg, boolean g, boolean sg, YamlConfiguration yml, PerWorldWelcome welcome) {
        this.joinMsg = a;
        this.leaveMsg = b;
        this.globalMsg = c;
        this.firstJoinMsg = d;
        this.firstJoinGlobalMsg = e;
        this.global = g;
        this.broadcastFirstJoin = firstjoin;
        this.worldGrouping = wg;
        this.suppress = sg;

        this.plugin = welcome;
        this.playerConfig = yml;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        if (suppress && event.getFrom().getName().split("_")[0].equals(event.getPlayer().getWorld().getName().split("_")[0])) {
            return;
        }
        if (global) {
            Player player = event.getPlayer();
            World from = event.getFrom();
            String path = player.getName() + "." + player.getWorld().getName();
            if (broadcastFirstJoin) {
                if (playerConfig.isSet(path) && !playerConfig.getBoolean(path)) {
                    Bukkit.broadcastMessage(formatGlobalMessage(firstJoinGlobalMsg, player.getName(), player.getWorld().getName(), from.getName()));
                    savePlayerToConfig(playerConfig, player, worldGrouping);
                    saveConfig(playerConfig, plugin);
                } else if (!playerConfig.isSet(path)) {
                    Bukkit.broadcastMessage(formatGlobalMessage(firstJoinGlobalMsg, player.getName(), player.getWorld().getName(), from.getName()));
                    savePlayerToConfig(playerConfig, player, worldGrouping);
                    saveConfig(playerConfig, plugin);
                } else {
                    Bukkit.broadcastMessage(formatGlobalMessage(globalMsg, player.getName(), player.getWorld().getName(), from.getName()));
                }
            } else {
                Bukkit.broadcastMessage(formatGlobalMessage(globalMsg, player.getName(), player.getWorld().getName(), from.getName()));
            }
        } else {
            Player player = event.getPlayer();
            World from = event.getFrom();
            String path = player.getName() + "." + player.getWorld().getName();
            Set<Player> oldfriends = groupWorlds(from, worldGrouping, plugin);
            String message = formatLeaveMessage(leaveMsg, player.getName(), from.getName());
            for (Player p : oldfriends) {
                p.sendMessage(message);
            }
            Set<Player> newfriends = groupWorlds(player.getWorld(), worldGrouping, plugin);
            if (broadcastFirstJoin) {
                if (playerConfig.isSet(path) && !playerConfig.getBoolean(path)) {
                    String msg = formatJoinMessage(firstJoinMsg, player.getName(), player.getWorld().getName());
                    for (Player pl : newfriends) {
                        pl.sendMessage(msg);
                    }
                    savePlayerToConfig(playerConfig, player, worldGrouping);
                    saveConfig(playerConfig, plugin);
                } else if (!playerConfig.isSet(path)) {
                    String msg = formatJoinMessage(firstJoinMsg, player.getName(), player.getWorld().getName());
                    for (Player pl : newfriends) {
                        pl.sendMessage(msg);
                    }
                    savePlayerToConfig(playerConfig, player, worldGrouping);
                    saveConfig(playerConfig, plugin);
                } else {
                    String msg = formatJoinMessage(joinMsg, player.getName(), player.getWorld().getName());
                    for (Player pl : newfriends) {
                        pl.sendMessage(msg);
                    }
                    savePlayerToConfig(playerConfig, player, worldGrouping);
                    saveConfig(playerConfig, plugin);
                }
            } else {
                String msg = formatJoinMessage(joinMsg, player.getName(), player.getWorld().getName());
                for (Player pl : newfriends) {
                    pl.sendMessage(msg);
                }
            }
        }
    }

    private String formatJoinMessage(String input, String player, String newWorld) {
        String formated = input;
        if (input.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (input.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", newWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }

    private String formatLeaveMessage(String input, String player, String oldWorld) {
        String formated = input;
        if (input.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (input.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", oldWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }

    private String formatGlobalMessage(String input, String player, String newWorld, String oldWorld) {
        String formated = input;
        if (input.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (input.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", newWorld);
        }
        if (input.contains("%WORLD2%")) {
            formated = formated.replace("%WORLD2%", oldWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }
}
