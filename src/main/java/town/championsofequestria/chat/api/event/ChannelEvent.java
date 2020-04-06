package town.championsofequestria.chat.api.event;

import org.bukkit.event.Event;

import town.championsofequestria.chat.api.Channel;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChatter;

public abstract class ChannelEvent extends Event {

    private Channel channel;
    private Chatter chatter;

    public ChannelEvent(Chatter sender, Channel channel, boolean async) {
        super(async);
        this.chatter = sender;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Chatter getChatter() {
        return chatter;
    }
}
