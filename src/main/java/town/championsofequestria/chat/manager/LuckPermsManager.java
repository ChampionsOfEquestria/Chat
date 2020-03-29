package town.championsofequestria.chat.manager;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class LuckPermsManager {

    private LuckPerms provider;

    public LuckPermsManager() throws IllegalStateException {
        try {
            provider = (LuckPerms) Bukkit.getServicesManager().getRegistration(Class.forName("net.luckperms.api.LuckPerms")).getProvider();
        } catch (NullPointerException | ClassNotFoundException ex) {
            throw new IllegalStateException("LuckPerms is not registered.", ex);
        }
    }

    public String getPlayerPrefix(Player player) {
        return this.getPlayerPrefix(this.getMetaData(player.getUniqueId()), 6);
    }

    public String getPlayerSuffix(Player player) {
        return this.getPlayerSuffix(this.getMetaData(player.getUniqueId()), 6);
    }

    public String getGroupPrefix(Player player) {
        String prefix = "";
        for (int i = 5; i > 0; i--) {
            prefix = this.getPlayerPrefix(this.getMetaData(player.getUniqueId()), i);
            if (prefix != "")
                return prefix;
        }
        return prefix;
    }

    public String getGroupSuffix(Player player) {
        String suffix = "";
        for (int i = 5; i > 0; i--) {
            suffix = this.getPlayerSuffix(this.getMetaData(player.getUniqueId()), i);
            if (suffix != "")
                return suffix;
        }
        return suffix;
    }

    public String getPrimaryGroup(Player player) {
        return provider.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
    }

    private CachedMetaData getMetaData(UUID playerUUID) {
        User user = provider.getUserManager().getUser(playerUUID);
        ContextManager contextManager = provider.getContextManager();
        return user.getCachedData().getMetaData(QueryOptions.contextual(contextManager.getContext(user).orElseGet(contextManager::getStaticContext)));
    }

    private String getPlayerPrefix(CachedMetaData data, int priority) {
        String prefix = data.getPrefixes().get(priority);
        return prefix != null ? prefix : "";
    }

    private String getPlayerSuffix(CachedMetaData data, int priority) {
        String suffix = data.getSuffixes().get(priority);
        return suffix != null ? suffix : "";
    }
}
