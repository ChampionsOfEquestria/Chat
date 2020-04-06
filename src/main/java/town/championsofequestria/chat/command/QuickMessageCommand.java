package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.StandardChannel;

public class QuickMessageCommand extends BaseCommand {

    public QuickMessageCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(StandardChatter sender, StandardChannel channel, String message) {
        if (!sender.hasChannel(channel)) {
            sender.sendMessage(ChatColor.RED + "You aren't in that channel! You need to /ch join it first.");
            return true;
        }
        plugin.getMessageHandler().handle(sender, channel, message, false);
        return true;
    }
}
