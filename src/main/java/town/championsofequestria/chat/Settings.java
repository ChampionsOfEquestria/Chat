package town.championsofequestria.chat;

import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import town.championsofequestria.chat.api.StandardChannel;

public class Settings {

    private FileConfiguration config;
    private String defaultAnnounceFormat;
    private String defaultChannelName;
    private String defaultEmote;
    private String defaultFormat;
    private ChatPlugin plugin;
    private String privateMessage;

    public Settings(final ChatPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin);
        config = Objects.requireNonNull(plugin.getConfig());
        readSettings();
    }

    public String getDefaultAnnounceFormat() {
        return defaultAnnounceFormat;
    }

    public StandardChannel getDefaultChannel() {
        return plugin.getChannelManager().getChannel(defaultChannelName).get();
    }

    public String getDefaultChannelName() {
        return defaultChannelName;
    }

    public String getDefaultEmoteFormat() {
        return defaultEmote;
    }

    public String getDefaultFormat() {
        return defaultFormat;
    }

    public String getPrivateMessageFormat() {
        return privateMessage;
    }

    protected void readSettings() {
        defaultChannelName = ChatColor.translateAlternateColorCodes('&', config.getString("default-channel")).replace("&r", ChatColor.RESET.toString());
        defaultFormat = ChatColor.translateAlternateColorCodes('&', config.getString("format.default")).replace("&r", ChatColor.RESET.toString());
        defaultAnnounceFormat = ChatColor.translateAlternateColorCodes('&', config.getString("format.announce")).replace("&r", ChatColor.RESET.toString());
        defaultEmote = ChatColor.translateAlternateColorCodes('&', config.getString("format.emote")).replace("&r", ChatColor.RESET.toString());
        privateMessage = ChatColor.translateAlternateColorCodes('&', config.getString("format.private-message")).replace("&r", ChatColor.RESET.toString());
    }

    public void reloadSettings() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        readSettings();
    }
}
