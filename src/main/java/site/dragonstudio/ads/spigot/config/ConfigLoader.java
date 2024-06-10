package site.dragonstudio.ads.spigot.config;

import site.dragonstudio.ads.spigot.Main;
import site.dragonstudio.ads.spigot.console.ConsoleManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Objects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.jetbrains.annotations.NotNull;

public class ConfigLoader {
    private static FileConfiguration settings;
    private static File settingsFile;
    private final Main main;
    private final ConsoleManager console;

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ConfigLoader(Main main, ConsoleManager console){
        this.main = main;
        this.console = console;
        reloadConfig();
    }

    private void saveConfig() {
        try {
            settings.save(settingsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig(){
        settingsFile = new File(main.getDataFolder(), "Settings.yml");

        if (!settingsFile.exists()) {
            main.saveResource("Settings.yml", false);
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
            settings = YamlConfiguration.loadConfiguration(settingsFile);
            console.logSuccessful("Settings file loaded successfully");
            console.resetLog("");
            main.reloadConfig();
        } catch (Exception e) {
            console.logError("---------- Config Error ----------");
            console.resetLog("");
            console.logError("Error loading configuration file:");
            console.resetLog("");
            console.logError("Error Code:");
            console.logError("Error loading plugin configuration");
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
            main.saveResource("Settings.yml", false);
            reloadConfig();
        }
    }

    public void setEnabled(boolean enabled) {
        settings.set("Plugin-Configuration.Enabled-Plugin", enabled);
        try {
            settings.save(settingsFile);
        } catch (IOException e) {
            console.logError("---------- Overwrite error ----------");
            console.resetLog("");
            console.logError("Error overwriting Configuration file:");
            console.resetLog("");
            console.logError("Error Code:");
            console.logError("Settings file not found!");
            console.resetLog("");
            console.logError("---------------------------------");
            console.resetLog("");
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

    public String getVersion(){
        return settings.getString("Plugin-Version");
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
    public Component getSpeedMessage(String param) {
        String message = Objects.requireNonNull(param);
        return miniMessage.deserialize(message);
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
            Set<String> keys = Objects.requireNonNull(settings.getConfigurationSection("Ads-Configuration")).getKeys(false);
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
    public Boolean isBossbarAdEnabled(String AdName){
        try {
            boolean config = settings.getBoolean("Ads-Configuration." + AdName + ".Bossbar.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey(AdName);

            console.logError("The verification key for whether bossbar ad type is enabled for " + AdName + " could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public Boolean isTitleAdEnabled(String AdName){
        try {
            boolean config = settings.getBoolean("Ads-Configuration." + AdName + ".Title.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey(AdName);

            console.logError("The verification key for whether title ad type is enabled for " + AdName + " could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public Boolean isActionbarAdEnabled(String AdName){
        try {
            boolean config = settings.getBoolean("Ads-Configuration." + AdName + ".Actionbar.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey(AdName);

            console.logError("The verification key for whether actionbar ad type is enabled for " + AdName + " could not be found. It will be set to \"false\" by default");
            return false;
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
    public List<String> getAdWorlds(String AdName){
        try {
            List<String> worlds = settings.getStringList("Ads-Configuration." + AdName + ".worlds");

            return worlds;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Worlds set to \"Vanilla Overworld, Vanilla Nether & Vanilla End \", fix settings in ad: " + AdName);

            List<String> listAux = new ArrayList<>();
            listAux.add("world");
            listAux.add("world_nether");
            listAux.add("world_the_end");

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
    public Boolean isWelcomeBossbarAdEnabled(){
        try {
            boolean config = settings.getBoolean("Welcome-Ad.WelcomeAd-Content.Bossbar.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey("Welcome Ad");

            console.logError("The verification key for whether bossbar ad type is enabled for Welcome ad could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public Boolean isTitleWelcomeAdEnabled(){
        try {
            boolean config = settings.getBoolean("Welcome-Ad.WelcomeAd-Content.Title.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey("Welcome Ad");

            console.logError("The verification key for whether title ad type is enabled for Welcome Ad could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public Boolean isActionbarWelcomeAdEnabled(){
        try {
            boolean config = settings.getBoolean("Welcome-Ad.WelcomeAd-Content.Actionbar.Enabled");

            return (Boolean) config;
        } catch (NullPointerException e) {
            notKey("Welcome Ad");

            console.logError("The verification key for whether actionbar ad type is enabled for Welcome Ad could not be found. It will be set to \"false\" by default");
            return false;
        }
    }

    @NotNull
    public String getWelcomeChatAdSound() {
        try {
            String sound = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Chat.Sound"));

            return sound;
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Sound set to \"BLOCK_NOTE_BLOCK_PLING\", fix settings in ad:  Welcome Ad");

            return "BLOCK_NOTE_BLOCK_PLING";
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
    public Component getWelcomeBossbarMessage(){
        try {
            String message = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Bossbar.Message"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Bossbar content set to \" \", Correct the error as soon as possible in the ad:  Welcome Ad");

            return miniMessage.deserialize("<red>No Bossbar content</red>");
        }
    }

    @NotNull
    public String getWelcomeBossbarColor(){
        try {
            String color = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Bossbar.Color"));

            return color;
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Bossbar color set to \"PINK\", Correct the error as soon as possible in the ad:  Welcome Ad");

            return "PINK";
        }
    }

    @NotNull
    public String getWelcomeBossbarStyle(){
        try {
            String style = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Bossbar.Style"));

            return style;
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Bossbar style set to \"SOLID\", Correct the error as soon as possible in the ad:  Welcome Ad");

            return "SOLID";
        }
    }

    @NotNull
    public Component getWelcomeTitleTitle(){
        try {
            String message = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Title.Title"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Title content set to \" \", Correct the error as soon as possible in the ad:  Welcome Ad");

            return miniMessage.deserialize("<red>No Title content</red>");
        }
    }

    @NotNull
    public Component getWelcomeTitleSubtitle(){
        try {
            String message = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Title.Subtitle"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Subtitle content set to \" \", Correct the error as soon as possible in the ad:  Welcome Ad");

            return miniMessage.deserialize("<red>No Title content</red>");
        }
    }

    @NotNull
    public Integer getWelcomeTitleFadeIn(){
        try {
            int fadeIn = settings.getInt("Welcome-Ad.WelcomeAd-Content.Title.FadeIn");

            return (Integer) fadeIn;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Title Fade In set to \"10\", Correct the error as soon as possible in the ad:  Welcome Ad");
            return 10;
        }
    }

    @NotNull
    public Integer getWelcomeTitleStay(){
        try {
            int stay = settings.getInt("Welcome-Ad.WelcomeAd-Content.Title.Stay");

            return (Integer) stay;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Title Stay set to \"70\", Correct the error as soon as possible in the ad:  Welcome Ad");
            return 70;
        }
    }

    @NotNull
    public Integer getWelcomeTitleFadeOut(){
        try {
            int fadeOut = settings.getInt("Welcome-Ad.WelcomeAd-Content.Title.FadeOut");

            return (Integer) fadeOut;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Title Fade Out set to \"20\", Correct the error as soon as possible in the ad:  Welcome Ad");
            return 20;
        }
    }

    @NotNull
    public Component getWelcomeActionbarMessage(){
        try {
            String message = Objects.requireNonNull(settings.getString("Welcome-Ad.WelcomeAd-Content.Actionbar.Message"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd("Welcome Ad");
            console.logError("Actionbar content set to \" \", Correct the error as soon as possible in the ad:  Welcome Ad");

            return miniMessage.deserialize("<red>No Bossbar content</red>");
        }
    }

    @NotNull
    public String getChatAdSound(String AdName) {
        try {
            String sound = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Chat.Sound"));

            return sound;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Sound set to \"BLOCK_NOTE_BLOCK_PLING\", fix settings in ad: " + AdName);

            return "BLOCK_NOTE_BLOCK_PLING";
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

    @NotNull
    public Component getBossbarMessage(String AdName){
        try {
            String message = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Bossbar.Message"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Bossbar content set to \" \", Correct the error as soon as possible in the ad: " + AdName);

            return miniMessage.deserialize("<red>No Bossbar content</red>");
        }
    }

    @NotNull
    public String getBossbarColor(String AdName){
        try {
            String color = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Bossbar.Color"));

            return color;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Bossbar color set to \"PINK\", Correct the error as soon as possible in the ad: " + AdName);

            return "PINK";
        }
    }

    @NotNull
    public String getBossbarStyle(String AdName){
        try {
            String style = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Bossbar.Style"));

            return style;
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Bossbar style set to \"SOLID\", Correct the error as soon as possible in the ad: " + AdName);

            return "SOLID";
        }
    }

    @NotNull
    public Component getTitleTitle(String AdName){
        try {
            String message = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Title.Title"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Bossbar content set to \" \", Correct the error as soon as possible in the ad: " + AdName);

            return miniMessage.deserialize("<red>No Title content</red>");
        }
    }

    @NotNull
    public Component getTitleSubtitle(String AdName){
        try {
            String message = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Title.Subtitle"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Subtitle content set to \" \", Correct the error as soon as possible in the ad: " + AdName);

            return miniMessage.deserialize("<red>No Title content</red>");
        }
    }

    @NotNull
    public Integer getTitleFadeIn(String AdName){
        try {
            int fadeIn = settings.getInt("Ads-Configuration." + AdName + ".Title.FadeIn");

            return (Integer) fadeIn;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Title Fade In set to \"10\", Correct the error as soon as possible in the ad: " + AdName);
            return 10;
        }
    }

    @NotNull
    public Integer getTitleStay(String AdName){
        try {
            int stay = settings.getInt("Ads-Configuration." + AdName + ".Title.Stay");

            return (Integer) stay;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Title Stay set to \"70\", Correct the error as soon as possible in the ad: " + AdName);
            return 70;
        }
    }

    @NotNull
    public Integer getTitleFadeOut(String AdName){
        try {
            int fadeOut = settings.getInt("Ads-Configuration." + AdName + ".Title.FadeOut");

            return (Integer) fadeOut;
        } catch (NullPointerException e){
            notCooldown();

            console.logError("Title Fade Out set to \"20\", Correct the error as soon as possible in the ad: " + AdName);
            return 20;
        }
    }

    @NotNull
    public Component getActionbarMessage(String AdName){
        try {
            String message = Objects.requireNonNull(settings.getString("Ads-Configuration." + AdName + ".Actionbar.Message"));

            return miniMessage.deserialize(message);
        } catch (NullPointerException e) {
            notAd(AdName);
            console.logError("Actionbar content set to \" \", Correct the error as soon as possible in the ad: " + AdName);

            return miniMessage.deserialize("<red>No ActionBar content</red>");
        }
    }
}