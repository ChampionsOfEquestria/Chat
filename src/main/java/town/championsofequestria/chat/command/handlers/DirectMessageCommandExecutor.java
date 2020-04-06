package town.championsofequestria.chat.command.handlers;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
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
        ChatPlugin.getPlugin().getLogger().info(Arrays.toString(args));
        if (!(sender instanceof Player)) {
            Optional<StandardChatter> target = chatterManager.getChatter(args[0]);
            if (target.isPresent()) {
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "You need to give a message as console!");
                    return true;
                }
                return dmCommand.execute(ChatPlugin.consoleChatter, target.get(), Joiner.on(' ').join(ArrayUtils.subarray(args, 1, args.length)));
            }
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
            return true;
        }
        if (args.length > 0) {
            Optional<StandardChatter> target = chatterManager.getChatter(args[0]);
            StandardChatter player = chatterManager.getChatter((Player) sender).get();
            if (target.isPresent()) {
                if (args.length == 1) {
                    // Just focus
                    return dmCommand.focus(player, target.get());
                }
                return dmCommand.execute(player, target.get(), Joiner.on(' ').join(ArrayUtils.subarray(args, 1, args.length)));
            }
            if (args[0].equalsIgnoreCase(ChatPlugin.consoleChatter.getName())) {
                if (args.length == 1) {
                    // Just focus
                    return dmCommand.focus(player, ChatPlugin.consoleChatter);
                }
                return dmCommand.execute(player, ChatPlugin.consoleChatter, Joiner.on(' ').join(ArrayUtils.subarray(args, 1, args.length)));
            }
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
            return true;
        }
        sender.sendMessage("/" + label + " <player> <message>");
        return true;
    }
}
