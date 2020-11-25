package com.koens.perworldwelcome.util;

import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldGrouping {
    public static Set<Player> groupWorlds(World world, boolean worldGrouping, PerWorldWelcome plugin) {
        Set<Player> players = new HashSet<Player>(world.getPlayers());
        if (worldGrouping) {
            String worldname = world.getName().split("_")[0];
            if (plugin.getServer().getWorld(worldname + "_nether") != null) {
                players.addAll(plugin.getServer().getWorld(worldname + "_nether").getPlayers());
            }
            if (plugin.getServer().getWorld(worldname + "_the_end") != null) {
                players.addAll(plugin.getServer().getWorld(worldname + "_the_end").getPlayers());
            }
        }
        return players;
    }
}
