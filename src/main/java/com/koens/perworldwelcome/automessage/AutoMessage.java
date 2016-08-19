package com.koens.perworldwelcome.automessage;


import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class AutoMessage implements Runnable {

    private PerWorldWelcome plugin;

    public AutoMessage(PerWorldWelcome p) {
        this.plugin = p;
    }

    public void run() {
        for (World w : Bukkit.getWorlds()) {
            if (plugin.getAutomessages().containsKey(w.getName())) {
        }
    }

}
