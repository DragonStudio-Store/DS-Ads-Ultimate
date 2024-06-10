package site.dragonstudio.ads.bungee.config;

import site.dragonstudio.ads.bungee.Main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private static Main main;
    private static int resourceId;

    public UpdateChecker(Main main, int resourceId) {
        UpdateChecker.main = main;
        UpdateChecker.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
             Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
            main.getLogger().info("No se pudo buscar actualizaciones: " + exception.getMessage());
        }
    }
}