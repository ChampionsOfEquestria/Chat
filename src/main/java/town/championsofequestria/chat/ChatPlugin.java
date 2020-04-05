package town.championsofequestria.chat;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_15_R1.DedicatedServer;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.command.handlers.ChannelCommandExecutor;
import town.championsofequestria.chat.command.handlers.DirectMessageCommandExecutor;
import town.championsofequestria.chat.command.handlers.IgnoreCommandExecutor;
import town.championsofequestria.chat.command.handlers.MeCommandExecutor;
import town.championsofequestria.chat.command.handlers.ReplyCommandExecutor;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;
import town.championsofequestria.chat.manager.YAMLChannelManager;
import town.championsofequestria.chat.manager.YAMLChatterManager;

public class ChatPlugin extends JavaPlugin {

    private Settings settings;
    private EventListener listener;
    private MessageHandler messageHandler;
    private YAMLChannelManager yamlChannelManager;
    private ChannelManager channelManager;
    private ChatterManager chatterManager;
    private DedicatedServer dedicatedServer;
    private YAMLChatterManager yamlChatterManager;
    private Brigadier brigadier;
    private static ChatPlugin plugin;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        plugin = this;
        dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        registerCommands();
        settings = new Settings(this);
        loadChannels();
        channelManager = new ChannelManager(yamlChannelManager.loadChannels());
        loadChatters();
        chatterManager = new ChatterManager(yamlChatterManager);
        messageHandler = new MessageHandler(chatterManager);
        listener = new EventListener(messageHandler, channelManager, chatterManager);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("ch").setExecutor(new ChannelCommandExecutor(this, channelManager, chatterManager));
        getCommand("ignore").setExecutor(new IgnoreCommandExecutor(this, channelManager, chatterManager));
        getCommand("pm").setExecutor(new DirectMessageCommandExecutor(this, channelManager, chatterManager));
        getCommand("r").setExecutor(new ReplyCommandExecutor(this, channelManager, chatterManager));
        getCommand("me").setExecutor(new MeCommandExecutor(this, channelManager, chatterManager));
    }

    @Override
    public void onDisable() {
        for (Chatter c : chatterManager.getChatters())
            yamlChatterManager.save(c);
    }

    private void registerCommands() {
        brigadier = new Brigadier(this);
        brigadier.register(getCommand("ch"));
        brigadier.register(getCommand("ignore"));
        brigadier.register(getCommand("pm"));
    }

    private void loadChannels() {
        File channelFolder = new File(getDataFolder(), "channels");
        channelFolder.mkdirs();
        yamlChannelManager = new YAMLChannelManager(channelFolder);
    }

    private void loadChatters() {
        File chatterFolder = new File(getDataFolder(), "chatters");
        chatterFolder.mkdirs();
        yamlChatterManager = new YAMLChatterManager(chatterFolder, channelManager, settings);
    }

    public static ChatPlugin getPlugin() {
        return plugin;
    }

    public static boolean isNull(String string) {
        if (string == null || string.trim().isEmpty())
            return true;
        return false;
    }

    /**
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    public void logChat(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public DedicatedServer getDedicatedServer() {
        return dedicatedServer;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }

    public ChatterManager getChatterManager() {
        return chatterManager;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}
