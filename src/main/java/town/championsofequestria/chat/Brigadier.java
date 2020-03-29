package town.championsofequestria.chat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.bukkit.command.Command;

import com.mojang.brigadier.tree.LiteralCommandNode;

import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileFormat;

class Brigadier {

    private ChatPlugin plugin;
    private Commodore commodore;

    Brigadier(ChatPlugin plugin) {
        this.plugin = Objects.requireNonNull(plugin);
        this.commodore = Objects.requireNonNull(CommodoreProvider.getCommodore(plugin));
    }

    void register(Command command) {
        try {
            InputStream is = plugin.getResource(command.getName() + ".commodore");
            if (is == null)
                throw new IOException("Brigadier command data missing from jar.");
            LiteralCommandNode<?> commandNode = CommodoreFileFormat.parse(is);
            commodore.register(command, commandNode);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
