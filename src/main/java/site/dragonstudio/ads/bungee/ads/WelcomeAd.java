package site.dragonstudio.ads.bungee.ads;

import site.dragonstudio.ads.bungee.Main;
import site.dragonstudio.ads.bungee.config.ConfigLoader;
import site.dragonstudio.ads.bungee.console.ConsoleManager;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class WelcomeAd {
    private static Main main;
    private static ConfigLoader configLoader;
    private static ConsoleManager console;
    private Map<String, Long> cooldowns = new HashMap<>();
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final Random random = new Random();
    private static final int CENTER_PX = 154;

    public WelcomeAd(Main main, ConfigLoader configLoader, ConsoleManager console){
        WelcomeAd.main = main;
        WelcomeAd.configLoader = configLoader;
        WelcomeAd.console = console;
    }

    public void sendAd(ProxiedPlayer player) {
        if (configLoader.isWelcomeChatAdEnabled()) {
            List<String> chatMessages = configLoader.getWelcomeChatAdContent();
            sendChatMessage(player, chatMessages);
        }
    }

    public void sendChatMessage(ProxiedPlayer player, List<String> chatMessages) {
        if (configLoader.isWelcomeChatAdEnabled()) {
            for (String message : chatMessages) {
                Component auxMsg1 = miniMessage.deserialize(message);
                String auxMsgFinal = LegacyComponentSerializer.legacySection().serialize(auxMsg1);

                player.sendMessage(auxMsgFinal);
            }
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
}