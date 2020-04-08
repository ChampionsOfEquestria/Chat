package town.championsofequestria.chat.api;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.World;

import town.championsofequestria.chat.ChatPlugin;

public class StandardChannel extends Channel {

    private ChatColor color;
    private String format;
    private int local;
    private ArrayList<UUID> muted;
    private String name;
    private String qmCommand;
    private ArrayList<World> worlds;

    public StandardChannel(String name, String qmCommand, ArrayList<UUID> muted, ChatColor color, String format, ArrayList<World> worlds, int local) {
        this.name = name;
        this.qmCommand = qmCommand;
        this.muted = muted;
        this.color = color;
        this.format = format;
        this.worlds = worlds;
        this.local = local;
    }

    public void addMuted(StandardChatter target) {
        muted.add(target.getPlayer().getUniqueId());
    }

    public void announceJoinMessage(StandardChatter chatter) {
        this.sendAnnouncementMessage(chatter.getPlayer().getName() + " has joined the channel.");
    }

    public void announceLeaveMessage(StandardChatter chatter) {
        this.sendAnnouncementMessage(chatter.getPlayer().getName() + " has left the channel.");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StandardChannel)
            if (this.name.equals(((StandardChannel) o).name))
                return true;
        return false;
    }

    private String formatAnnounceMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', ChatPlugin.getPlugin().getSettings().getDefaultAnnounceFormat().replace("{color}", this.color.toString()).replace("{name}", this.name).replace("{msg}", stripColor(message)));
    }

    private String formatEmoteMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', ChatPlugin.getPlugin().getSettings().getDefaultEmoteFormat().replace("{color}", this.color.toString()).replace("{name}", this.name).replace("{msg}", stripColor(message)));
    }

    public ChatColor getColor() {
        return color;
    }

    public String getFormat() {
        return format;
    }

    public int getLocalDistance() {
        return local;
    }

    public ArrayList<String> getMuteNames() {
        ArrayList<String> muteNames = new ArrayList<String>(muted.size());
        for (UUID uuid : muted)
            muteNames.add(uuid.toString());
        return muteNames;
    }

    public ArrayList<UUID> getMutes() {
        return muted;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getQuickMessageCommand() {
        return qmCommand;
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

    public boolean isLocal() {
        return local > 0;
    }

    @Override
    public boolean isMuted(Chatter chatter) {
        if (!(chatter instanceof StandardChatter))
            return false;
        return isMuted(((StandardChatter) chatter).getPlayer().getUniqueId());
    }

    public boolean isMuted(UUID uuid) {
        return muted.contains(uuid);
    }

    public boolean isPerWorld() {
        return worlds.size() > 0;
    }

    public void removeMuted(StandardChatter target) {
        muted.remove(target.getPlayer().getUniqueId());
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

    /**
     * Sends a message to the channel, determining who should be the recipent.
     * 
     * @param sender
     * @param message
     */
    @Override
    public void sendChatMessage(Chatter oSender, String message) {
        ArrayList<Chatter> recipents = ChatPlugin.getPlugin().getChatterManager().getChatters();
        StandardChatter sender = (StandardChatter) oSender;
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

    public void sendEmoteMessage(StandardChatter chatter, String message) {
        sendChatMessage(chatter, formatEmoteMessage(chatter.getName() + " " + message));
    }

    @Override
    public String toString() {
        return name;
    }
}
