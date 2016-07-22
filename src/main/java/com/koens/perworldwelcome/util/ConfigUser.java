package com.koens.perworldwelcome.util;

import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigUser {

    protected void saveConfig(YamlConfiguration yml, PerWorldWelcome plugin) {
        try {
            File file = new File(plugin.getDataFolder(), "players");
            File path = new File(file, "players.yml");
            yml.save(path);
        } catch (IOException e) {
            e.printStackTrace();
            plugin.getLogger().warning("Something went wrong while saving the player config file!");
        }
    }

    protected void savePlayerToConfig(YamlConfiguration yml, Player target, boolean worldgrouping) {
        List<String> paths = new ArrayList<String>();
        String worldname = target.getWorld().getName();
        if (worldgrouping) {
            World world = target.getWorld();
            if (world.getEnvironment().equals(World.Environment.NETHER)) {
                paths.add(worldname.replace("_nether", ""));
                paths.add(worldname);
                paths.add(worldname.replace("nether", "the_end"));
            } else if (world.getEnvironment().equals(World.Environment.THE_END)) {
                paths.add(worldname.replace("_the_end", ""));
                paths.add(worldname.replace("the_end", "nether"));
                paths.add(worldname);
            } else {
                paths.add(worldname);
                paths.add(worldname + "_nether");
                paths.add(worldname + "_the_end");
            }
        } else {
            paths.add(worldname);
        }
        if (!yml.isConfigurationSection(target.getName())) {
            yml.createSection(target.getName());
        }
        for (String s : paths) {
            yml.set(target.getName() + "." + s, true);
        }
    }

}
