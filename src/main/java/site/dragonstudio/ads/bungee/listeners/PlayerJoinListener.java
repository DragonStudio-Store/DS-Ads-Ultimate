package site.dragonstudio.ads.bungee.listeners;

import site.dragonstudio.ads.bungee.ads.WelcomeAd;
import site.dragonstudio.ads.bungee.config.ConfigLoader;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinListener implements Listener {
    private static WelcomeAd welcomeAd;
    private static ConfigLoader configLoader;

    public PlayerJoinListener(WelcomeAd welcomeAd, ConfigLoader configLoader) {
        PlayerJoinListener.welcomeAd = welcomeAd;
        PlayerJoinListener.configLoader = configLoader;
    }

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        welcomeAd.sendAd(player);
    }
}