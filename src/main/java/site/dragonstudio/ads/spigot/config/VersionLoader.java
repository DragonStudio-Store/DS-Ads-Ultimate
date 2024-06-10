package site.dragonstudio.ads.spigot.config;

import site.dragonstudio.ads.spigot.Main;
import site.dragonstudio.ads.spigot.console.ConsoleManager;

import java.io.File;

public class VersionLoader {
    private static final String pluginVersion = "6.0";

    private final Main main;
    private final ConfigLoader configLoader;
    private final ConsoleManager console;

    public VersionLoader(Main main, ConfigLoader configLoader, ConsoleManager console){
        this.main = main;
        this.configLoader = configLoader;
        this.console = console;
    }

    public void versionTester() {
        String settingsVersion = configLoader.getVersion();

        File oldConfigDir = new File(main.getDataFolder(), "OldConfig");
        if (!oldConfigDir.exists()) {
            oldConfigDir.mkdirs();
        }

        if (!pluginVersion.equals(settingsVersion)) {
            console.logError("---------- Outdated Version ----------");
            console.resetLog("");
            console.logError("Old or null plugin version detected:");
            console.resetLog("");
            console.logError("Error Code:");
            console.logError("Version different from the current one detected");
            console.resetLog("");
            console.logError("---------------------------------");
            console.resetLog("");

            configLoader.handleConfigError(oldConfigDir);
        }
    }
}