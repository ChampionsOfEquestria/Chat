package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import town.championsofequestria.chat.ChatPlugin;

public abstract class Channel {

    public abstract String getName();

    public abstract String getQuickMessageCommand();

    public abstract boolean isMuted(Chatter chatter);

    protected void logChat(String message) {
        ChatPlugin.getPlugin().getDedicatedServer().console.sendMessage(message);
    }

    protected abstract void sendChannelMessage(ArrayList<Chatter> recipents, String message);

    public abstract void sendChatMessage(Chatter sender, String message);

    protected String stripColor(String message) {
        Pattern pattern = Pattern.compile("(?i)(&)([0-9a-fk-or])");
        Matcher match = pattern.matcher(message);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "");
        }
        return match.appendTail(sb).toString();
    }
}
