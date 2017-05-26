package com.koens.perworldwelcome.automessage;


import com.koens.perworldwelcome.PerWorldWelcome;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AutoMessage {

    private PerWorldWelcome plugin;

    public AutoMessage(PerWorldWelcome p) {
        this.plugin = p;
    }

    public void run() {
        HashMap<String, List<String>> automessages = plugin.getAutomessages();
        plugin.getLogger().info("automessages");
        for (String s1 : automessages.keySet()) {
            World world = Bukkit.getWorld(UUID.fromString(s1));
            if (world != null) {
                List<String> msg = automessages.get(s1);
                int r = RandomUtils.nextInt(0, msg.size());
                String mssg = msg.get(r);
                for (Player p : world.getPlayers()) {
                    p.sendMessage(mssg);
                }
            }
        }
    }

}
