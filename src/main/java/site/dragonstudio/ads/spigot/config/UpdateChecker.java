package site.dragonstudio.ads.spigot.config;

import site.dragonstudio.ads.spigot.Main;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final Main main;
    private final int resourceId;

    public UpdateChecker(Main main, int resourceId) {
        this.main = main;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                main.getLogger().info("Could not check for updates: " + exception.getMessage());
            }
        });
    }
}