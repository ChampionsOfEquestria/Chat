package town.championsofequestria.chat.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.StandardChannel;

public class YAMLChannelManager {

    private File channelFolder;

    public YAMLChannelManager(File channelFolder) {
        this.channelFolder = Objects.requireNonNull(channelFolder);
    }

    private ArrayList<UUID> getMuted(YamlConfiguration config) {
        ArrayList<String> uuidStrings = (ArrayList<String>) config.getStringList("muted");
        ArrayList<UUID> muted = new ArrayList<UUID>(uuidStrings.size());
        for (String uuid : uuidStrings)
            muted.add(UUID.fromString(uuid));
        return muted;
    }

    private ArrayList<World> getWorlds(YamlConfiguration config) {
        ArrayList<String> worldNames = (ArrayList<String>) config.getStringList("worlds");
        ArrayList<World> worlds = new ArrayList<World>(worldNames.size());
        for (String worldName : worldNames) {
            Optional<World> oWorld = Optional.<World>ofNullable(Bukkit.getWorld(worldName));
            if (!oWorld.isPresent()) {
                ChatPlugin.getPlugin().getLogger().severe("A Channel wanted to add " + worldName + ", but it wasn't loaded!");
                continue;
            }
            worlds.add(oWorld.get());
        }
        return worlds;
    }

    public StandardChannel load(String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(channelFolder, name + ".yml"));
        String format = ChatColor.translateAlternateColorCodes('&', config.getString("format")).replace("&r", ChatColor.RESET.toString());
        ArrayList<World> worlds = getWorlds(config);
        int local = config.getInt("local");
        ChatColor color = ChatColor.valueOf(config.getString("color"));
        String qmCommand = config.getString("quick-message-command");
        ArrayList<UUID> muted = getMuted(config);
        if (format.equals("{default}"))
            format = ChatPlugin.getPlugin().getSettings().getDefaultFormat();
        return new StandardChannel(name, qmCommand, muted, color, format, worlds, local);
    }

    public ArrayList<StandardChannel> loadChannels() {
        ArrayList<StandardChannel> channels = new ArrayList<StandardChannel>(0);
        for (String file : channelFolder.list()) {
            channels.add(load(file.substring(0, file.lastIndexOf(46))));
        }
        return channels;
    }

    public void save(StandardChannel channel) {
        try {
            File file = new File(channelFolder, channel.getName() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("format", channel.getFormat());
            config.set("worlds", channel.getWorldNames());
            config.set("local", channel.getLocalDistance());
            config.set("color", channel.getColor().name());
            config.set("quick-message-command", channel.getQuickMessageCommand());
            config.set("muted", channel.getMuteNames());
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
