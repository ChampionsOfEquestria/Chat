package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.StandardChannel;

public class KickCommand extends BaseCommand {

    public KickCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(CommandSender sender, StandardChatter target, StandardChannel channel) {
        if (sender.hasPermission("brohoofchat.commands.kick")) {
            channel.announceLeaveMessage(target);
            target.removeChannel(channel);
            target.setCurrentChannel(null);
            target.sendMessage(ChatColor.YELLOW + "You were kicked from " + channel.getName() + "!");
            sender.sendMessage(ChatColor.GREEN + "Kicked " + target.getPlayer().getName() + " from channel " + channel.getName());
            return true;
        }
        sender.sendMessage(ChatColor.RED + "You do not have permission.");
        return true;
    }
}
