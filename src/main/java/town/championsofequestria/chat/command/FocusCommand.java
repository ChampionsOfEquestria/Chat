package town.championsofequestria.chat.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.StandardChannel;
import town.championsofequestria.chat.api.event.ChannelJoinEvent;

public class FocusCommand extends BaseCommand {

    public FocusCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(StandardChatter chatter, StandardChannel channel) {
        // They're already in that channel.
        if (chatter.hasChannel(channel)) {
            if(!chatter.hasPermissionToSpeak(channel)) {
                chatter.sendMessage(ChatColor.RED + "You don't have permission to chat in " + channel.getName());
                return true;
            }
            if (chatter.getActiveChannel() != null && chatter.getActiveChannel().equals(channel)) {
                chatter.sendMessage(ChatColor.RED + "You're already chatting in " + channel.getName());
                return true;
            }
            chatter.setCurrentChannel(channel);
            chatter.sendMessage(ChatColor.YELLOW + "Now chatting in " + channel.getName());
            return true;
        }
        // Try to join the channel
        ChannelJoinEvent event = new ChannelJoinEvent(chatter, channel, false);
        Bukkit.getPluginManager().callEvent(event);
        switch (event.getResult()) {
            case ALLOWED: {
                chatter.addChannel(channel);
                chatter.setCurrentChannel(channel);
                chatter.sendMessage(ChatColor.YELLOW + "Now chatting in " + channel.getName());
                return true;
            }
            case NO_PERMISSION: {
                chatter.sendMessage(ChatColor.RED + "You are not allowed to join that channel.");
                return true;
            }
            case MUTED: {
                chatter.sendMessage(ChatColor.RED + "You are muted from that channel!");
                return true;
            }
            case NO_SUCH_CHANNEL: {
                chatter.sendMessage(ChatColor.RED + channel.getName() + " doesn't appear to exist! How odd.");
                return true;
            }
            default : {
                throw new IllegalStateException();
            }
        }
    }
}
