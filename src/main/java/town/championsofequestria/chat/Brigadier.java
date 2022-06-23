package town.championsofequestria.chat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

import org.bukkit.command.Command;

import com.mojang.brigadier.tree.LiteralCommandNode;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;

class Brigadier {

    private ChatPlugin plugin;
    private Optional<Commodore> commodore;

    Brigadier(ChatPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin);
        this.commodore = getCommodore();
        if (commodore.isEmpty()) {
            plugin.getLogger().severe("Commodore is currently not available. This sucks ass!!! :(");
        }
    }

    public boolean isAvailable() {
        return commodore.isPresent();
    }

    void register(Command command) {
        try {
            Commodore commodore = this.commodore.get();
            InputStream is = plugin.getResource(command.getName() + ".commodore");
            if (is == null)
                throw new IOException("Brigadier command data missing from jar.");
            LiteralCommandNode<?> commandNode = CommodoreFileReader.INSTANCE.parse(is);
            commodore.register(command, commandNode);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Optional<Commodore> getCommodore() {
        try {
            if (CommodoreProvider.isSupported())
                return Optional.ofNullable(CommodoreProvider.getCommodore(plugin));
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}