package com.koens.perworldwelcome.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoCMD implements CommandExecutor {

    private String version;

    public InfoCMD(String v) {
        this.version = v;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(ChatColor.BLUE + "===PerWorldWelcome info:===");
        commandSender.sendMessage(ChatColor.DARK_BLUE + "Version: " + version);
        commandSender.sendMessage(ChatColor.DARK_BLUE + "Author: TheRealKS, 2016");
        commandSender.sendMessage(ChatColor.BLUE + "===========================");
        return true;
    }
}
