package town.championsofequestria.chat.command.handlers;

import java.util.Optional;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

import town.championsofequestria.chat.ChatPlugin;
import town.championsofequestria.chat.api.Chatter;
import town.championsofequestria.chat.api.StandardChannel;
import town.championsofequestria.chat.command.DirectMessageCommand;
import town.championsofequestria.chat.command.EmoteCommand;
import town.championsofequestria.chat.command.FocusCommand;
import town.championsofequestria.chat.command.IgnoreCommand;
import town.championsofequestria.chat.command.JoinCommand;
import town.championsofequestria.chat.command.KickCommand;
import town.championsofequestria.chat.command.LeaveCommand;
import town.championsofequestria.chat.command.ListCommand;
import town.championsofequestria.chat.command.MuteCommand;
import town.championsofequestria.chat.command.QuickMessageCommand;
import town.championsofequestria.chat.command.WhoCommand;
import town.championsofequestria.chat.manager.ChannelManager;
import town.championsofequestria.chat.manager.ChatterManager;

public class ChannelCommandExecutor extends BaseCommandExecutor {

    private DirectMessageCommand dmCommand;
    private FocusCommand focusCommand;
    private IgnoreCommand ignoreCommand;
    private JoinCommand joinCommand;
    private KickCommand kickCommand;
    private LeaveCommand leaveCommand;
    private MuteCommand muteCommand;
    private QuickMessageCommand qmCommand;
    private ListCommand listCommand;
    private WhoCommand whoCommand;

    public ChannelCommandExecutor(ChatPlugin plugin, ChannelManager channelManager, ChatterManager chatterManager) {
        super(plugin, channelManager, chatterManager);
        this.dmCommand = new DirectMessageCommand(plugin);
        new EmoteCommand(plugin);
        this.ignoreCommand = new IgnoreCommand(plugin);
        this.joinCommand = new JoinCommand(plugin);
        this.kickCommand = new KickCommand(plugin);
        this.leaveCommand = new LeaveCommand(plugin);
        this.muteCommand = new MuteCommand(plugin);
        this.qmCommand = new QuickMessageCommand(plugin);
        this.listCommand = new ListCommand(plugin);
        this.focusCommand = new FocusCommand(plugin);
        this.whoCommand = new WhoCommand(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Chatter player = chatterManager.getChatter((Player) sender).get();
            if (args.length != 0) {
                switch (args[0].toLowerCase()) {
                    case "ignore": {
                        if (args.length > 1) {
                            Optional<Chatter> target = chatterManager.getChatter(args[1]);
                            if (target.isPresent()) {
                                return ignoreCommand.execute(player, target.get());
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                            return true;
                        }
                        sender.sendMessage("/ch ignore <player>");
                        return true;
                    }
                    case "join": {
                        if (args.length == 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                return joinCommand.execute(player, channel.get());
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        player.sendMessage("/ch join <channel>");
                        return true;
                    }
                    case "list": {
                        return listCommand.execute(player);
                    }
                    case "leave": {
                        if (args.length == 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                return leaveCommand.execute(player, channel.get());
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        player.sendMessage("/ch leave <channel>");
                        return true;
                    }
                    case "who": {
                        if (args.length == 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                return whoCommand.execute(player, channel.get());
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        player.sendMessage("/ch who <channel>");
                        return true;
                    }
                    case "kick": {
                        if (args.length == 3) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                Optional<Chatter> target = chatterManager.getChatter(args[2]);
                                if (target.isPresent()) {
                                    return kickCommand.execute(sender, target.get(), channel.get());
                                }
                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                                return true;
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        player.sendMessage("/ch kick <channel> <player>");
                        return true;
                    }
                    case "mute": {
                        if (args.length == 3) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                Optional<Chatter> target = chatterManager.getChatter(args[2]);
                                if (target.isPresent()) {
                                    return muteCommand.execute(sender, target.get(), channel.get());
                                }
                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                                return true;
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        player.sendMessage("/ch mute <channel> <player>");
                        return true;
                    }
                    case "qm": {
                        if (args.length > 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                return qmCommand.execute(player, channel.get(), Joiner.on(' ').join(ArrayUtils.subarray(args, 2, args.length)));
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        sender.sendMessage("/ch qm <channel> <message>");
                        return true;
                    }
                    case "pm": {
                        if (args.length == 2) {
                            Optional<Chatter> target = chatterManager.getChatter(args[1]);
                            if (target.isPresent()) {
                                return dmCommand.focus(player, target.get());
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                            return true;
                        }
                        if (args.length > 2) {
                            Optional<Chatter> target = chatterManager.getChatter(args[1]);
                            if (target.isPresent()) {
                                return dmCommand.execute(player, target.get(), Joiner.on(' ').join(ArrayUtils.subarray(args, 2, args.length)));
                            }
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                            return true;
                        }
                        sender.sendMessage("/ch pm player <message>");
                        return true;
                    }
                }
                Optional<StandardChannel> channel = channelManager.getChannel(args[0]);
                if (channel.isPresent()) {
                    return focusCommand.execute(player, channel.get());
                }
                return false;
            }
        }
        if (sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender ccs = (ConsoleCommandSender) sender;
            if (args.length != 0) {
                switch (args[0].toLowerCase()) {
                    case "kick": {
                        if (args.length == 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                Optional<Chatter> target = chatterManager.getChatter(args[2]);
                                if (target.isPresent()) {
                                    return kickCommand.execute(ccs, target.get(), channel.get());
                                }
                                ccs.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                                return true;
                            }
                            ccs.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        ccs.sendMessage("/ch kick <channel> <player>");
                        return true;
                    }
                    case "mute": {
                        if (args.length == 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                Optional<Chatter> target = chatterManager.getChatter(args[2]);
                                if (target.isPresent()) {
                                    return muteCommand.execute(ccs, target.get(), channel.get());
                                }
                                ccs.sendMessage(ChatColor.LIGHT_PURPLE + "Player not found.");
                                return true;
                            }
                            ccs.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        ccs.sendMessage("/ch mute <channel> <player>");
                        return true;
                    }
                    case "list": {
                        return listCommand.execute(ccs);
                    }
                    case "who": {
                        if (args.length == 2) {
                            Optional<StandardChannel> channel = channelManager.getChannel(args[1]);
                            if (channel.isPresent()) {
                                return whoCommand.execute(sender, channel.get());
                            }
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Channel not found.");
                            return true;
                        }
                        sender.sendMessage("/ch who <channel>");
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }
}
