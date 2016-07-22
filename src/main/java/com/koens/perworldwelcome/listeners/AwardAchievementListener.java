package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.achievements.Achievements;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import java.util.Map;

public class AwardAchievementListener implements Listener {

    Map<String, Boolean> booleanMap;
    Map<String, String> stringMap;
    /*
    public AwardAchievementListener(Map<String, Boolean> booleans, Map<String, String> strings) {
        this.booleanMap = booleans;
        this.stringMap = strings;
    }
    */
    @EventHandler
    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
        String mssg = "%PLAYER% just earned the achievment: %ACHIEVEMENT%";
        mssg = formatMSSG(event.getPlayer().getName(), mssg, event.getAchievement());
        event.getPlayer().sendMessage(mssg);
    }

    private String formatMSSG(String playerName, String message, Achievement achievement) {
        Achievements achievment = Achievements.valueOf(achievement.name());
        String name = achievment.getName();
        String description = achievment.getDescription();
        message = message.replace("%PLAYER", playerName);
        return message.replace("%ACHIEVEMENT%", ChatColor.GREEN + "[" + ChatColor.RESET + name + ChatColor.GREEN + "]" + ChatColor.RESET);
    }
}
