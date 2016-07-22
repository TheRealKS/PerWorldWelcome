package com.koens.perworldwelcome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CheckDelayCMD implements CommandExecutor{


    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        if (strings.length == 0) {
            String time = strings[0];
            if (time.contains(":")) {
                String[] parts = time.split(":");
                if (parts[0].contains("H")) {
                    hours = Integer.parseInt(parts[0].substring(0, parts[0].indexOf("H")));
                }
                if (parts[0].contains("M")) {
                    minutes = Integer.parseInt(parts[0].substring(0, parts[0].indexOf("M")));
                }
                if (parts[1].contains("M")) {
                    minutes = Integer.parseInt(parts[1].substring(0, parts[1].indexOf("M")));
                }
                if (parts[1].contains("S")) {
                    seconds = Integer.parseInt(parts[1].substring(0, parts[1].indexOf("S")));
                }
                if (parts[2].contains("S")) {
                    seconds = Integer.parseInt(parts[2].substring(0, parts[2].indexOf("S")));
                }
            } else {
                seconds = Integer.parseInt(time.substring(0, time.indexOf("S")));
            }
            commandSender.sendMessage(Integer.toString(hours) + ":" + Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
        return false;
    }
}
