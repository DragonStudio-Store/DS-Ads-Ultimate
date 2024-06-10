package site.dragonstudio.ads.bungee.config;

import site.dragonstudio.ads.bungee.config.ConfigLoader;
import site.dragonstudio.ads.bungee.console.ConsoleManager;

public class SplashX {
    private static ConsoleManager console;
    private static ConfigLoader configLoader;

    public SplashX(ConsoleManager console, ConfigLoader configLoader){
        SplashX.console = console;
        SplashX.configLoader = configLoader;
    }

    public void splash(){
        String PluginVersion = configLoader.getVersion();

        console.resetLog("");
        console.logInfo("  ▄▄▄▄▄▄  ▄▄▄▄▄▄▄       ▄▄▄▄▄▄▄ ▄▄▄▄▄▄  ▄▄▄▄▄▄▄ ");
        console.logInfo(" █      ██       █     █       █      ██       █");
        console.logInfo(" █  ▄    █  ▄▄▄▄▄█     █   ▄   █  ▄    █  ▄▄▄▄▄█");
        console.logInfo(" █ █ █   █ █▄▄▄▄▄      █  █▄█  █ █ █   █ █▄▄▄▄▄ ");
        console.logInfo(" █ █▄█   █▄▄▄▄▄  █     █       █ █▄█   █▄▄▄▄▄  █");
        console.logInfo(" █       █▄▄▄▄▄█ █     █   ▄   █       █▄▄▄▄▄█ █");
        console.logInfo(" █▄▄▄▄▄▄██▄▄▄▄▄▄▄█     █▄▄█ █▄▄█▄▄▄▄▄▄██▄▄▄▄▄▄▄█");
        console.logInfo(" ");
        console.resetLog("");
        console.logSuccessful("Created in DragonStudio - Plugin Development");
        console.logSuccessful("Developer: ZxyretrohackyxZ");
        console.logSuccessful("Docs: https://wiki.dragonstudio.site");
        console.logSuccessful("Discord: https://discord.dragonstudio.site");
        console.logSuccessful("Contributors: DragonStudio");
        console.logWarning("Plugin Versión: " + PluginVersion);
        console.resetLog("");
    }
}