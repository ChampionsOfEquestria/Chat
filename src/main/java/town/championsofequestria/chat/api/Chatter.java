package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.World;

public abstract class Chatter {

    protected Chatter lastReceivedMessageFrom;
    protected PrivateChannel privateChannelForPlayer;

    public Chatter() {
        this.privateChannelForPlayer = new PrivateChannel(this);
        this.lastReceivedMessageFrom = null;
    }

    public abstract void addChannel(StandardChannel channel);

    public abstract void addIgnore(StandardChatter target);

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chatter) {
            Chatter c = (Chatter) o;
            if (getName().equals(c.getName()))
                return true;
        }
        return false;
    }

    public abstract Channel getActiveChannel();

    public abstract ArrayList<StandardChannel> getChannels();

    public Optional<Chatter> getLastChatter() {
        if (this.lastReceivedMessageFrom.isOnline())
            return Optional.of(lastReceivedMessageFrom);
        return Optional.empty();
    }

    public abstract String getName();

    public PrivateChannel getPrivateChannel() {
        return privateChannelForPlayer;
    }

    public abstract boolean hasChannel(StandardChannel standardChannel);

    public abstract boolean hasPermissionToColor(Channel channel);

    public abstract boolean hasPermissionToEmote(Channel channel);

    public abstract boolean hasPermissionToJoin(Channel channel);

    public abstract boolean hasPermissionToLeave(Channel channel);

    public abstract boolean hasPermissionToPM();

    public abstract boolean hasPermissionToSocialSpy();

    public abstract boolean hasPermissionToSpeak(Channel channel);

    public abstract boolean isIgnoring(Chatter chatter);

    public abstract boolean isInRange(Chatter chatter, int distance);

    public abstract boolean isInWorld(ArrayList<World> worlds);

    public abstract boolean isOnline();

    public abstract boolean mustForceJoin(Channel channel);

    public abstract void removeChannel(StandardChannel channel);

    public abstract void removeIgnore(StandardChatter target);

    public abstract void sendMessage(String message);

    public abstract void setCurrentChannel(Channel privateChannel);

    public abstract void setLastChatter(Chatter chatter);
}
