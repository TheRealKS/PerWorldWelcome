package com.koens.perworldwelcome;

import com.koens.perworldwelcome.automessage.AutoMessage;
import com.koens.perworldwelcome.commands.InfoCMD;
import com.koens.perworldwelcome.commands.ToggleCMD;
import com.koens.perworldwelcome.listeners.JoinQuitListener;
import com.koens.perworldwelcome.listeners.WorldChangeListener;
import com.koens.perworldwelcome.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerWorldWelcome extends JavaPlugin {

    private boolean globalBroadcast;
    private boolean errorBroadcast;
    private boolean firstJoinServerMsg;
    private boolean firstJoinWorldMsg;
    private boolean worldGrouping;
    private boolean errorqueue;
    private String joinMsg;
    private String leaveMsg;
    private String firstJoinMsg;
    private String joinServerMsg;
    private String leaveServerMsg;
    private String firstJoinSrvrMsg;
    private String globalMsg;
    private String globalFirstJoinWorldMsg;
    private String globalFirstJoinServerMsg;

    private YamlConfiguration playerConfig;
    private YamlConfiguration automessagesconfig;

    private int automessagestaskid;

    private boolean Enabled = true;

    private HashMap<String, List<String>> automessages;

    @Override
    public void onEnable() {
        getLogger().info("Loading config files...");
        loadConfig();
        try {
            setupPlayerConfig();
        } catch (IOException e) {
            e.printStackTrace();
            getServer().getLogger().info("Couldn't setup the player config file! This plugin will not work without it, so it will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
        }
        try {
            setupMessageConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLogger().info("Config files loaded!");
        WorldChangeListener listener = new WorldChangeListener(joinMsg, leaveMsg, globalMsg, firstJoinMsg, globalFirstJoinWorldMsg, firstJoinWorldMsg, worldGrouping, globalBroadcast, playerConfig, this);
        JoinQuitListener listener1 = new JoinQuitListener(errorBroadcast, errorqueue, worldGrouping, firstJoinServerMsg, globalBroadcast, joinServerMsg, leaveServerMsg, firstJoinSrvrMsg, globalFirstJoinServerMsg, this, playerConfig);
        getServer().getPluginManager().registerEvents(listener, this);
        getServer().getPluginManager().registerEvents(listener1, this);
        try {
            if (getAnnouncheAchievements()) {
                if (getConfig().getBoolean("force-achievement-messages-enabled")) {
                    getLogger().warning("anounce-player-achievements is enabled in server.properties! Force enable is enabled.");
                } else {
                    getLogger().warning("anounce-player-achievements is enabled in server.properties! This interferes with PerWorldWelcome, so please set this value to false in server.properties to use this functionality!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getCommand("pwwtoggle").setExecutor(new ToggleCMD(this));
        getCommand("pwwinfo").setExecutor(new InfoCMD(getDescription().getVersion()));
        automessagestaskid = setupAutoMessages();
        setupMetrics();
        getLogger().info("PerWorldWelcome v." + getDescription().getVersion() + " has been enabled! All credits go to TheRealKS123");
    }
    @Override
    public void onDisable() {
        getLogger().info("PerWorldWelcome v." + getDescription().getVersion() + " has been disabled! All credits go to TheRealKS123");
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
        if (getConfig().isSet("first-join-server-message") && getConfig().isBoolean("first-join-server-message")) {
            firstJoinServerMsg = getConfig().getBoolean("first-join-server-message");
        }
        else {
            getLogger().warning("The formatting of an item in config.yml is invalid! Item: first-join-server-messsage");
            firstJoinServerMsg = true;
            errorqueue = true;
        }
        if (getConfig().isSet("first-join-world-message") && getConfig().isBoolean("first-join-world-message")) {
            firstJoinWorldMsg = getConfig().getBoolean("first-join-world-message");
        }
        else {
            getLogger().warning("The formatting of an item in config.yml is invalid! Item: first-join-world-messsage");
            firstJoinWorldMsg = true;
            errorqueue = true;
        }
        if (getConfig().isSet("world-grouping") && getConfig().isBoolean("world-grouping")) {
            worldGrouping = getConfig().getBoolean("world-grouping");
        }
        else {
            getLogger().warning("The formatting of an item in config.yml is invalid! Item: world-grouping");
            worldGrouping = true;
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
        if (getConfig().isSet("join-server-message") && getConfig().isString("join-server-message")) {
            joinServerMsg = getConfig().getString("join-server-message");
        }
        else {
            getLogger().warning("No server join message was set! Using default value!");
            joinServerMsg = "&9%PLAYER% joined the game!";
            errorqueue = true;
        }
        if (getConfig().isSet("leave-server-message") && getConfig().isString("join-server-message")) {
            leaveServerMsg = getConfig().getString("leave-server-message");
        }
        else {
            getLogger().warning("No server leave message was set! Using default value!");
            joinServerMsg = "&9%PLAYER% left the game!";
            errorqueue = true;
        }
        if (getConfig().isSet("first-join-message") && getConfig().isString("first-join-message")) {
            firstJoinMsg = getConfig().getString("first-join-message");
        }
        else {
            getLogger().warning("No first join message was set! Using default value!");
            firstJoinMsg = "&9%PLAYER% joined this world for the first time!";
            errorqueue = true;
        }
        if (getConfig().isSet("first-server-join-message") && getConfig().isString("first-server-join-message")) {
            firstJoinSrvrMsg = getConfig().getString("first-server-join-message");
        }
        else {
            getLogger().warning("No first join server message was set! Using default value!");
            joinServerMsg = "&9%PLAYER% joined the server for the first time!";
            errorqueue = true;
        }
        if (getConfig().isSet("global-first-join-message") && getConfig().isString("global-first-join-message")) {
            globalFirstJoinWorldMsg = getConfig().getString("global-first-join-message");
        }
        else {
            getLogger().warning("No global first join message was set! Using default value!");
            joinServerMsg = "&9%PLAYER% joined %WORLD% for the first time!";
            errorqueue = true;
        }
        if (getConfig().isSet("global-first-join-server-message") && getConfig().isString("global-first-join-server-message")) {
            globalFirstJoinServerMsg = getConfig().getString("global-first-join-server-message");
        }
        else {
            getLogger().warning("No global first join server message was set! Using default value!");
            joinServerMsg = "&9Welcome %PLAYER% to the server!";
            errorqueue = true;
        }
    }

    private void setupPlayerConfig() throws IOException {
        File file = new File(getDataFolder(), "players");
        if (!file.exists()) {
            file.mkdir();
        }
        File yml = new File(file, "players.yml");
        playerConfig = YamlConfiguration.loadConfiguration(yml);
        if (!yml.exists()) {
            playerConfig.save(yml);
        }
        playerConfig = YamlConfiguration.loadConfiguration(yml);
    }
    private void setupMessageConfig() throws Exception {
        saveResource("automessages.yml", false);
    }

    public boolean getEnabled() {
        return Enabled;
    }
    public void setFunctionsEnabled(boolean which) {
        this.Enabled = which;
        if (which) {
            loadConfig();
            try {
                setupPlayerConfig();
                setupAutoMessagesMap();
            } catch (IOException e) {
                e.printStackTrace();
                getServer().getLogger().info("Couldn't setup the player config file! This plugin will not work without it, so it will be disabled!");
                getServer().getPluginManager().disablePlugin(this);
            }
            setupAutoMessages();
            WorldChangeListener listener = new WorldChangeListener(joinMsg, leaveMsg, globalMsg, firstJoinMsg, globalFirstJoinWorldMsg, firstJoinWorldMsg, worldGrouping, globalBroadcast, playerConfig, this);
            JoinQuitListener listener1 = new JoinQuitListener(errorBroadcast, errorqueue, worldGrouping, firstJoinServerMsg, globalBroadcast, joinServerMsg, leaveServerMsg, firstJoinSrvrMsg, globalFirstJoinServerMsg, this, playerConfig);
            getServer().getPluginManager().registerEvents(listener, this);
            getServer().getPluginManager().registerEvents(listener1, this);
        } else {
            PlayerChangedWorldEvent.getHandlerList().unregister(this);
            PlayerJoinEvent.getHandlerList().unregister(this);
            PlayerQuitEvent.getHandlerList().unregister(this);
            getServer().getScheduler().cancelTask(automessagestaskid);
        }
    }

    private boolean getAnnouncheAchievements() throws IOException {
        String file = Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        file = file.substring(0, file.lastIndexOf("/"));
        file = file.replaceAll("%20", " ");
        File serverpropertiesfile = new File(file, "server.properties");
        Path serverpropertiespath = serverpropertiesfile.toPath();
        List<String> properties = Files.readAllLines(serverpropertiespath);
        for (String a : properties) {
            if (a.startsWith("announce-player-achievements")) {
                String value = a.substring(a.indexOf("=") + 1);
                return Boolean.valueOf(value);
            }
        }
        return false;
    }

    private void setupAutoMessagesMap() throws IOException {
        automessages = new HashMap<>();
        File file = new File(getDataFolder(), "automessages.yml");
        automessagesconfig = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            automessagesconfig.save(file);
        }
        automessagesconfig = YamlConfiguration.loadConfiguration(file);
        for (World world : getServer().getWorlds()) {
            if (automessagesconfig.isSet(world.getName())) {
                if (!automessagesconfig.getBoolean(world.getName() + ".enabled"))
                    continue;
                List<String> messages = new ArrayList<>();
                for (String s1 : automessagesconfig.getStringList(world.getName() + ".mesages")) {
                    if (!s1.startsWith("<EXCLUDE>")) {
                        messages.add(ChatColor.translateAlternateColorCodes('&', s1));
                    }
                }
                automessages.put(world.getUID().toString(), messages);
            }
        }
    }

    private int setupAutoMessages() {
        long delay = getConfig().getInt("auto-message-delay") * 20L;
        BukkitTask task = getServer().getScheduler().runTaskTimerAsynchronously(this, new AutoMessage(this), delay, delay);
        return task.getTaskId();
    }

    private void setupMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("Couldn't enable metrics!");
            getLogger().warning("Detailed error information: " + e.getMessage());
        }
    }

    public boolean getWorldGrouping() {
        return worldGrouping;
    }

    public HashMap<String, List<String>> getAutomessages() {
        return automessages;
    }

}
