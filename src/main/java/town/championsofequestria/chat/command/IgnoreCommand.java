package town.championsofequestria.chat.command;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;

public class IgnoreCommand extends BaseCommand {

    public IgnoreCommand(ChatPlugin plugin) {
        super(plugin);
    }

    public boolean execute(StandardChatter chatter, StandardChatter target) {
        if (chatter.isIgnoring(target)) {
            // Remove from the list
            chatter.removeIgnore(target);
            chatter.sendMessage("No longer ignoring " + target.getPlayer().getName());
            return true;
        }
        // Add to this list
        chatter.addIgnore(target);
        chatter.sendMessage("Now ignoring " + target.getPlayer().getName());
        return true;
    }
}
