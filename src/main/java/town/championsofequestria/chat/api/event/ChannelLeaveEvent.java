package town.championsofequestria.chat.api.event;

import org.bukkit.event.HandlerList;

import town.championsofequestria.chat.api.ChatResult;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.StandardChannel;

public class ChannelLeaveEvent extends ChannelEvent {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private ChatResult result;

    public ChannelLeaveEvent(StandardChatter chatter, StandardChannel channel, boolean async) {
        super(chatter, channel, async);
        this.result = ChatResult.ALLOWED;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public ChatResult getResult() {
        return result;
    }

    public void setResult(ChatResult result) {
        this.result = result;
    }
}
