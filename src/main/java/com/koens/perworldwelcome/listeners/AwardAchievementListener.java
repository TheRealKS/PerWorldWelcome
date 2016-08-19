package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.achievements.Achievements;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class AwardAchievementListener implements Listener {

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
