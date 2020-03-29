package town.championsofequestria.chat;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_15_R1.DedicatedServer;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;
import town.championsofequestria.chat.manager.YAMLChannelManager;
import town.championsofequestria.chat.manager.YAMLChatterManager;

public class ChatPlugin extends JavaPlugin {

    private Settings settings;
    private CommandHandler commandHandler;
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
        commandHandler = new CommandHandler(this, channelManager, chatterManager);
        getServer().getPluginManager().registerEvents(listener, this);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandHandler.onCommand(sender, command, label, args);
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

    /**
     * @return the commandHandler
     */
    public CommandHandler getCommandHandler() {
        return commandHandler;
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
