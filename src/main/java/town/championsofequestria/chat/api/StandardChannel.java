package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.World;

import town.championsofequestria.chat.ChatPlugin;

public class StandardChannel extends Channel {

    private String format;
    private ChatColor color;
    private String name;
    private int local;
    private ArrayList<World> worlds;
    private String qmCommand;
    private ArrayList<UUID> muted;

    public StandardChannel(String name, String qmCommand, ArrayList<UUID> muted, ChatColor color, String format, ArrayList<World> worlds, int local) {
        this.name = name;
        this.qmCommand = qmCommand;
        this.muted = muted;
        this.color = color;
        this.format = format;
        this.worlds = worlds;
        this.local = local;
    }

    /**
     * Sends a message to the channel, determining who should be the recipent.
     * 
     * @param sender
     * @param message
     */
    @Override
    public void sendChatMessage(Chatter sender, String message) {
        ArrayList<Chatter> recipents = ChatPlugin.getPlugin().getChatterManager().getChatters();
        for (Chatter chatter : ChatPlugin.getPlugin().getChatterManager().getChatters()) {
            if (!chatter.hasChannel(this)) {
                recipents.remove(chatter);
            }
            if (this.isLocal() && !sender.isInRange(chatter, local)) {
                recipents.remove(chatter);
            }
            if (isPerWorld() && !chatter.isInWorld(worlds)) {
                recipents.remove(chatter);
            }
            if (chatter.isIgnoring(sender)) {
                recipents.remove(chatter);
            }
        }
        sendChannelMessage(recipents, message);
    }

    public void sendAnnouncementMessage(String message) {
        ArrayList<Chatter> recipents = ChatPlugin.getPlugin().getChatterManager().getChatters();
        for (Chatter chatter : ChatPlugin.getPlugin().getChatterManager().getChatters()) {
            if (!chatter.hasChannel(this)) {
                recipents.remove(chatter);
            }
        }
        sendChannelMessage(recipents, formatAnnounceMessage(message));
    }

    private String formatAnnounceMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', ChatPlugin.getPlugin().getSettings().getDefaultAnnounceFormat().replace("{color}", this.color.toString()).replace("{name}", this.name).replace("{msg}", stripColor(message)));
    }

    public void sendEmoteMessage(Chatter chatter, String message) {
        sendChatMessage(chatter, formatEmoteMessage(chatter.getName() + " " + message));
    }

    private String formatEmoteMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', ChatPlugin.getPlugin().getSettings().getDefaultEmoteFormat().replace("{color}", this.color.toString()).replace("{name}", this.name).replace("{msg}", stripColor(message)));
    }

    /**
     * Private method to actually do the sending and logging.
     * 
     * @param recipents
     * @param message
     */
    @Override
    protected void sendChannelMessage(ArrayList<Chatter> recipents, String message) {
        logChat(message);
        recipents.forEach((chatter) -> chatter.sendMessage(message));
    }

    public String getFormat() {
        return format;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public boolean isLocal() {
        return local > 0;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getQuickMessageCommand() {
        return qmCommand;
    }

    @Override
    public boolean isMuted(Chatter chatter) {
        return isMuted(chatter.getPlayer().getUniqueId());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StandardChannel)
            if (this.name.equals(((StandardChannel) o).name))
                return true;
        return false;
    }

    public boolean isMuted(UUID uuid) {
        return muted.contains(uuid);
    }

    public void announceJoinMessage(Chatter chatter) {
        this.sendAnnouncementMessage(chatter.getPlayer().getName() + " has joined the channel.");
    }

    public void announceLeaveMessage(Chatter chatter) {
        this.sendAnnouncementMessage(chatter.getPlayer().getName() + " has left the channel.");
    }

    public void removeMuted(Chatter target) {
        muted.remove(target.getPlayer().getUniqueId());
    }

    public void addMuted(Chatter target) {
        muted.add(target.getPlayer().getUniqueId());
    }

    public ArrayList<UUID> getMutes() {
        return muted;
    }

    public int getLocalDistance() {
        return local;
    }

    public boolean isPerWorld() {
        return worlds.size() > 0;
    }

    public ArrayList<String> getMuteNames() {
        ArrayList<String> muteNames = new ArrayList<String>(muted.size());
        for (UUID uuid : muted)
            muteNames.add(uuid.toString());
        return muteNames;
    }

    public ArrayList<String> getWorldNames() {
        ArrayList<String> worldNames = new ArrayList<String>(worlds.size());
        for (World world : worlds)
            worldNames.add(world.getName());
        return worldNames;
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }
}
