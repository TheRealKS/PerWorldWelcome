package com.koens.perworldwelcome.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class JoinQuitListener implements Listener {

    private boolean broadcast;
    private boolean queue;
    private String joinMsg;
    private String leaveMsg;
    private static final char AND = '&';

    public JoinQuitListener(boolean b, boolean q, String j, String l) {
        this.broadcast = b;
        this.queue = q;
        this.joinMsg = j;
        this.leaveMsg = l;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        List<Player> onlines = p.getWorld().getPlayers();
        for (Player pl : onlines) {
            pl.sendMessage(formatJoinMessage(p.getName(), p.getWorld().getName()));
        }
        if (broadcast && queue) {
            if (event.getPlayer().hasPermission("perworldwelcome.receiveerrors")) {
                event.getPlayer().sendMessage("There were some errors while loading the plugin! Please check to console to see these errors!");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        List<Player> onlines = p.getWorld().getPlayers();
        for (Player pl : onlines) {
            pl.sendMessage(formatLeaveMessage(p.getName(), p.getWorld().getName()));
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
    private String formatLeaveMessage(String player, String oldWorld) {
        String formated = leaveMsg;
        if (leaveMsg.contains("%PLAYER%")) {
            formated = formated.replace("%PLAYER%", player);
        }
        if (leaveMsg.contains("%WORLD%")) {
            formated = formated.replace("%WORLD%", oldWorld);
        }
        return ChatColor.translateAlternateColorCodes(AND, formated);
    }
}
