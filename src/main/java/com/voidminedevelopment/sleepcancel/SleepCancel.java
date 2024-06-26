package com.voidminedevelopment.sleepcancel;

import com.voidminedevelopment.sleepcancel.Listeners.SleepListener;
import com.voidminedevelopment.sleepcancel.commands.WakeUp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SleepCancel extends JavaPlugin {
    public static final CopyOnWriteArrayList<Player> playersSleeping = new CopyOnWriteArrayList<>();
    public static final ConcurrentHashMap<UUID, Long> commandCooldowns = new ConcurrentHashMap<>();
    public static final long cooldownTimeMillis = 60000; // 5 seconds cooldown (adjust as needed)

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("wakeup").setExecutor(new WakeUp(this));
        getServer().getPluginManager().registerEvents(new SleepListener(), this);
        getLogger().info("SleepCancel - включён");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("SleepCancel - отключён");
    }

    public static void setPlayerSleepState(Player player) {
        playersSleeping.add(player);
        TextComponent playerSleepMessage = Component.text(player.getName(), NamedTextColor.AQUA).append(Component.text(" лёг спать", NamedTextColor.GOLD)).append(Component.text(" [CANCEL]", NamedTextColor.RED, TextDecoration.BOLD).clickEvent(ClickEvent.runCommand("/wakeup")));
        for (Player i : Bukkit.getOnlinePlayers()) {
            i.sendMessage(playerSleepMessage);
        }
    }

    public static void clearPlayerSleepState(Player player) {
        playersSleeping.remove(player);
    }

    public static boolean hasCooldown(UUID playerId) {
        return commandCooldowns.containsKey(playerId) &&
                System.currentTimeMillis() - commandCooldowns.get(playerId) < cooldownTimeMillis;
    }

    public static void updateCooldown(UUID playerId) {
        commandCooldowns.put(playerId, System.currentTimeMillis());
    }

    public static long getRemainingCooldown(UUID playerId) {
        return (cooldownTimeMillis - (System.currentTimeMillis() - commandCooldowns.get(playerId))) / 1000;
    }
}
