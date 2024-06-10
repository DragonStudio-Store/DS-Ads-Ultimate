package site.dragonstudio.ads.spigot.listeners;

import site.dragonstudio.ads.spigot.config.ConfigLoader;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
    private final ConfigLoader configLoader;

    public PlayerLeaveListener(ConfigLoader configLoader){
        this.configLoader = configLoader;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(configLoader.getPluginConfig("Disable-MCJoinLeave-Messages")){
            event.setQuitMessage(null);
        }
    }
}