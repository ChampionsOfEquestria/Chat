package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;

public class MuteCommand extends BaseCommand {

    public MuteCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(CommandSender sender, Chatter target, StandardChannel channel) {
        if (sender.hasPermission("brohoofchat.commands.mute")) {
            if (channel.isMuted(target)) {
                channel.removeMuted(target);
                target.sendMessage(ChatColor.GREEN + "You are no longer muted from " + channel.getName());
                sender.sendMessage(ChatColor.YELLOW + "Unmuted " + target.getPlayer().getName() + " from " + channel.getName());
                return true;
            }
            channel.addMuted(target);
            target.sendMessage(ChatColor.RED + "You were muted from " + channel.getName());
            sender.sendMessage(ChatColor.YELLOW + "Muted " + target.getPlayer().getName() + " from " + channel.getName());
            return true;
        }
        sender.sendMessage(ChatColor.RED + "You do not have permission.");
        return true;
    }
}
