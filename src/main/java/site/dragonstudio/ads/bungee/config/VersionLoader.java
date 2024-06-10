package site.dragonstudio.ads.bungee.config;

import site.dragonstudio.ads.bungee.Main;
import site.dragonstudio.ads.bungee.console.ConsoleManager;

import java.io.File;

public class VersionLoader {
    private static final String pluginVersion = "6.0";

    private static Main main;
    private static ConfigLoader configLoader;
    private static ConsoleManager console;

    public VersionLoader(Main main, ConfigLoader configLoader, ConsoleManager console){
        VersionLoader.main = main;
        VersionLoader.configLoader = configLoader;
        VersionLoader.console = console;
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