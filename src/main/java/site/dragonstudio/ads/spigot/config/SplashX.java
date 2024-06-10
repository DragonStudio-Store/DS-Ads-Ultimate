package site.dragonstudio.ads.spigot.config;

import site.dragonstudio.ads.spigot.console.ConsoleManager;

public class SplashX {
    private final ConsoleManager console;
    private final ConfigLoader configLoader;

    public SplashX(ConsoleManager console, ConfigLoader configLoader){
        this.console = console;
        this.configLoader = configLoader;
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