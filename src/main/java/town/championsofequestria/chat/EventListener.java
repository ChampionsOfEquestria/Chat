package town.championsofequestria.chat;

import java.util.Objects;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import town.championsofequestria.chat.api.ChatResult;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.PrivateChannel;
import town.championsofequestria.chat.api.StandardChannel;
import town.championsofequestria.chat.api.event.ChannelChatEvent;
import town.championsofequestria.chat.api.event.ChannelJoinEvent;
import town.championsofequestria.chat.api.event.ChannelLeaveEvent;
import town.championsofequestria.chat.api.event.ChannelPrivateMessageEvent;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public class EventListener implements Listener {

    private MessageHandler messageHandler;
    private ChannelManager channelManager;
    private ChatterManager chatterManager;

    public EventListener(MessageHandler messageHandler, ChannelManager channelManager, ChatterManager chatterManager) {
        this.messageHandler = Objects.requireNonNull(messageHandler);
        this.channelManager = Objects.requireNonNull(channelManager);
        this.chatterManager = Objects.requireNonNull(chatterManager);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerAsyncEvent(final AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        messageHandler.handle(event.getPlayer(), event.getMessage(), event.isAsynchronous());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerPrivateMessageEvent(final ChannelPrivateMessageEvent event) {
        PrivateChannel channel = (PrivateChannel) event.getChannel();
        if (!event.getChatter().getPlayer().hasPermission("brohoofchat.commands.pm")) {
            event.setResult(ChatResult.NO_PERMISSION);
        }
        if (channel.isMuted(event.getChatter())) {
            event.setResult(ChatResult.MUTED);
            return;
        }
        if(!channel.getTarget().isOnline()) {
            event.setResult(ChatResult.NO_SUCH_CHANNEL);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerChatEvent(final ChannelChatEvent event) {
        StandardChannel channel = (StandardChannel) event.getChannel();
        if (!channelManager.hasChannel(channel)) {
            event.setResult(ChatResult.NO_SUCH_CHANNEL);
            return;
        }
        if (channel.isPerWorld() && !event.getChatter().isInWorld(channel.getWorlds())) {
            event.setResult(ChatResult.NO_SUCH_CHANNEL);
            return;
        }
        if (!event.getChatter().hasPermissionToSpeak(event.getChannel())) {
            event.setResult(ChatResult.NO_PERMISSION);
            return;
        }
        if (event.getChannel().isMuted(event.getChatter())) {
            event.setResult(ChatResult.MUTED);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerChannelJoinEvent(final ChannelJoinEvent event) {
        if (!channelManager.hasChannel((StandardChannel) event.getChannel())) {
            event.setResult(ChatResult.NO_SUCH_CHANNEL);
            return;
        }
        if (!event.getChatter().hasPermissionToJoin(event.getChannel())) {
            event.setResult(ChatResult.NO_PERMISSION);
            return;
        }
        if (event.getChannel().isMuted(event.getChatter())) {
            event.setResult(ChatResult.MUTED);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerChannelLeaveEvent(final ChannelLeaveEvent event) {
        if (!channelManager.hasChannel((StandardChannel) event.getChannel())) {
            event.setResult(ChatResult.NO_SUCH_CHANNEL);
            return;
        }
        if (!event.getChatter().hasPermissionToLeave(event.getChannel())) {
            event.setResult(ChatResult.NO_PERMISSION);
            return;
        }
        if (event.getChannel().isMuted(event.getChatter())) {
            event.setResult(ChatResult.MUTED);
            return;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoinEvent(final PlayerJoinEvent event) {
        Chatter chatter = chatterManager.loadChatter(event.getPlayer(), ((CraftPlayer) event.getPlayer()).getHandle());
        for (StandardChannel c : channelManager.getChannels()) {
            if (chatter.mustForceJoin(c))
                if (!chatter.hasChannel(c))
                    chatter.addChannel(c);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLeaveEvent(final PlayerQuitEvent event) {
        if (event.getPlayer() == null) {
            ChatPlugin.getPlugin().getLogger().info("A PlayerQuitEvent was fired with the player being null!");
            return;
        }
        chatterManager.unloadChatter(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKickEvent(final PlayerKickEvent event) {
        chatterManager.unloadChatter(event.getPlayer());
    }
}
