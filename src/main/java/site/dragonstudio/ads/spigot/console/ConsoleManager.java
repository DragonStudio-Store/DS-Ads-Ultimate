package site.dragonstudio.ads.spigot.console;

import site.dragonstudio.ads.spigot.Main;

public class ConsoleManager {
    private static final String CRED = "\u001B[31m"; // Rojo
    private static final String CCYAN = "\u001B[36m"; // Aqua
    private static final String CGREEN = "\u001B[92m"; // Verde
    private static final String CYELLOW = "\u001B[33m"; // Amarillo
    private static final String CDARKGREEN = "\u001B[32m"; // Verde oscuro
    private static final String CWHITE = "\u001B[37m"; // Blanco
    private static final String CRESET = "\u001B[0m"; // Reset

    private final Main main;

    public ConsoleManager(Main main) {
        this.main = main;
    }

    public void logError(String Log){
        main.getLogger().info(CRED + Log + CRESET);
    }

    public void logInfo(String Log){
        main.getLogger().info(CCYAN + Log + CRESET);
    }

    public void logSuccessful(String Log){
        main.getLogger().info(CGREEN + Log + CRESET);
    }

    public void logWarning(String Log){
        main.getLogger().info(CYELLOW + Log + CRESET);
    }

    public void logVoid(String Log){
        main.getLogger().info(CWHITE + Log + CRESET);
    }

    public void resetLog(String Log){
        main.getLogger().info(CRESET + Log);
    }

    public void spigot(String Log){
        main.getLogger().info(CYELLOW + "SpigotMC: " + CWHITE + Log);
    }
    public void polymart(String Log){
        main.getLogger().info(CDARKGREEN + "Polymart: " + CWHITE + Log);
    }
    public void modrinth(String Log){
        main.getLogger().info(CCYAN + "Modrinth: " + CWHITE + Log);
    }
}