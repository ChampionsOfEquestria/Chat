package town.championsofequestria.chat.manager;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChatter;

public class ChatterManager {

    private ArrayList<StandardChatter> chatters = new ArrayList<StandardChatter>(0);
    private YAMLChatterManager yamlManager;

    public ChatterManager(YAMLChatterManager yamlManager) {
        this.yamlManager = Objects.requireNonNull(yamlManager);
    }

    public Optional<StandardChatter> getChatter(Player player) {
        return getChatter(player.getName());
    }

    public Optional<StandardChatter> getChatter(String player) {
        for (StandardChatter chatter : chatters) {
            if (chatter.getPlayer().getName().equalsIgnoreCase(player))
                return Optional.of(chatter);
        }
        return Optional.empty();
    }

    public ArrayList<Chatter> getChatters() {
        return new ArrayList<Chatter>(chatters);
    }

    /**
     * Only load chatters as needed
     * 
     * @param entityPlayer
     * @param ply          the player to load
     */
    public StandardChatter loadChatter(Player player) {
        StandardChatter chatter = yamlManager.load(player);
        chatters.add(chatter);
        return chatter;
    }

    public void saveChatter(StandardChatter chatter) {
        yamlManager.save(chatter);
    }

    public void unloadChatter(Player player) {
        Optional<StandardChatter> oChatter = getChatter(player);
        if (!oChatter.isPresent()) {
            ChatPlugin.getPlugin().getLogger().severe(ChatColor.RED + player.getName() + " left abnormally. Not saving their chat settings.");
            return;
        }

        StandardChatter chatter = oChatter.get();
        yamlManager.save(chatter);
        chatters.remove(chatter);
    }
}
