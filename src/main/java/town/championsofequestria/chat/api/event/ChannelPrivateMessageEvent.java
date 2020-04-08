package town.championsofequestria.chat.api.event;

import org.bukkit.event.HandlerList;

import town.championsofequestria.chat.api.ChatResult;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.PrivateChannel;

public class ChannelPrivateMessageEvent extends ChannelEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private String message;
    private ChatResult result;

    public ChannelPrivateMessageEvent(Chatter sender, PrivateChannel channel, ChatResult result, String message, boolean async) {
        super(sender, channel, async);
        this.result = result;
        this.message = message;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(ChatResult result) {
        this.result = result;
    }
}
