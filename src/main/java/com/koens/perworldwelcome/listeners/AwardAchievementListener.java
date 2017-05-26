package com.koens.perworldwelcome.listeners;

import com.koens.perworldwelcome.PerWorldWelcome;
import com.koens.perworldwelcome.achievements.Achievements;
import com.koens.perworldwelcome.achievements.ExtraAchievements;
import com.koens.perworldwelcome.util.WorldGroupingHelper;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

public class AwardAchievementListener implements Listener {

    private PerWorldWelcome plugin;

    public AwardAchievementListener(PerWorldWelcome p) {
        this.plugin = p;
    }

    @EventHandler
    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
        String mssg = "%PLAYER% just earned the achievment: %ACHIEVEMENT%";
        mssg = formatMSSG(event.getPlayer().getName(), mssg, event.getAchievement());
        WorldGroupingHelper.setupWorldList(event.getPlayer().getWorld(), plugin.getWorldGrouping(), plugin);
    }

    private String formatMSSG(String playerName, String message, Achievement achievement) {
        Achievements achievment = Achievements.valueOf(achievement.name());
        String name = achievment.getName();
        String description = achievment.getClientequivalent();
        message = message.replace("%PLAYER", playerName);
        if (EnumUtils.isValidEnum(ExtraAchievements.class, achievement.name())) {
            return message.replace("%ACHIEVEMENT%", ChatColor.DARK_PURPLE + "[" + name + "]" + ChatColor.RESET);
        }
        return message.replace("%ACHIEVEMENT%", ChatColor.GREEN + "[" + name + "]" + ChatColor.RESET);
    }
}
