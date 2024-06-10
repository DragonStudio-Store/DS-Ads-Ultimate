package site.dragonstudio.ads.bungee.config;

import net.kyori.adventure.text.minimessage.MiniMessage;
import site.dragonstudio.ads.bungee.Main;
import site.dragonstudio.ads.bungee.console.ConsoleManager;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import net.kyori.adventure.text.Component;

import org.jetbrains.annotations.NotNull;

public class ConfigLoader {
    private static Main main;
    private static Configuration settings;
    private static File settingsFile;
    private static ConsoleManager console;

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ConfigLoader(Main main, ConsoleManager console) {
        ConfigLoader.main = main;
        ConfigLoader.console = console;
        saveDefaultConfig();
        reloadConfig();
    }

    public String getVersion(){
        return settings.getString("Plugin-Version");
    }

    public void saveDefaultConfig() {
        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
        }

        File file = new File(main.getDataFolder(), "bSettings.yml");
        if (!file.exists()) {
            try {
                InputStream in = main.getResourceAsStream("bSettings.yml");
                try {
                    Files.copy(in, file.toPath(), new CopyOption[0]);
                } catch (Throwable var6) {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }
                    throw var6;
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException var7) {
                handleConfigError(file);
            }
        }
    }

    public void setEnabled(boolean enabled) {
        settings.set("Plugin-Configuration.Enabled-Plugin", enabled);

        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(settings, new File(main.getDataFolder(), "bSettings.yml"));
        } catch (IOException var3) {
            IOException e = var3;
            e.printStackTrace();
        }

    }

    public void reloadConfig(){
        settingsFile = new File(main.getDataFolder(), "bSettings.yml");

        if (!settingsFile.exists()) {
            saveDefaultConfig();
        }

        File oldConfigDir = new File(main.getDataFolder(), "OldConfig");
        if (!oldConfigDir.exists()) {
            oldConfigDir.mkdirs();
        }

        File errorConfigDir = new File(main.getDataFolder(), "Error");
        if (!errorConfigDir.exists()) {
            errorConfigDir.mkdirs();
        }

        console.resetLog("");
        console.logSuccessful("Loading Settings File...");

        try {
            settings = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(main.getDataFolder(), "bSettings.yml"));
            console.logSuccessful("Settings file loaded successfully");
            console.resetLog("");
        } catch (Exception e) {
            console.logError("---------- Config Error ----------");
            console.resetLog("");
            console.logError("Error loading configuration file:");
            console.resetLog("");
            console.logError("Error Code:");
            console.logError("Error loading main configuration");
            console.logError("I recommend analyzing the configuration");
            console.logError("before loading it again.");
            console.resetLog("");
            console.logError("---------------------------------");
            console.resetLog("");
            handleConfigError(errorConfigDir);
        }
    }

    public void handleConfigError(File targetDir) {
        File targetFile = new File(targetDir, "ErrorConfig_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".yml");
        if (settingsFile.renameTo(targetFile)) {
            saveDefaultConfig();
            reloadConfig();
        }
    }

    private void notKey(String path){
        console.logError("---------- Key not found ----------");
        console.resetLog(" ");
        console.logError("A YAML file key was not found:");
        console.resetLog(" ");
        console.logError("Error Code:");
        console.logError("The key " + path + " was not found");
        console.resetLog(" ");
        console.logError("---------------------------------");
        console.resetLog(" ");
    }

    private void notAd(String AdName){
        console.logError("---------- Ad config not found ----------");
        console.resetLog(" ");
        console.logError("The ad config cannot be found:");
        console.resetLog(" ");
        console.logError("Error Code:");
        console.logError("The Ad config " + AdName + " was not found");
        console.resetLog(" ");
        console.logError("---------------------------------");
        console.resetLog(" ");
    }

    private void notCooldown(){
        console.logError("---------- Cooldown not found ----------");
        console.resetLog(" ");
        console.logError("The cooldown ads cannot be found:");
        console.resetLog(" ");
        console.logError("Error Code:");
        console.logError("The Cooldown was not found");
        console.resetLog(" ");
        console.logError("---------------------------------");
        console.resetLog(" ");
    }

    @NotNull
    public Component getPrefixedMessage(String path) {
        try {
            String prefix = Objects.requireNonNull(settings.getString("Messages.Prefix"));
            String message = Objects.requireNonNull(settings.getString("Messages." + path), "No Key Found " + path);
            return miniMessage.deserialize(prefix + message);
        } catch (NullPointerException e) {
            notKey(path);
            return miniMessage.deserialize("<red>No Key found: " + path + "</red>");
        }
    }

    @NotNull
    public Component getMessage(String path) {
        try {
            String message = Objects.requireNonNull(settings.getString("Messages." + path), "No Key Found " + path);
            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notKey(path);
            return miniMessage.deserialize("<red>No Key found: " + path + "</red>");
        }
    }

    @NotNull
    public Boolean getPluginConfig(String path){
        try {
            boolean config = settings.getBoolean("Plugin-Configuration." + path);

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey(path);

            console.logError("Key set to true by default, fix the error as soon as possible" + path);
            return true;
        }
    }

    @NotNull
    public Integer getAdsCooldown(){
        try {
            int cooldown = settings.getInt("Ads-Configuration.Ads-Cooldown");

            return (Integer) cooldown;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Ad cooldown has been set to \"10\" seconds, fix the error as soon as possible");
            return 10;
        }
    }

    @NotNull
    public String getAdPermission(String AdName){
        try {
            String permission = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".permission"));

            return permission;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Permission set to \" \", fix settings in ad: " + AdName);

            return "";
        }
    }

    @NotNull
    public String getCommandPermission(String path){
        try {
            String permission = Objects.requireNonNull(settings.getString("Plugin-Permissions." + path));

            return permission;
        } catch (NullPointerException e) {
            notKey(path);

            console.logError("Permission set to \" \", fix the error as soon as possible" + path);
            return "";
        }
    }

    @NotNull
    public List<String> getAdNames() {
        try {
            Set<String> keys = (Set<String>) Objects.requireNonNull(settings.getSection("Ads-Configuration")).getKeys();
            List<String> filteredKeys = keys.stream()
                    .filter(key -> !key.equals("Ads-Cooldown"))
                    .collect(Collectors.toList());

            return filteredKeys;
        } catch (NullPointerException e) {
            console.logError("No ads configuration!!!!");
            return new ArrayList<>();
        }
    }

    @NotNull
    public Boolean isChatAdEnabled(String AdName){
        try {
            boolean config = settings.getBoolean("Ads-Configuration." + AdName + ".Chat.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey(AdName);

            console.logError("The verification key for whether chat ad type is enabled for " + AdName + " could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public List<String> getAdServers(String AdName){
        try {
            List<String> servers = settings.getStringList("Ads-Configuration." + AdName + ".worlds");

            return servers;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Servers set to \"Lobby & Survival\", fix settings in ad: " + AdName);

            List<String> listAux = new ArrayList<>();
            listAux.add("Lobby");
            listAux.add("Survival");

            return listAux;
        }
    }

    @NotNull
    public Boolean isWelcomeChatAdEnabled(){
        try {
            boolean config = settings.getBoolean("Welcome-Ad.WelcomeAd-Content.Chat.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey("Welcome Ad");

            console.logError("The verification key for whether chat ad type is enabled for Welcome Ad could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public List<String> getWelcomeChatAdContent(){
        try {
            List<String> adContent = settings.getStringList("Welcome-Ad.WelcomeAd-Content.Chat.Ad-content");

            return adContent;
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("The default message has been set for ad Welcome Ad");

            List<String> listAux = new ArrayList<>();
            listAux.add("----------");
            listAux.add(" No ad config there ");
            listAux.add(" this is a error");
            listAux.add("----------");

            return listAux;
        }
    }

    @NotNull
    public List<String> getChatAdContent(String AdName){
        try {
            List<String> adContent = settings.getStringList("Ads-Configuration." + AdName + ".Chat.Ad-content");

            return adContent;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("The default message has been set for ad" + AdName);

            List<String> listAux = new ArrayList<>();
            listAux.add("----------");
            listAux.add(" No ad config there ");
            listAux.add(" this is a error");
            listAux.add("----------");

            return listAux;
        }
    }
}
