package site.dragonstudio.ads.bungee.commands;

import site.dragonstudio.ads.bungee.Main;
import site.dragonstudio.ads.bungee.config.ConfigLoader;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class AdsCommand extends Command implements TabExecutor {
    private static Main main;
    private static ConfigLoader configLoader;
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public AdsCommand(Main main, ConfigLoader configLoader) {
        super("ads");
        AdsCommand.main = main;
        AdsCommand.configLoader = configLoader;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendMessage(sender, configLoader.getPrefixedMessage("No-Subcmd"));
        } else {
            switch (args[0].toLowerCase()) {
                case "disable":
                    if (!sender.hasPermission(configLoader.getCommandPermission("Disable-Plugin"))) {
                        sendMessage(sender, configLoader.getPrefixedMessage("No-Perms"));
                        return;
                    }

                    if (!configLoader.getPluginConfig("Enabled")) {
                        sendMessage(sender, configLoader.getPrefixedMessage("Already-Disabled-Plugin"));
                        return;
                    }

                    configLoader.setEnabled(false);
                    sendMessage(sender, configLoader.getPrefixedMessage("Disabled-Plugin"));
                    break;
                case "enable":
                    if (!sender.hasPermission(configLoader.getCommandPermission("Enable-Plugin"))) {
                        sendMessage(sender, configLoader.getPrefixedMessage("No-Perms"));
                        return;
                    }

                    if (configLoader.getPluginConfig("Enabled")) {
                        sendMessage(sender, configLoader.getPrefixedMessage("Already-Enabled-Plugin"));
                        return;
                    }

                    configLoader.setEnabled(true);
                    sendMessage(sender, configLoader.getPrefixedMessage("Enabled-Plugin"));
                    break;
                case "reload":
                    if (!sender.hasPermission(configLoader.getCommandPermission("Reload-Plugin"))) {
                        sendMessage(sender, configLoader.getPrefixedMessage("No-Perms"));
                        return;
                    }

                    configLoader.reloadConfig();
                    sendMessage(sender, configLoader.getPrefixedMessage("Reloaded-Sucess"));
                    break;
                default:
                    sendMessage(sender, configLoader.getPrefixedMessage("No-Subcmd"));
            }
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> commands = Arrays.asList("disable", "enable", "reload");
            String input = args[0].toLowerCase(Locale.ROOT);
            List<String> completions = new ArrayList<>();
            Iterator<String> var6 = commands.iterator();

            while (var6.hasNext()) {
                String cmd = var6.next();
                if (cmd.startsWith(input)) {
                    completions.add(cmd);
                }
            }

            return completions;
        } else {
            return new ArrayList<>();
        }
    }

    private void sendMessage(CommandSender sender, Component message) {
        String auxMsgFinal = LegacyComponentSerializer.legacySection().serialize(message);
        sender.sendMessage(auxMsgFinal);
    }
}