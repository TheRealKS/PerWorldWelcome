package com.koens.perworldwelcome;

import com.koens.perworldwelcome.listeners.WorldChangeListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PerWorldWelcome extends JavaPlugin implements Listener {

    private boolean globalBroadcast;
    private boolean errorBroadcast;
    private boolean errorqueue;
    private String joinMsg;
    private String leaveMsg;
    private String globalMsg;

    @Override
    public void onEnable() {
        loadConfig();
        WorldChangeListener listener = new WorldChangeListener(joinMsg, leaveMsg, globalMsg, globalBroadcast);
        getServer().getPluginManager().registerEvents(listener, this);
        getLogger().info("PerWorldWelcome v." + getDescription().getVersion() + " has been enabled! All credits go to TheRealKS123");
    }
    @Override
    public void onDisable() {
        getLogger().info("PerWorldWelcome v." + getDescription().getVersion() + " has been disbled! All credits go to TheRealKS123");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (errorBroadcast) {
            if (event.getPlayer().hasPermission("perworldwelcome.receiveerrors")) {
                event.getPlayer().sendMessage("There were some errors while loading the plugin! Please check to console to see these errors!");
            }
        }
    }

    private void loadConfig() {
        this.saveDefaultConfig();
        if (getConfig().isSet("broadcast-globally") && getConfig().isBoolean("broadcast-globally")) {
            globalBroadcast = getConfig().getBoolean("broadcast-globally");
        }
        else {
            getLogger().warning("The formatting of an item in config.yml is invalid! Item: broadcast-globally");
            globalBroadcast = false;
            errorqueue = true;
        }
        if (getConfig().isSet("error-broadcast") && getConfig().isBoolean("broadcast-globally")) {
            errorBroadcast = getConfig().getBoolean("broadcast-globally");
        }
        else {
            getLogger().warning("The formatting of an item in config.yml is invalid! Item: error-broadcast");
            errorBroadcast = true;
            errorqueue = true;
        }
        if (getConfig().isSet("join-message") && getConfig().isString("join-message")) {
            joinMsg = getConfig().getString("join-message");
        }
        else {
            getLogger().warning("No join message was set! Using default value!");
            joinMsg = "&9%PLAYER% joined this world!";
            errorqueue = true;
        }
        if (getConfig().isSet("leave-message") && getConfig().isString("leave-message")) {
            leaveMsg = getConfig().getString("leave-message");
        }
        else {
            getLogger().warning("No leave message was set! Using default value!");
            leaveMsg = "&9%PLAYER% left this world!";
            errorqueue = true;
        }
        if (getConfig().isSet("global-message") && getConfig().isString("global-message")) {
            globalMsg = getConfig().getString("global-message");
        }
        else {
            getLogger().warning("No global message was set! Using default value!");
            leaveMsg = "&9%PLAYER% joined %WORLD%, leaving %WORLD2%!";
            errorqueue = true;
        }
    }
}
