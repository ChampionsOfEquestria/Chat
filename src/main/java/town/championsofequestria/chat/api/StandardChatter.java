package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import town.championsofequestria.chat.ChatPlugin;

public class StandardChatter extends Chatter {

    private Channel activeChannel;
    private ArrayList<StandardChannel> channels;
    private EntityPlayer entityPlayer;
    private ArrayList<UUID> ignores;
    private Player player;
    private UUID uuid;

    public StandardChatter(EntityPlayer entityPlayer, Player player, Channel activeChannel, ArrayList<StandardChannel> channels2, ArrayList<UUID> ignores) {
        this.player = player;
        this.entityPlayer = entityPlayer;
        this.activeChannel = activeChannel;
        this.channels = channels2;
        this.ignores = ignores;
        this.uuid = player.getUniqueId();
    }

    @Override
    public void addChannel(StandardChannel channel) {
        channels.add(channel);
        channel.announceJoinMessage(this);
        save();
    }

    @Override
    public void addIgnore(StandardChatter target) {
        ignores.add(target.getPlayer().getUniqueId());
        save();
    }

    @Override
    public Channel getActiveChannel() {
        return activeChannel;
    }

    @Override
    public ArrayList<StandardChannel> getChannels() {
        return channels;
    }

    public ArrayList<UUID> getIgnores() {
        return ignores;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean hasChannel(StandardChannel channel) {
        return channels.contains(channel);
    }

    @Override
    public boolean hasPermissionToColor(Channel channel) {
        return player.hasPermission("brohoofchat.color." + channel.getName());
    }

    @Override
    public boolean hasPermissionToEmote(Channel channel) {
        return player.hasPermission("brohoofchat.emote." + channel.getName());
    }

    @Override
    public boolean hasPermissionToJoin(Channel channel) {
        return player.hasPermission("brohoofchat.join." + channel.getName());
    }

    @Override
    public boolean hasPermissionToLeave(Channel channel) {
        return player.hasPermission("brohoofchat.leave." + channel.getName());
    }

    @Override
    public boolean hasPermissionToPM() {
        return player.hasPermission("brohoofchat.commands.pm");
    }

    @Override
    public boolean hasPermissionToSocialSpy() {
        return player.hasPermission("brohoofchat.socialspy");
    }

    @Override
    public boolean hasPermissionToSpeak(Channel channel) {
        return player.hasPermission("brohoofchat.speak." + channel.getName());
    }

    @Override
    public boolean isIgnoring(Chatter sender) {
        if (!(sender instanceof StandardChatter))
            return false;
        return ignores.contains(((StandardChatter) sender).getPlayer().getUniqueId());
    }

    @Override
    public boolean isInRange(Chatter chatter, int distance) {
        if (!(chatter instanceof StandardChatter))
            return false;
        Player otherPlayer = ((StandardChatter) chatter).getPlayer();
        return !player.getWorld().equals(otherPlayer.getWorld()) ? false : player.getLocation().distanceSquared(otherPlayer.getLocation()) <= distance * distance;
    }

    @Override
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

    public boolean isInWorld(StandardChatter other) {
        return player.getWorld().equals(other.getPlayer().getWorld());
    }

    public boolean isInWorld(World world) {
        return player.getWorld().equals(world);
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean mustForceJoin(Channel channel) {
        return player.hasPermission("brohoofchat.forcejoin." + channel.getName());
    }

    @Override
    public void removeChannel(StandardChannel channel) {
        channels.remove(channel);
        save();
    }

    @Override
    public void removeIgnore(StandardChatter target) {
        ignores.remove(target.getPlayer().getUniqueId());
        save();
    }

    private void save() {
        ChatPlugin.getPlugin().getChatterManager().saveChatter(this);
    }

    private void sendMessage(IChatBaseComponent[] iChatBaseComponent) {
        entityPlayer.sendMessage(iChatBaseComponent);
    }

    @Override
    public void sendMessage(String message) {
        sendMessage(CraftChatMessage.fromString(message));
    }

    @Override
    public void setCurrentChannel(Channel privateChannel) {
        this.activeChannel = privateChannel;
        save();
    }

    @Override
    public void setLastChatter(Chatter c) {
        this.lastReceivedMessageFrom = Objects.requireNonNull(c);
    }
}
