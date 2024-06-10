package site.dragonstudio.ads.spigot.commands;

import site.dragonstudio.ads.spigot.Main;
import site.dragonstudio.ads.spigot.ads.Ads;
import site.dragonstudio.ads.spigot.config.ConfigLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class AdsCommands implements CommandExecutor, TabCompleter {
    private final Main main;
    private final Ads ads;
    private final ConfigLoader configLoader;

    public AdsCommands(Main main, Ads ads, ConfigLoader configLoader) {
        this.main = main;
        this.ads = ads;
        this.configLoader = configLoader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            Component msg1 = configLoader.getPrefixedMessage("No-Subcmd");

            String message1 = LegacyComponentSerializer.legacySection().serialize(msg1);
            sender.sendMessage(message1);
            return true;
        }

        String perm;
        switch (args[0].toLowerCase()) {
            case "reload":
                perm = configLoader.getCommandPermission("Reload-Plugin");
                if (!sender.hasPermission(perm)) {
                    Component msg1 = configLoader.getPrefixedMessage("No-Perms");

                    String message1 = LegacyComponentSerializer.legacySection().serialize(msg1);
                    sender.sendMessage(message1);
                    return true;
                }
                configLoader.reloadConfig();
                main.reloadConfig();
                Component msg1 = configLoader.getPrefixedMessage("Reloaded-Sucess");

                String message1 = LegacyComponentSerializer.legacySection().serialize(msg1);
                sender.sendMessage(message1);
                break;
            case "enable":
                perm = configLoader.getCommandPermission("Manage-Ads");
                if (!sender.hasPermission(perm)) {
                    Component msg2 = configLoader.getPrefixedMessage("Reloaded-Sucess");

                    String message2 = LegacyComponentSerializer.legacySection().serialize(msg2);
                    sender.sendMessage(message2);
                    return true;
                }
                configLoader.setEnabled(true);

                Component msg2 = configLoader.getPrefixedMessage("Enabled-Plugin");

                String message2 = LegacyComponentSerializer.legacySection().serialize(msg2);
                sender.sendMessage(message2);
                break;
            case "disable":
                perm = configLoader.getCommandPermission("Manage-Ads");
                if (!sender.hasPermission(perm)) {
                    Component msg3 = configLoader.getPrefixedMessage("No-Perms");

                    String message3 = LegacyComponentSerializer.legacySection().serialize(msg3);
                    sender.sendMessage(message3);
                    return true;
                }
                configLoader.setEnabled(false);
                Component msg3 = configLoader.getPrefixedMessage("Disabled-Plugin");

                String message3 = LegacyComponentSerializer.legacySection().serialize(msg3);
                sender.sendMessage(message3);
                break;
            case "send":
                perm = configLoader.getCommandPermission("Send-Ads");
                if (!sender.hasPermission(perm)) {
                    Component msg4 = configLoader.getPrefixedMessage("No-Perms");

                    String message4 = LegacyComponentSerializer.legacySection().serialize(msg4);
                    sender.sendMessage(message4);
                    return true;
                }
                if (args.length < 3) {
                    Component msg4 = configLoader.getPrefixedMessage("Usage");

                    String message4 = LegacyComponentSerializer.legacySection().serialize(msg4);
                    sender.sendMessage(message4);
                    return true;
                }
                handleSendCommand(sender, args);
                break;
            default:
                Component msgDefault = configLoader.getPrefixedMessage("No-Subcmd");

                String messageDefault = LegacyComponentSerializer.legacySection().serialize(msgDefault);
                sender.sendMessage(messageDefault);
                return true;
        }
        return true;
    }

    private void handleSendCommand(CommandSender sender, String[] args) {
        String adName = args[1];
        String target = args[2];

        if (target.equalsIgnoreCase("*")) {
            int count = 0;
            for (Player player : main.getServer().getOnlinePlayers()) {
                if (attemptToSendAd(player, adName, true)) {
                    count++;
                }
            }

            String quantity = null;

            if(count != 0 && count > 1){
                quantity = "players";
            }

            if(count == 1){
                quantity = "player";
            }

            Component auxMsg1 = configLoader.getMessage("Everyone-Send" + "to " + count + quantity);

            String message1 = LegacyComponentSerializer.legacySection().serialize(auxMsg1);
            sender.sendMessage(message1);
        } else {
            Player player = main.getServer().getPlayer(target);
            if (player == null) {
                Component auxMsg2 = configLoader.getPrefixedMessage("No-Player-Found");

                String message2 = LegacyComponentSerializer.legacySection().serialize(auxMsg2);
                sender.sendMessage(message2);
                return;
            }
            if (attemptToSendAd(player, adName, true)) {
                Component auxMsg3 = configLoader.getPrefixedMessage("Player-Send");

                String message3 = LegacyComponentSerializer.legacySection().serialize(auxMsg3);
                sender.sendMessage(message3);
            } else {
                Component auxMsg4 = configLoader.getPrefixedMessage("Ad-Failed");

                String message4 = LegacyComponentSerializer.legacySection().serialize(auxMsg4);
                sender.sendMessage(message4);
            }
        }
    }

    private boolean attemptToSendAd(Player player, String adName, boolean bypassCooldown) {
        ads.sendAd(player, adName, bypassCooldown);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("ads.reload")) completions.add("reload");
            if (sender.hasPermission("ads.manage")) {
                completions.add("enable");
                completions.add("disable");
            }
            if (sender.hasPermission("ads.send")) completions.add("send");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("send")) {
            if (sender.hasPermission("ads.send")) {
                completions.addAll(configLoader.getAdNames());
                completions.remove("Ads-Cooldown");
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("send")) {
            if (sender.hasPermission("ads.send")) {
                completions.add("*");
                main.getServer().getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            }
        }
        return completions;
    }
}