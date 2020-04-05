package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import town.championsofequestria.chat.ChatPlugin;

public class Chatter {

    private EntityPlayer entityPlayer;
    private Player player;
    private ArrayList<StandardChannel> channels;
    private Channel activeChannel;
    private ArrayList<UUID> ignores;
    private PrivateChannel privateChannelForPlayer;
    private Chatter lastReceivedMessageFrom;

    public Chatter(EntityPlayer entityPlayer, Player player, Channel activeChannel, ArrayList<StandardChannel> channels2, ArrayList<UUID> ignores) {
        this.player = player;
        this.entityPlayer = entityPlayer;
        this.activeChannel = activeChannel;
        this.channels = channels2;
        this.ignores = ignores;
        lastReceivedMessageFrom = null;
        privateChannelForPlayer = new PrivateChannel(this);
    }

    public Channel getActiveChannel() {
        return activeChannel;
    }

    public boolean isInWorld(World world) {
        return player.getWorld().equals(world);
    }

    public boolean isInWorld(ArrayList<World> worlds) {
        boolean result = false;
        for (World world : worlds) {
            if (isInWorld(world)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public ArrayList<StandardChannel> getChannels() {
        return channels;
    }

    public PrivateChannel getPrivateChannel() {
        return privateChannelForPlayer;
    }

    public boolean hasChannel(Channel channel) {
        return channels.contains(channel);
    }

    public void addChannel(StandardChannel channel) {
        channels.add(channel);
        channel.announceJoinMessage(this);
        save();
    }

    public String getName() {
        return player.getName();
    }

    public void removeChannel(StandardChannel channel) {
        channels.remove(channel);
        save();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

    public void sendMessage(String message) {
        sendMessage(CraftChatMessage.fromString(message));
    }

    private void sendMessage(IChatBaseComponent[] iChatBaseComponent) {
        entityPlayer.sendMessage(iChatBaseComponent);
    }

    public boolean hasPermissionToJoin(Channel channel) {
        return player.hasPermission("brohoofchat.join." + channel.getName());
    }

    public boolean hasPermissionToSpeak(Channel channel) {
        return player.hasPermission("brohoofchat.speak." + channel.getName());
    }

    public boolean hasPermissionToLeave(Channel channel) {
        return player.hasPermission("brohoofchat.leave." + channel.getName());
    }

    public boolean hasPermissionToColor(Channel channel) {
        return player.hasPermission("brohoofchat.color." + channel.getName());
    }

    public boolean hasPermissionToEmote(Channel channel) {
        return player.hasPermission("brohoofchat.emote." + channel.getName());
    }

    public boolean mustForceJoin(Channel channel) {
        return player.hasPermission("brohoofchat.forcejoin." + channel.getName());
    }

    public boolean isInRange(Chatter other, int distance) {
        Player otherPlayer = other.getPlayer();
        return !player.getWorld().equals(otherPlayer.getWorld()) ? false : player.getLocation().distanceSquared(otherPlayer.getLocation()) <= distance * distance;
    }

    public boolean isInWorld(Chatter other) {
        return player.getWorld().equals(other.getPlayer().getWorld());
    }

    public boolean isIgnoring(Chatter sender) {
        return ignores.contains(sender.getPlayer().getUniqueId());
    }

    public void setCurrentChannel(Channel privateChannel) {
        this.activeChannel = privateChannel;
        save();
    }

    public void addIgnore(Chatter target) {
        ignores.add(target.getPlayer().getUniqueId());
        save();
    }

    public void removeIgnore(Chatter target) {
        ignores.remove(target.getPlayer().getUniqueId());
        save();
    }

    private void save() {
        ChatPlugin.getPlugin().getChatterManager().saveChatter(this);
    }

    public ArrayList<UUID> getIgnores() {
        return ignores;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chatter) {
            Chatter c = (Chatter) o;
            if (player.getName().equals(c.player.getName()))
                return true;
        }
        return false;
    }

    public void setLastChatter(Chatter c) {
        this.lastReceivedMessageFrom = Objects.requireNonNull(c);
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public Optional<Chatter> getLastChatter() {
        if (this.lastReceivedMessageFrom.isOnline())
            return Optional.of(lastReceivedMessageFrom);
        return Optional.empty();
    }
}
