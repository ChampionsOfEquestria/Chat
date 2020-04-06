package town.championsofequestria.chat.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.StandardChannel;
import town.championsofequestria.chat.api.event.ChannelJoinEvent;

public class JoinCommand extends BaseCommand {

    public JoinCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(StandardChatter chatter, StandardChannel channel) {
        ChannelJoinEvent event = new ChannelJoinEvent(chatter, channel, false);
        Bukkit.getPluginManager().callEvent(event);
        switch (event.getResult()) {
            case ALLOWED: {
                chatter.addChannel(channel);
                return true;
            }
            case NO_PERMISSION: {
                chatter.sendMessage(ChatColor.RED + "You are not allowed to join that channel.");
                return true;
            }
            default : {
                chatter.sendMessage(event.getResult().toString());
                return true;
            }
        }
    }
}
