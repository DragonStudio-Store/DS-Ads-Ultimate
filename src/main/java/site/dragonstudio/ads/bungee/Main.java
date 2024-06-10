package site.dragonstudio.ads.bungee;

import site.dragonstudio.ads.bungee.ads.Ads;
import site.dragonstudio.ads.bungee.ads.WelcomeAd;
import site.dragonstudio.ads.bungee.commands.AdsCommand;
import site.dragonstudio.ads.bungee.console.ConsoleManager;
import site.dragonstudio.ads.bungee.config.ConfigLoader;
import site.dragonstudio.ads.bungee.config.SplashX;
import site.dragonstudio.ads.bungee.config.UpdateChecker;
import site.dragonstudio.ads.bungee.config.VersionLoader;
import site.dragonstudio.ads.bungee.listeners.PlayerJoinListener;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin implements Listener {

    private final ConsoleManager console = new ConsoleManager(this);
    private final ConfigLoader configLoader = new ConfigLoader(this, console);
    private final UpdateChecker updateChecker = new UpdateChecker(this, 113438);
    private final SplashX splashX = new SplashX(console, configLoader);
    private final VersionLoader versionLoader = new VersionLoader(this, configLoader, console);
    private final Ads ads = new Ads(this, configLoader, console);
    private final WelcomeAd welcomeAd = new WelcomeAd(this, configLoader, console);
    private final PlayerJoinListener playerJoinListener = new PlayerJoinListener(welcomeAd, configLoader);
    private final AdsCommand adsCommand = new AdsCommand(this, configLoader);

    public Main() {}

    public void onEnable() {
        long startMS = System.currentTimeMillis();

        splashX.splash();
        ads.adsSender();

        updateChecker.getVersion(version -> {
            if (getDescription().getVersion().equalsIgnoreCase(version)) {
                console.logInfo("¡Estás ejecutando la última versión del plugin!");
            } else {
                console.logVoid("");
                console.logInfo("¡Hay una nueva versión de DS-Ads disponible! Visita:");
                console.spigot("https://www.spigotmc.org/resources/ds-ads-ultimate-%E2%8F%B3-actionbar-bossbar-chat-title-ads-%E2%8F%B3.113438/");
                console.polymart("https://polymart.org/resource/ds-ads-ultimate.5029");
                console.modrinth("https://modrinth.com/plugin/ds-ads");
                console.logVoid("");
            }
        });

        getProxy().getPluginManager().registerListener(this, playerJoinListener);
        getProxy().getPluginManager().registerCommand(this, adsCommand);

        long stopMS = System.currentTimeMillis();
        int milis = (int) (stopMS - startMS);
        console.logSuccessful("Plugin loaded on " + milis + "ms");
    }

    public void onDisable() {
    }
}