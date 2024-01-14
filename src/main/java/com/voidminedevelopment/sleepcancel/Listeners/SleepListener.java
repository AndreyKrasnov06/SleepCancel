package com.voidminedevelopment.sleepcancel.Listeners;

import com.voidminedevelopment.sleepcancel.SleepCancel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.UUID;

public class SleepListener implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            Player player = (Player) event.getPlayer();
            UUID playerId = player.getUniqueId();

            if (SleepCancel.hasCooldown(playerId)) {
                long remainingCooldown = SleepCancel.getRemainingCooldown(playerId);
                TextComponent playerRemainingCooldownMessage = Component.text("Вы не можете лечь спать ещё ", NamedTextColor.GOLD).append(Component.text(remainingCooldown, NamedTextColor.AQUA)).append(Component.text(" секунд", NamedTextColor.GOLD));
                player.sendMessage(playerRemainingCooldownMessage);
                event.setCancelled(true);
                return;
            }
            SleepCancel.setPlayerSleepState(event.getPlayer());
            SleepCancel.updateCooldown(playerId);
        }
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        SleepCancel.clearPlayerSleepState(event.getPlayer());
    }
}
