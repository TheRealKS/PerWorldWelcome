package com.koens.perworldwelcome.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class WorldChangeListener implements Listener {

    private String joinMsg;
    private String leaveMsg;
    private String globalMsg;
    private static final char AND = '&';
    private boolean global;

    public WorldChangeListener(String a, String b, String c, boolean g) {
        this.joinMsg = a;
        this.leaveMsg = b;
        this.globalMsg = c;
        this.global = g;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
        if (global) {
            Player player = event.getPlayer();
            World from = event.getFrom();
            Bukkit.broadcastMessage (formatGlobalMessage(player.getName(), player.getWorld().getName(), from.getName()));
        }
        else {
            Player player = event.getPlayer();
            World from = event.getFrom();
            List<Player> oldfriends = from.getPlayers();
            for (Player p : oldfriends) {
                p.sendMessage(formateLeaveMessage(player.getName(), from.getName()));
            }
            List<Player> newfriends = player.getWorld().getPlayers();
            for (Player pl : newfriends) {
                pl.sendMessage(formatJoinMessage(player.getName(), player.getWorld().getName()));
            }
        }
    }

    private String formatJoinMessage(String player, String newWorld) {
        String formated = joinMsg;
        if (joinMsg.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (joinMsg.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", newWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }
    private String formateLeaveMessage(String player, String oldWorld) {
        String formated = leaveMsg;
        if (leaveMsg.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (leaveMsg.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", oldWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }
    private String formatGlobalMessage(String player, String newWorld, String oldWorld) {
        String formated = globalMsg;
        if (leaveMsg.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (leaveMsg.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", newWorld);
        }
        if (leaveMsg.contains("%WORLD2%")) {
            formated = formated.replace("%WORLD2%", oldWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }

}
