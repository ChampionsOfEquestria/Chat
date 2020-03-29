package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;

public class ListCommand extends BaseCommand {

    public ListCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(ConsoleCommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Here are the channels that are currently loaded.");
        for (StandardChannel c : channelManager.getChannels()) {
            sender.sendMessage(ChatColor.YELLOW + "- " + c.getColor() + c.getName());
        }
        return true;
    }

    public boolean execute(Chatter sender) {
        sender.sendMessage(ChatColor.YELLOW + "Here are the channels you can join.");
        for (StandardChannel c : channelManager.getChannels()) {
            if (sender.hasChannel(c))
                sender.sendMessage(ChatColor.YELLOW + "- " + ChatColor.GRAY + "*" + c.getColor() + c.getName());
            else if (sender.hasPermissionToJoin(c))
                sender.sendMessage(ChatColor.YELLOW + "- " + c.getColor() + c.getName());
        }
        return true;
    }
}
