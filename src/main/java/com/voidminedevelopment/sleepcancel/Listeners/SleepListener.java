package com.voidminedevelopment.sleepcancel.Listeners;

import com.voidminedevelopment.sleepcancel.SleepCancel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SleepListener implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            SleepCancel.setPlayerSleepState(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        SleepCancel.clearPlayerSleepState(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (SleepCancel.checkPlayerSleepState(event.getPlayer())) {
            SleepCancel.clearPlayerSleepState(event.getPlayer());
        }
    }
}
