package town.championsofequestria.chat.command.handlers;

import java.util.Optional;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.command.DirectMessageCommand;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;


public class DirectMessageCommandExecutor extends BaseCommandExecutor {
    


    private DirectMessageCommand dmCommand;

    public DirectMessageCommandExecutor(ChatPlugin plugin, ChannelManager channelManager, ChatterManager chatterManager) {
        super(plugin, channelManager, chatterManager);
        this.dmCommand = new DirectMessageCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player only command.");
            return true;
        }
        if (args.length > 0) {
            Optional<Chatter> target = chatterManager.getChatter(args[0]);
            if (target.isPresent()) {
                if (args.length == 0) {
                    // Just focus
                    return dmCommand.focus(chatterManager.getChatter((Player) sender).get(), target.get());
                }
                return dmCommand.execute(chatterManager.getChatter((Player) sender).get(), target.get(), Joiner.on(' ').join(ArrayUtils.subarray(args, 1, args.length)));
            }
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
            return true;
        }
        sender.sendMessage("/" + command.getName() + " <player> <message>");
        return true;
    }
}
