package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;

public class WhoCommand extends BaseCommand {

    public WhoCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(CommandSender sender, StandardChannel channel) {
        StringBuilder builder = new StringBuilder();
        builder.append(channel.getColor() + channel.getName() + ChatColor.RESET + ": ");
        for (Chatter c : chatterManager.getChatters()) {
            if (c.hasChannel(channel))
                builder.append(c.getName() + " ");
        }
        sender.sendMessage(builder.toString());
        return true;
    }

    public boolean execute(StandardChatter chatter, StandardChannel channel) {
        if (chatter.hasPermissionToJoin(channel)) {
            StringBuilder builder = new StringBuilder();
            builder.append(channel.getColor() + channel.getName() + ChatColor.RESET + ": ");
            for (Chatter c : chatterManager.getChatters()) {
                if (c.hasChannel(channel))
                    builder.append(c.getName() + " ");
            }
            chatter.sendMessage(builder.toString());
            return true;
        }
        chatter.sendMessage(ChatColor.RED + "You don't have permission to join that channel.");
        return true;
    }
}
