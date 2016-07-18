package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.achievements.Achievements;
import com.koens.perworldwelcome.achievements.ExtraAchievements;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Achievement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import java.util.Map;

public class AwardAchievementListener implements Listener {

    Map<String, Boolean> booleanMap;
    Map<String, String> stringMap;

    public AwardAchievementListener(Map<String, Boolean> booleans, Map<String, String> strings) {
        this.booleanMap = booleans;
        this.stringMap = strings;
    }

    @EventHandler
    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
        String mssg = "hoi";
        mssg = formatMSSG(event.getPlayer().getName(), event.getAchievement());
    }

    private String formatMSSG(String playerName, Achievement achievement) {
        Achievements achievment = Achievements.valueOf(achievement.name());
        String name = achievment.getName();
        String description = achievment.getDescription();
        if (EnumUtils.isValidEnum(ExtraAchievements.class, "FLY_PIG")) {

        }
        return name;
    }
}
