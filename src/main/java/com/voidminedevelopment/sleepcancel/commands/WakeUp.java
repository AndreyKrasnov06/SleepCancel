package com.voidminedevelopment.sleepcancel.commands;

import com.voidminedevelopment.sleepcancel.SleepCancel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WakeUp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent playerCancelMessage = Component.text(player.getName(), NamedTextColor.AQUA).append(Component.text(" Не хочет пропускать ночь", NamedTextColor.GOLD));
            for (Player i: Bukkit.getOnlinePlayers()) {
                i.sendMessage(playerCancelMessage);
            }
            for (Player i: SleepCancel.playersSleeping) {
                i.wakeup(true);
            }
            SleepCancel.playersSleeping.clear();
            return true;
        }
        return false;
    }
}
