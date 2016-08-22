package com.koens.perworldwelcome.achievements;

import mkremins.fanciful.FancyMessage;
import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class AchievementMessageFactory {

    public static void createJSONTemplate(Player p) {
        FancyMessage message = new FancyMessage("Hey Hey Hey")
                .color(ChatColor.WHITE)
                .achievementTooltip(Achievement.ACQUIRE_IRON);
        message.send(p);

    }
}



