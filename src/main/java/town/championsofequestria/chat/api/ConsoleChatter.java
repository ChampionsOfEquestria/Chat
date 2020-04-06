package town.championsofequestria.chat.api;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleChatter extends Chatter {

    private ConsoleCommandSender sender = Bukkit.getConsoleSender();

    @Override
    public void sendMessage(String message) {
        sender.sendMessage(message);
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public String getName() {
        return "@Console";
    }

    @Override
    public boolean isIgnoring(Chatter chatter) {
        return false;
    }

    @Override
    public boolean hasChannel(StandardChannel standardChannel) {
        return false;
    }

    @Override
    public boolean isInWorld(ArrayList<World> worlds) {
        return false;
    }

    @Override
    public void setLastChatter(Chatter chatter) {
        this.lastReceivedMessageFrom = chatter;
    }

    @Override
    public Channel getActiveChannel() {
        if (lastReceivedMessageFrom != null)
            return lastReceivedMessageFrom.getPrivateChannel();
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCurrentChannel(Channel privateChannel) {
        if (privateChannel instanceof PrivateChannel) {
            this.lastReceivedMessageFrom = ((PrivateChannel) privateChannel).getTarget();
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermissionToPM() {
        return true;
    }

    @Override
    public boolean hasPermissionToJoin(Channel channel) {
        return true;
    }

    @Override
    public boolean hasPermissionToSpeak(Channel channel) {
        return true;
    }

    @Override
    public boolean hasPermissionToLeave(Channel channel) {
        return true;
    }

    @Override
    public boolean hasPermissionToColor(Channel channel) {
        return true;
    }

    @Override
    public boolean hasPermissionToEmote(Channel channel) {
        return true;
    }

    @Override
    public boolean mustForceJoin(Channel channel) {
        return false;
    }
}
