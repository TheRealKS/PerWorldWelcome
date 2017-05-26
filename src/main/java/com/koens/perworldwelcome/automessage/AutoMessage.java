package com.koens.perworldwelcome.automessage;


import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class AutoMessage {

    private PerWorldWelcome plugin;

    public AutoMessage(PerWorldWelcome p) {
        this.plugin = p;
    }

    public void run() {
        HashMap<String, List<String>> automessages = plugin.getAutomessages();
        for (String s1 : automessages.keySet()) {
            World world = Bukkit.getWorld(s1);
            if (world != null) {
                for (Player p : world.getPlayers()) {
                    for (String s2 : automessages.get(s1)) {
                        p.sendMessage(s2);
                    }
                }
            }
        }
    }

}
