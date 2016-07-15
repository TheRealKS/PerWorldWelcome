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
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.List;

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

    private PerWorldWelcome plugin;

    private YamlConfiguration playerConfig;

    public WorldChangeListener(String a, String b, String c, String d, String e, boolean firstjoin, boolean wg, boolean g, YamlConfiguration yml, PerWorldWelcome welcome) {
        this.joinMsg = a;
        this.leaveMsg = b;
        this.globalMsg = c;
        this.firstJoinMsg = d;
        this.firstJoinGlobalMsg = e;
        this.global = g;
        this.broadcastFirstJoin = firstjoin;
        this.worldGrouping = wg;

        this.plugin = welcome;
        this.playerConfig = yml;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerWorldChange(PlayerChangedWorldEvent event) {
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
            List<Player> oldfriends = setupLeavingWorldList(from);
            String message = formatLeaveMessage(leaveMsg, player.getName(), from.getName());
            for (Player p : oldfriends) {
                p.sendMessage(message);
            }
            List<Player> newfriends = setupJoiningWorldList(player.getWorld());
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

    private List<Player> setupJoiningWorldList(World joining) {
        if (worldGrouping) {
            String worldname = joining.getName();
            if (joining.getEnvironment().equals(World.Environment.NETHER)) {
                List<Player> overworld = new ArrayList<Player>();
                List<Player> end = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname.replace("_nether", "")) != null) {
                    overworld = plugin.getServer().getWorld(worldname.replace("_nether", "")).getPlayers();
                }
                if (plugin.getServer().getWorld(worldname.replace("_nether", "_the_end")) != null) {
                    end = plugin.getServer().getWorld(worldname.replace("_nether", "_the_end")).getPlayers();
                }
                List<Player> current = joining.getPlayers();
                current.addAll(overworld);
                current.addAll(end);
                return current;
            } else if (joining.getEnvironment().equals(World.Environment.THE_END)) {
                List<Player> overworld = new ArrayList<Player>();
                List<Player> nether = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname.replace("_the_end", "")) != null) {
                    overworld = plugin.getServer().getWorld(worldname.replace("_the_end", "")).getPlayers();
                }
                if (plugin.getServer().getWorld(worldname.replace("_the_end", "_nether")) != null) {
                    nether = plugin.getServer().getWorld(worldname.replace("_the_end", "_nether")).getPlayers();
                }
                List<Player> current = joining.getPlayers();
                current.addAll(overworld);
                current.addAll(nether);
                return current;
            } else {
                List<Player> nether = new ArrayList<Player>();
                List<Player> end = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname + "_nether") != null) {
                    nether = plugin.getServer().getWorld(worldname + "_nether").getPlayers();
                }
                if (plugin.getServer().getWorld(worldname + "_the_end") != null) {
                    end = plugin.getServer().getWorld(worldname + "_the_end").getPlayers();
                }
                List<Player> current = joining.getPlayers();
                current.addAll(nether);
                current.addAll(end);
                return current;
            }
        } else {
            return joining.getPlayers();
        }
    }

    private List<Player> setupLeavingWorldList(World leaving) {
        if (worldGrouping) {
            String worldname = leaving.getName();
            if (leaving.getEnvironment().equals(World.Environment.NETHER)) {
                List<Player> overworld = new ArrayList<Player>();
                List<Player> end = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname.replace("_nether", "")) != null) {
                    overworld = plugin.getServer().getWorld(worldname.replace("_nether", "")).getPlayers();
                }
                if (plugin.getServer().getWorld(worldname.replace("_nether", "_the_end")) != null) {
                    end = plugin.getServer().getWorld(worldname.replace("_nether", "_the_end")).getPlayers();
                }
                List<Player> current = leaving.getPlayers();
                current.addAll(overworld);
                current.addAll(end);
                return current;
            } else if (leaving.getEnvironment().equals(World.Environment.THE_END)) {
                List<Player> overworld = new ArrayList<Player>();
                List<Player> nether = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname.replace("_the_end", "")) != null) {
                    overworld = plugin.getServer().getWorld(worldname.replace("_the_end", "")).getPlayers();
                }
                if (plugin.getServer().getWorld(worldname.replace("_the_end", "_nether")) != null) {
                    nether = plugin.getServer().getWorld(worldname.replace("_the_end", "_nether")).getPlayers();
                }
                List<Player> current = leaving.getPlayers();
                current.addAll(overworld);
                current.addAll(nether);
                return current;
            } else {
                List<Player> nether = new ArrayList<Player>();
                List<Player> end = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname + "_nether") != null) {
                    nether = plugin.getServer().getWorld(worldname + "_nether").getPlayers();
                }
                if (plugin.getServer().getWorld(worldname + "_the_end") != null) {
                    end = plugin.getServer().getWorld(worldname + "_the_end").getPlayers();
                }
                List<Player> current = leaving.getPlayers();
                current.addAll(nether);
                current.addAll(end);
                return current;
            }
        } else {
            return leaving.getPlayers();
        }

    }
}
