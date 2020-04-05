package town.championsofequestria.chat.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import town.championsofequestria.chat.api.Chatter;

public class ChatterManager {

    private YAMLChatterManager yamlManager;
    private HashMap<Player, Chatter> chattersMap = new HashMap<Player, Chatter>(0);
    private ArrayList<Chatter> chatters = new ArrayList<Chatter>(0);

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
    public Chatter loadChatter(Player player, EntityPlayer entityPlayer) {
        Chatter chatter = yamlManager.load(player, entityPlayer);
        chattersMap.put(player, chatter);
        chatters.add(chatter);
        return chatter;
    }

    public Optional<Chatter> getChatter(Player player) {
        return Optional.ofNullable(chattersMap.get(player));
    }

    public Optional<Chatter> getChatter(String player) {
        for (Chatter chatter : chatters) {
            if (chatter.getPlayer().getName().equalsIgnoreCase(player))
                return Optional.of(chatter);
        }
        return Optional.empty();
    }

    public void unloadChatter(Player player) {
        Chatter chatter = chattersMap.remove(player);
        chatters.remove(chatter);
        yamlManager.save(chatter);
    }

    public void saveChatter(Chatter chatter) {
        yamlManager.save(chatter);
    }

    public ArrayList<Chatter> getChatters() {
        return new ArrayList<Chatter>(chatters);
    }
}
