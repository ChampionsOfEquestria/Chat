package town.championsofequestria.chat.command.handlers;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.command.ReplyCommand;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public class ReplyCommandExecutor extends BaseCommandExecutor {

    private ReplyCommand replyCommand;

    public ReplyCommandExecutor(ChatPlugin plugin, ChannelManager channelManager, ChatterManager chatterManager) {
        super(plugin, channelManager, chatterManager);
        this.replyCommand = new ReplyCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (sender instanceof Player) {
                return replyCommand.execute(chatterManager.getChatter((Player) sender).get(), Joiner.on(' ').join(ArrayUtils.subarray(args, 0, args.length)));
            }
        }
        return false;
    }
}
