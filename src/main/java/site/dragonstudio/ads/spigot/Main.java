package site.dragonstudio.ads.spigot;

import site.dragonstudio.ads.spigot.ads.Ads;
import site.dragonstudio.ads.spigot.ads.WelcomeAd;
import site.dragonstudio.ads.spigot.commands.AdsCommands;
import site.dragonstudio.ads.spigot.config.ConfigLoader;
import site.dragonstudio.ads.spigot.config.SplashX;
import site.dragonstudio.ads.spigot.config.UpdateChecker;
import site.dragonstudio.ads.spigot.config.VersionLoader;
import site.dragonstudio.ads.spigot.console.ConsoleManager;
import site.dragonstudio.ads.spigot.listeners.PlayerJoinListener;
import site.dragonstudio.ads.spigot.listeners.PlayerLeaveListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    ConsoleManager console = new ConsoleManager(this);
    ConfigLoader configLoader = new ConfigLoader(this, console);
    SplashX splashX = new SplashX(console, configLoader);
    VersionLoader versionLoader = new VersionLoader(this, configLoader, console);
    Ads ads = new Ads(this, configLoader, console);
    WelcomeAd welcomeAd = new WelcomeAd(this, configLoader, console);
    UpdateChecker updateChecker = new UpdateChecker(this, 113438);
    PlayerJoinListener playerJoinListener = new PlayerJoinListener(welcomeAd, configLoader, updateChecker);
    PlayerLeaveListener playerLeaveListener = new PlayerLeaveListener(configLoader);
    AdsCommands adsCommands = new AdsCommands(this, ads, configLoader);

    @Override
    public void onEnable() {
        long startMS = System.currentTimeMillis();

        ads.hideBossBar();
        welcomeAd.hideWelcomeBossBar();
        splashX.splash();
        versionLoader.versionTester();
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

        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(playerLeaveListener, this);

        getCommand("ads").setExecutor(adsCommands);
        getCommand("ads").setTabCompleter(adsCommands);

        long stopMS = System.currentTimeMillis();

        int milis = (int) (stopMS - startMS);
        console.logSuccessful("Plugin loaded on " + milis + "ms");
    }

    @Override
    public void onDisable() {
        // Lógica de desactivación del plugin
    }
}