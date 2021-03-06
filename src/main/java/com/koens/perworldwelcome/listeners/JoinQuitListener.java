package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.PerWorldWelcome;
import com.koens.perworldwelcome.util.ConfigUser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.koens.perworldwelcome.util.WorldGrouping.groupWorlds;

public class JoinQuitListener extends ConfigUser implements Listener {

    private boolean broadcast;
    private boolean queue;
    private boolean worldGrouping;
    private boolean firstJoinMessage;
    private boolean global;
    private String firstJoinMsg;
    private String firstJoinGlobalMsg;
    private String joinMsg;
    private String leaveMsg;
    private static final char AND = '&';

    private PerWorldWelcome plugin;
    private YamlConfiguration playerConfig;

    public JoinQuitListener(boolean b, boolean q, boolean wg, boolean fjm, boolean fjgm, String j, String l, String fj, String gfj, PerWorldWelcome pww, YamlConfiguration yml) {
        this.broadcast = b;
        this.queue = q;
        this.worldGrouping = wg;
        this.firstJoinMessage = fjm;
        this.global = fjgm;
        this.joinMsg = j;
        this.leaveMsg = l;
        this.firstJoinMsg = fj;
        this.firstJoinGlobalMsg = gfj;
        this.plugin = pww;
        this.playerConfig = yml;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        Set<Player> onlines = setupJoiningWorldList(p.getWorld());
        if (firstJoinMessage) {
            if (global) {
                if (!p.hasPlayedBefore()) {
                    String msg = formatJoinMessage(firstJoinGlobalMsg, p.getName(), p.getWorld().getName());
                    Bukkit.broadcastMessage(msg);
                    playerConfig.set(p.getName(), true);
                    saveConfig(playerConfig, plugin);
                }
            } else {
                if (!p.hasPlayedBefore()) {
                    String msg = formatJoinMessage(firstJoinMsg, p.getName(), p.getWorld().getName());
                    for (Player pl : onlines) {
                        pl.sendMessage(msg);
                    }
                } else {
                    String msg = formatJoinMessage(joinMsg, p.getName(), p.getWorld().getName());
                    for (Player pl : onlines) {
                        pl.sendMessage(msg);
                    }
                }
            }
        }
        else {
            String msg = formatJoinMessage(joinMsg, p.getName(), p.getWorld().getName());
            for (Player pl : onlines) {
                pl.sendMessage(msg);
            }
        }
        if (broadcast && queue) {
            if (p.hasPermission("perworldwelcome.receiveerrors")) {
                p.sendMessage("There were some errors while loading the plugin! Please check to console to see these errors!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        Set<Player> onlines = setupLeavingWorldList(p.getWorld());
        String msg = formatLeaveMessage(leaveMsg, p.getName(), p.getWorld().getName());
        for (Player pl : onlines) {
            pl.sendMessage(msg);
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

    private Set<Player> setupJoiningWorldList(World joining) {
        return groupWorlds(joining, worldGrouping, plugin);
    }

    private Set<Player> setupLeavingWorldList(World leaving) {
        return groupWorlds(leaving, worldGrouping, plugin);
    }
}
