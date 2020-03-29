package town.championsofequestria.chat.manager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import town.championsofequestria.chat.api.StandardChannel;

public class ChannelManager {

    private ArrayList<StandardChannel> channels = new ArrayList<StandardChannel>(0);

    public ChannelManager(ArrayList<StandardChannel> channels) {
        this.channels = Objects.requireNonNull(channels);
    }

    public boolean hasChannel(StandardChannel channel) {
        return channels.contains(channel);
    }

    public Optional<StandardChannel> getChannel(String name) {
        for (StandardChannel channel : channels) {
            if (channel.getName().equalsIgnoreCase(name))
                return Optional.of(channel);
        }
        return Optional.empty();
    }

    public Optional<StandardChannel> getChannelFromQuickCommand(String qm) {
        for (StandardChannel c : channels) {
            if (qm.equalsIgnoreCase(c.getQuickMessageCommand()))
                return Optional.of(c);
        }
        return Optional.empty();
    }

    public ArrayList<StandardChannel> getChannels() {
        return channels;
    }
}
