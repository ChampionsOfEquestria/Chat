package town.championsofequestria.chat.api.event;

import org.bukkit.event.HandlerList;

import town.championsofequestria.chat.api.ChatResult;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;

public class ChannelChatEvent extends ChannelEvent {

    private static final HandlerList handlers = new HandlerList();
    private ChatResult result;
    private String message;
    private String format;

    public ChannelChatEvent(Chatter sender, StandardChannel channel, ChatResult result, String message, String channelFormat, boolean async) {
        super(sender, channel, async);
        this.result = result;
        this.message = message;
        this.format = channelFormat;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public ChatResult getResult() {
        return result;
    }

    public void setResult(ChatResult result) {
        this.result = result;
    }
}
