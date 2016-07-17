package com.koens.perworldwelcome.util;

import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class WorldGroupingHelper {

    public static List<Player> setupWorldList(World world, boolean worldGrouping, PerWorldWelcome plugin) {
        if (worldGrouping) {
            String worldname = world.getName();
            if (world.getEnvironment().equals(World.Environment.NETHER)) {
                List<Player> overworld = new ArrayList<Player>();
                List<Player> end = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname.replace("_nether", "")) != null) {
                    overworld = plugin.getServer().getWorld(worldname.replace("_nether", "")).getPlayers();
                }
                if (plugin.getServer().getWorld(worldname.replace("_nether", "_the_end")) != null) {
                    end = plugin.getServer().getWorld(worldname.replace("_nether", "_the_end")).getPlayers();
                }
                List<Player> current = world.getPlayers();
                current.addAll(overworld);
                current.addAll(end);
                return current;
            } else if (world.getEnvironment().equals(World.Environment.THE_END)) {
                List<Player> overworld = new ArrayList<Player>();
                List<Player> nether = new ArrayList<Player>();
                if (plugin.getServer().getWorld(worldname.replace("_the_end", "")) != null) {
                    overworld = plugin.getServer().getWorld(worldname.replace("_the_end", "")).getPlayers();
                }
                if (plugin.getServer().getWorld(worldname.replace("_the_end", "_nether")) != null) {
                    nether = plugin.getServer().getWorld(worldname.replace("_the_end", "_nether")).getPlayers();
                }
                List<Player> current = world.getPlayers();
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
                List<Player> current = world.getPlayers();
                current.addAll(nether);
                current.addAll(end);
                return current;
            }
        } else {
            return world.getPlayers();
        }
    }
}
