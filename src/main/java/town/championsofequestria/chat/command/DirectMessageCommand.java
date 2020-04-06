package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.PrivateChannel;

public class DirectMessageCommand extends BaseCommand {

    public DirectMessageCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(Chatter sender, Chatter target, String message) {
        if (sender.equals(target)) {
            sender.sendMessage(ChatColor.GREEN + "It's weird to talk to yourself.");
            return true;
        }
        plugin.getMessageHandler().handlePM(sender, target.getPrivateChannel(), message, false);
        return true;
    }

    public boolean focus(Chatter sender, Chatter target) {
        if (sender.getActiveChannel() instanceof PrivateChannel) {
            PrivateChannel channel = (PrivateChannel) sender.getActiveChannel();
            if (channel.getTarget().equals(target)) {
                sender.sendMessage(ChatColor.RED + "You are already talking to this person.");
                return true;
            }
        }
        sender.setCurrentChannel(target.getPrivateChannel());
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Now chatting with " + target.getName());
        return true;
    }
}
