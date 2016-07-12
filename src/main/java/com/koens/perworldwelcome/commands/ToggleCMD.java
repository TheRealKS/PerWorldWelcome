package com.koens.perworldwelcome.commands;

import com.koens.perworldwelcome.PerWorldWelcome;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ToggleCMD implements CommandExecutor {

    private PerWorldWelcome plugin;

    public ToggleCMD(PerWorldWelcome p) {
        this.plugin = p;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.hasPermission("perworldwelcome.toggle")) {
                if (plugin.getEnabled()) {
                    plugin.setFunctionsEnabled(false);
                    p.sendMessage(ChatColor.RED + "The plugin's functionality has been disabled!");
                } else {
                    plugin.setFunctionsEnabled(true);
                    p.sendMessage(ChatColor.GREEN + "The plugin's functionality has been enabled!");
                }
            } else {
                p.sendMessage(ChatColor.RED + "You cannot run this command because you lack the permission: perworldwelcome.toggle!");
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            if (plugin.getEnabled()) {
                plugin.setFunctionsEnabled(false);
                commandSender.sendMessage("The plugin's functionality has been disabled!");
            } else {
                plugin.setFunctionsEnabled(true);
                commandSender.sendMessage("The plugin's functionality has been enabled!");
            }
        }
        else {
            commandSender.sendMessage("This command can only be run by players or the console!");
        }
        return true;
    }
}
