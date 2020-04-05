package town.championsofequestria.chat.command;

import java.util.Optional;

import org.bukkit.ChatColor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;

public class ReplyCommand extends BaseCommand {

    public ReplyCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(Chatter sender, String message) {
        Optional<Chatter> oTarget = sender.getLastChatter();
        if (oTarget.isPresent()) {
            Chatter target = oTarget.get();
            plugin.getMessageHandler().handlePM(sender, target.getPrivateChannel(), message, false);
            return true;
        }
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "No one has messaged you recently.");
        return true;
    }
}
