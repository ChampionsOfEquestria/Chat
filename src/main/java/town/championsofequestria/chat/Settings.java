package town.championsofequestria.chat;

import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import town.championsofequestria.chat.api.StandardChannel;

public class Settings {

    private ChatPlugin plugin;
    private FileConfiguration config;
    private String defaultChannelName;
    private String defaultFormat;
    private String defaultAnnounceFormat;
    private String defaultEmote;
    private String privateMessage;

    public Settings(final ChatPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin);
        config = Objects.requireNonNull(plugin.getConfig());
        readSettings();
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

    public String getDefaultChannelName() {
        return defaultChannelName;
    }

    public StandardChannel getDefaultChannel() {
        return plugin.getChannelManager().getChannel(defaultChannelName).get();
    }

    public String getDefaultFormat() {
        return defaultFormat;
    }

    public String getDefaultAnnounceFormat() {
        return defaultAnnounceFormat;
    }

    public String getDefaultEmoteFormat() {
        return defaultEmote;
    }

    public String getPrivateMessageFormat() {
        return privateMessage;
    }
}
