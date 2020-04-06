package town.championsofequestria.chat.command;

import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Channel;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.api.PrivateChannel;
import town.championsofequestria.chat.api.StandardChannel;

public class EmoteCommand extends BaseCommand {

    public EmoteCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(StandardChatter chatter, String emote) {
        Channel activeChannel = chatter.getActiveChannel();
        if (activeChannel == null) {
            chatter.sendMessage(ChatColor.LIGHT_PURPLE + "You aren't currently focused in a channel.");
            return true;
        }
        if (activeChannel instanceof PrivateChannel) {
            chatter.sendMessage(ChatColor.RED + "You cannot emote in a private message!");
            return true;
        }
        if (chatter.hasPermissionToEmote(chatter.getActiveChannel())) {
            ((StandardChannel) chatter.getActiveChannel()).sendEmoteMessage(chatter, emote);
            return true;
        }
        chatter.sendMessage(ChatColor.RED + "You don't have permission to emote in " + chatter.getActiveChannel().getName());
        return true;
    }
}
