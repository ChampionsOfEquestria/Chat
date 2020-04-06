package town.championsofequestria.chat.command.handlers;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChatter;
import town.championsofequestria.chat.command.EmoteCommand;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public class MeCommandExecutor extends BaseCommandExecutor {

    private EmoteCommand emoteCommand;

    public MeCommandExecutor(ChatPlugin plugin, ChannelManager channelManager, ChatterManager chatterManager) {
        super(plugin, channelManager, chatterManager);
        this.emoteCommand = new EmoteCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        StandardChatter chatter = chatterManager.getChatter((Player) sender).get();
        if (args.length > 0) {
            return emoteCommand.execute(chatter, Joiner.on(' ').join(ArrayUtils.subarray(args, 0, args.length)));
        }
        return false;
    }
}
