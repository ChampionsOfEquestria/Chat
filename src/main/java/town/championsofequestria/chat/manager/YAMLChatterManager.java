package town.championsofequestria.chat.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import town.championsofequestria.chat.Settings;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;

public class YAMLChatterManager {

    private File channelFolder;
    private ChannelManager channelManager;
    private Settings settings;

    public YAMLChatterManager(File channelFolder, ChannelManager channelManager, Settings settings) {
        this.channelFolder = Objects.requireNonNull(channelFolder);
        this.channelManager = Objects.requireNonNull(channelManager);
        this.settings = Objects.requireNonNull(settings);
    }

    public Chatter load(Player player, EntityPlayer entityPlayer) {
        Optional<YamlConfiguration> oConfig = getConfig(new File(channelFolder, player.getUniqueId().toString() + ".yml"));
        if (!oConfig.isPresent()) {
            // The player does not already have an existing config. Let's make a new one.
            StandardChannel defaultChannel = settings.getDefaultChannel();
            ArrayList<StandardChannel> channels = new ArrayList<StandardChannel>(1);
            channels.add(defaultChannel);
            Chatter c = new Chatter(entityPlayer, player, defaultChannel, channels, new ArrayList<UUID>(0));
            save(c);
            return c;
        }
        YamlConfiguration config = oConfig.get();
        ArrayList<String> channelNames = (ArrayList<String>) config.getStringList("channels");
        ArrayList<StandardChannel> channels = new ArrayList<StandardChannel>(channelNames.size());
        for (String channelName : channelNames)
            channels.add(channelManager.getChannel(channelName).get());
        String activeChannelName = config.getString("active-channel");
        Optional<StandardChannel> optionalChannel = channelManager.getChannel(activeChannelName);
        StandardChannel channel;
        if (optionalChannel.isPresent())
            channel = optionalChannel.get();
        else if(channels.contains(settings.getDefaultChannel()))
            channel = settings.getDefaultChannel();
        else if(channels.size() > 0)
            channel = channels.get(0);
        else
            channel = null;
        ArrayList<String> uuidStrings = (ArrayList<String>) config.getStringList("ignores");
        ArrayList<UUID> ignores = new ArrayList<UUID>(uuidStrings.size());
        for (String uuid : uuidStrings)
            ignores.add(UUID.fromString(uuid));
        return new Chatter(entityPlayer, player, channel, channels, ignores);
    }

    public void save(Chatter chatter) {
        try {
            File file = new File(channelFolder, chatter.getPlayer().getUniqueId().toString() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("active-channel", chatter.getActiveChannel() == null ? chatter.getActiveChannel() : "NONE");
            config.set("channels", channelToString(chatter.getChannels()));
            config.save(file);
            config.set("ignores", chatter.getIgnores());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> channelToString(ArrayList<StandardChannel> arrayList) {
        ArrayList<String> names = new ArrayList<String>(arrayList.size());
        for (StandardChannel c : arrayList)
            names.add(c.getName());
        return names;
    }

    private Optional<YamlConfiguration> getConfig(File file) {
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.load(file);
            return Optional.of(config);
        } catch (IOException | InvalidConfigurationException e) {
            if (e instanceof FileNotFoundException)
                return Optional.empty();
            throw new RuntimeException(e);
        }
    }
}
