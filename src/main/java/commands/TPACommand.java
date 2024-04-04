package commands;

import handlers.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import org.kasimantonyo.kessentials.KEssentialsUtils;
import runnables.SyncPlayerTasks;
import utils.CC;

import java.util.HashMap;
import java.util.Map;

public class TPACommand extends KEssentialsUtils implements CommandExecutor {

    private final KEssentials plugin;

    protected SyncPlayerTasks pr;

    public static void setMap(Player p, Player target) {
        tMap.put(p, target);
    }

    public static void removeTargetTpa(Player p) {
        tMap.remove(p);
    }

    public static Player getTpaMapOf(Player p) {
        return tMap.get(p);
    }

    public static final Map<Player, Player> tMap = new HashMap<>();

    public TPACommand(KEssentials plugin) {

        this.plugin = plugin;
        this.pr = new SyncPlayerTasks(plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tpa")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("kessentials.tpa")) {
                    if (args.length == 0) {
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tpa-bad-usage")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                        return true;
                    }

                    Player target = plugin.getServer().getPlayerExact(args[0]);

                    if (target == null) {
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.player-not-found")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                        return true;
                    }

                    if (target.getName().equalsIgnoreCase(p.getName())) {
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.cannot-tpa-yourself")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                        return true;
                    }

                    if (PlayerManager.tpDeny.contains(target.getName())) {
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tpa-deny-enabled")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                        return true;
                    }

                    setMap(target, p);
                    this.pr.tpaRequestCountdown(this.plugin, target);

                    if (plugin.getMessagesConfig() != null) {
                        for (String playerMessage : plugin.getMessagesConfig().getStringList("messages.teleporting-player")) {
                            p.sendMessage(CC.translate(playerMessage).replace("%target%", target.getName()));
                        }

                        for (String targetMessage : plugin.getMessagesConfig().getStringList("messages.teleporting-target")) {
                            target.sendMessage(CC.translate(targetMessage).replace("%player%", p.getName()));
                        }
                    } else {
                        p.sendMessage("debug #17");
                    }

                    if (args.length >= 2) {
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tpa-bad-usage")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                        return true;
                    }
                }
            } else {
                if (plugin.getMessagesConfig() != null) {
                    sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
                } else {
                    sender.sendMessage("debug #17");
                }
            }
        } else if (command.getName().equalsIgnoreCase("tpaccept")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("kessentials.tpaccept")) {
                    if (args.length == 0) {
                        if (tMap.containsKey(p)) {
                            Player target = getTpaMapOf(p);
                            target.teleport(p.getLocation());
                            removeTargetTpa(p);
                            this.pr.timeTpaccepting = 0;
                            sh.cancelTask(this.pr.tpaTask);
                            if (plugin.getMessagesConfig() != null) {
                                p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tpa-accepted")));
                                target.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tpa-accepted-target")));
                            } else {
                                p.sendMessage("debug #17");
                            }
                            return true;
                        }
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-tpa-requests")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                    } else {
                        if (plugin.getMessagesConfig() != null) {
                            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tpa-bad-usage")));
                        } else {
                            p.sendMessage("debug #17");
                        }
                    }
                } else {
                    if (plugin.getMessagesConfig() != null) {
                        p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
                    } else {
                        p.sendMessage("debug #17");
                    }
                }
            }
        }
        return false;
    }
}
