package site.dragonstudio.ads.spigot.ads;

import site.dragonstudio.ads.spigot.Main;
import site.dragonstudio.ads.spigot.config.ConfigLoader;
import site.dragonstudio.ads.spigot.console.ConsoleManager;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import me.clip.placeholderapi.PlaceholderAPI;

public class WelcomeAd {
    private final Main main;
    private final ConfigLoader configLoader;
    private final ConsoleManager console;
    private Map<String, Long> cooldowns = new HashMap<>();
    private Map<Player, BossBar> bossBars = new HashMap<>();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static int CENTER_PX = 154;

    public WelcomeAd(Main main, ConfigLoader configLoader, ConsoleManager console){
        this.main = main;
        this.configLoader = configLoader;
        this.console = console;
    }

    public void sendAd(Player player) {
        if (configLoader.isWelcomeChatAdEnabled()) {
            List<String> chatMessages = configLoader.getWelcomeChatAdContent();
            sendChatMessage(player, chatMessages);
        }

        if (configLoader.isWelcomeBossbarAdEnabled()) {
            Component messageAux1 = configLoader.getWelcomeBossbarMessage();
            String message = LegacyComponentSerializer.legacySection().serialize(messageAux1);

            String color = configLoader.getWelcomeBossbarColor();
            String style = configLoader.getWelcomeBossbarStyle();
            sendBossBar(player, message, color, style);
        }

        if (configLoader.isTitleWelcomeAdEnabled()) {
            Component titleAux = configLoader.getWelcomeTitleTitle();
            String title = LegacyComponentSerializer.legacySection().serialize(titleAux);

            Component subtitleAux = configLoader.getWelcomeTitleSubtitle();
            String subtitle = LegacyComponentSerializer.legacySection().serialize(subtitleAux);

            int fadeIn = configLoader.getWelcomeTitleFadeIn();
            int stay = configLoader.getWelcomeTitleStay();
            int fadeOut = configLoader.getWelcomeTitleFadeOut();
            sendTitle(player, title, subtitle, fadeIn, stay, fadeOut);
        }

        if (configLoader.isActionbarWelcomeAdEnabled()) {
            Component messageAux = configLoader.getWelcomeActionbarMessage();
            String message = LegacyComponentSerializer.legacySection().serialize(messageAux);

            sendActionbar(player, message);
        }
    }

    public String centerMessage(String message) {
        int messagePxSize = 0;
        boolean insideTag = false;

        for (char c : message.toCharArray()) {
            if (c == '<') {
                insideTag = true;
                continue;
            } else if (c == '>') {
                insideTag = false;
                continue;
            }

            if (!insideTag) {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += dFI.getLength() + 1;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb.toString() + message;
    }

    public enum DefaultFontInfo{
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 4),
        DEFAULT('a', 4);

        private char character;
        private int length;

        DefaultFontInfo(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public char getCharacter(){
            return this.character;
        }

        public int getLength(){
            return this.length;
        }

        public int getBoldLength(){
            if(this == DefaultFontInfo.SPACE) return this.getLength();
            return this.length + 1;
        }

        public static DefaultFontInfo getDefaultFontInfo(char c){
            for(DefaultFontInfo dFI : DefaultFontInfo.values()){
                if(dFI.getCharacter() == c) return dFI;
            }
            return DefaultFontInfo.DEFAULT;
        }
    }

    public void sendChatMessage(Player player, List<String> chatMessages) {
        if (configLoader.isWelcomeChatAdEnabled()) {
            String soundName = configLoader.getWelcomeChatAdSound();
            Sound sound = Sound.valueOf(soundName);

            for (String message : chatMessages) {
                String messageWithPlaceholders = PlaceholderAPI.setPlaceholders(player, message);

                if (messageWithPlaceholders.contains("<center>")) {
                    messageWithPlaceholders = messageWithPlaceholders.replace("<center>", "").replace("</center>", "");
                    messageWithPlaceholders = centerMessage(messageWithPlaceholders);
                }

                Component auxMsg = miniMessage.deserialize(messageWithPlaceholders);
                String messageFinally = LegacyComponentSerializer.legacySection().serialize(auxMsg);

                player.sendMessage(messageFinally);
            }

            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        }
    }

    public void sendBossBar(Player player, String message, String color, String style) {
        if (configLoader.isWelcomeBossbarAdEnabled()) {
            String parsedMessage = PlaceholderAPI.setPlaceholders(player, message.replace("%player_name%", player.getName()));

            BossBar bossBar = Bukkit.createBossBar(
                    parsedMessage,
                    BarColor.valueOf(color.toUpperCase()),
                    BarStyle.valueOf(style.toUpperCase())
            );

            bossBar.addPlayer(player);

            bossBars.put(player, bossBar);

            int durationTicks = 20 * 10;
            Bukkit.getScheduler().runTaskLater(main, () -> {
                bossBar.removePlayer(player);
                bossBars.remove(player);
            }, durationTicks);
        }
    }

    public void hideWelcomeBossBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            BossBar bossBar = bossBars.get(player);
            if (bossBar != null) {
                bossBar.removePlayer(player);
                bossBars.remove(player);
            }
        }
    }

    public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (configLoader.isTitleWelcomeAdEnabled()) {
            String parsedTitle = PlaceholderAPI.setPlaceholders(player, title.replace("%player_name%", player.getName()));
            String parsedSubtitle = PlaceholderAPI.setPlaceholders(player, subtitle.replace("%player_name%", player.getName()));

            player.sendTitle(parsedTitle, parsedSubtitle, fadeIn, stay, fadeOut);
        }
    }

    public void sendActionbar(Player player, String message) {
        if (configLoader.isActionbarWelcomeAdEnabled()) {
            String parsedMessage = PlaceholderAPI.setPlaceholders(player, message.replace("%player_name%", player.getName()));

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(parsedMessage));
        }
    }
}