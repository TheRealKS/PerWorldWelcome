package com.koens.perworldwelcome.util;

import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
}
