package town.championsofequestria.chat.command.handlers;

import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.command.IgnoreCommand;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public class IgnoreCommandExecutor extends BaseCommandExecutor {

    private IgnoreCommand ignoreCommand;

    public IgnoreCommandExecutor(ChatPlugin plugin, ChannelManager channelManager, ChatterManager chatterManager) {
        super(plugin, channelManager, chatterManager);
        this.ignoreCommand = new IgnoreCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This is a player only command.");
            return true;
        }
        if (args.length > 0) {
            Optional<StandardChatter> target = chatterManager.getChatter(args[0]);
            if (target.isPresent()) {
                return ignoreCommand.execute(chatterManager.getChatter((Player) sender).get(), target.get());
            }
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
            return true;
        }
        return false;
    }
}
