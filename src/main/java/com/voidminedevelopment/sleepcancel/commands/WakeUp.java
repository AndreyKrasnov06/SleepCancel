package com.voidminedevelopment.sleepcancel.commands;

import com.voidminedevelopment.sleepcancel.SleepCancel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WakeUp implements CommandExecutor {
    private final JavaPlugin plugin;

    public WakeUp(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (SleepCancel.playersSleeping.isEmpty()) {
                TextComponent playerNobodySleepingMessage = Component.text("Сейчас никто не спит", NamedTextColor.GOLD);
                sender.sendMessage(playerNobodySleepingMessage);
            } else {
                Player player = (Player) sender;
                TextComponent playerCancelMessage = Component.text(player.getName(), NamedTextColor.AQUA).append(Component.text(" не хочет пропускать ночь", NamedTextColor.GOLD));
                for (Player i : Bukkit.getOnlinePlayers()) {
                    i.sendMessage(playerCancelMessage);
                }
                for (Player i : SleepCancel.playersSleeping) {
                    Runnable task = () -> i.wakeup(true);
                    i.getScheduler().run(plugin, value -> task.run(), task);
                }
                SleepCancel.playersSleeping.clear();
            }
            return true;
        }
        return false;
    }
}
