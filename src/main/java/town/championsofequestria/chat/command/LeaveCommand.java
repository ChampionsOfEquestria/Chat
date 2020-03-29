package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;

public class LeaveCommand extends BaseCommand {

    public LeaveCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(Chatter chatter, StandardChannel channel) {
        if (!chatter.hasChannel(channel)) {
            chatter.sendMessage(ChatColor.RED + "You aren't in " + channel.getName());
            return true;
        }
        if (chatter.hasPermissionToLeave(channel)) {
            channel.announceLeaveMessage(chatter);
            chatter.removeChannel(channel);
            chatter.setCurrentChannel(null);
            return true;
        }
        chatter.sendMessage(ChatColor.RED + "You do not have permission to leave " + channel.getName());
        return true;
    }
}
