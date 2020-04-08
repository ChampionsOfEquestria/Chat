package town.championsofequestria.chat.api.event;

import org.bukkit.event.HandlerList;

import town.championsofequestria.chat.api.ChatResult;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.StandardChannel;

public class ChannelChatEvent extends ChannelEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String format;
    private String message;
    private ChatResult result;

    public ChannelChatEvent(StandardChatter sender, StandardChannel channel, ChatResult result, String message, String channelFormat, boolean async) {
        super(sender, channel, async);
        this.result = result;
        this.message = message;
        this.format = channelFormat;
    }

    public String getFormat() {
        return format;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public String getMessage() {
        return message;
    }

    public ChatResult getResult() {
        return result;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(ChatResult result) {
        this.result = result;
    }
}
