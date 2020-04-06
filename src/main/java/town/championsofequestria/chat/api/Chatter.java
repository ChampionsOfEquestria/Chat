package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.Optional;

import org.bukkit.World;

public abstract class Chatter {

    protected PrivateChannel privateChannelForPlayer;
    protected Chatter lastReceivedMessageFrom;

    public Chatter() {
        this.privateChannelForPlayer = new PrivateChannel(this);
        this.lastReceivedMessageFrom = null;
    }

    public abstract void sendMessage(String message);

    public abstract boolean isOnline();

    public PrivateChannel getPrivateChannel() {
        return privateChannelForPlayer;
    }

    public abstract String getName();

    public abstract boolean isIgnoring(Chatter chatter);

    public abstract boolean hasChannel(StandardChannel standardChannel);

    public abstract boolean isInWorld(ArrayList<World> worlds);

    public abstract void setLastChatter(Chatter chatter);

    public abstract Channel getActiveChannel();

    public abstract void setCurrentChannel(Channel privateChannel);

    public abstract boolean hasPermissionToPM();

    public abstract boolean hasPermissionToJoin(Channel channel);

    public abstract boolean hasPermissionToSpeak(Channel channel);

    public abstract boolean hasPermissionToLeave(Channel channel);

    public abstract boolean hasPermissionToColor(Channel channel);

    public abstract boolean hasPermissionToEmote(Channel channel);

    public abstract boolean mustForceJoin(Channel channel);
    
    public Optional<Chatter> getLastChatter() {
        if (this.lastReceivedMessageFrom.isOnline())
            return Optional.of(lastReceivedMessageFrom);
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chatter) {
            Chatter c = (Chatter) o;
            if (getName().equals(c.getName()))
                return true;
        }
        return false;
    }

    
}
