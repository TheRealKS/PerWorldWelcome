package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.PerWorldWelcome;
import com.koens.perworldwelcome.util.WorldGroupingHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class PlayerDeathListener implements Listener {

    private PerWorldWelcome plugin;
    private boolean worldgrouping;

    public PlayerDeathListener(PerWorldWelcome plugin, boolean wg) {
        this.plugin = plugin;
        this.worldgrouping = wg;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        String deathmessage = event.getDeathMessage();
        event.setDeathMessage("");
        Player player = event.getEntity();
        List<Player> players = WorldGroupingHelper.setupWorldList(player.getWorld(), worldgrouping, plugin);
        for (Player p : players) {
            p.sendMessage(deathmessage);
        }
    }


}
