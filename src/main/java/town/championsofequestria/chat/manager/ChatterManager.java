package town.championsofequestria.chat.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChatter;

public class ChatterManager {

    private YAMLChatterManager yamlManager;
    private HashMap<Player, StandardChatter> chattersMap = new HashMap<Player, StandardChatter>(0);
    private ArrayList<StandardChatter> chatters = new ArrayList<StandardChatter>(0);

    public ChatterManager(YAMLChatterManager yamlManager) {
        this.yamlManager = Objects.requireNonNull(yamlManager);
    }

    /**
     * Only load chatters as needed
     * 
     * @param entityPlayer
     * @param ply
     *            the player to load
     */
    public StandardChatter loadChatter(Player player, EntityPlayer entityPlayer) {
        StandardChatter chatter = yamlManager.load(player, entityPlayer);
        chattersMap.put(player, chatter);
        chatters.add(chatter);
        return chatter;
    }

    public Optional<StandardChatter> getChatter(Player player) {
        return Optional.ofNullable(chattersMap.get(player));
    }

    public Optional<StandardChatter> getChatter(String player) {
        for (StandardChatter chatter : chatters) {
            if (chatter.getPlayer().getName().equalsIgnoreCase(player))
                return Optional.of(chatter);
        }
        return Optional.empty();
    }

    public void unloadChatter(Player player) {
        StandardChatter chatter = chattersMap.remove(player);
        chatters.remove(chatter);
        yamlManager.save(chatter);
    }

    public void saveChatter(StandardChatter chatter) {
        yamlManager.save(chatter);
    }

    public ArrayList<Chatter> getChatters() {
        return new ArrayList<Chatter>(chatters);
    }
}
