package site.dragonstudio.ads.spigot.listeners;

import site.dragonstudio.ads.spigot.ads.WelcomeAd;
import site.dragonstudio.ads.spigot.config.ConfigLoader;
import site.dragonstudio.ads.spigot.config.UpdateChecker;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final WelcomeAd welcomeAd;
    private final ConfigLoader configLoader;
    private final UpdateChecker updateChecker;

    public PlayerJoinListener(WelcomeAd welcomeAd, ConfigLoader configLoader, UpdateChecker updateChecker) {
        this.welcomeAd = welcomeAd;
        this.configLoader = configLoader;
        this.updateChecker = updateChecker;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (configLoader.getPluginConfig("Disable-MCJoinLeave-Messages")){
            event.setJoinMessage(null);
        }

        welcomeAd.sendAd(player);
    }
}