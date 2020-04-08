package town.championsofequestria.chat.command.handlers;

import java.util.Objects;

import org.bukkit.command.CommandExecutor;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public abstract class BaseCommandExecutor implements CommandExecutor {

    protected ChannelManager channelManager;
    protected ChatterManager chatterManager;
    protected ChatPlugin plugin;

    public BaseCommandExecutor(ChatPlugin plugin, ChannelManager channelManager, ChatterManager chatterManager) {
        this.plugin = plugin;
        this.channelManager = Objects.requireNonNull(channelManager);
        this.chatterManager = Objects.requireNonNull(chatterManager);
    }
}
