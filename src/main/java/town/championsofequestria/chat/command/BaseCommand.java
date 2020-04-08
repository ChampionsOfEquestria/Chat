package town.championsofequestria.chat.command;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public abstract class BaseCommand {

    protected ChannelManager channelManager;
    protected ChatterManager chatterManager;
    protected ChatPlugin plugin;

    public BaseCommand(ChatPlugin plugin) {
        this.plugin = plugin;
        this.chatterManager = plugin.getChatterManager();
        this.channelManager = plugin.getChannelManager();
    }
}
