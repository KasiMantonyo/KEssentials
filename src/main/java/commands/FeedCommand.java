package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import org.kasimantonyo.kessentials.KEssentialsUtils;
import utils.CC;

public class FeedCommand extends KEssentialsUtils implements CommandExecutor {

    private final KEssentials plugin;

    public FeedCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("kessentials.feed") || p.hasPermission("VERGAXD")) {
                if (args.length == 0) {
                    p.setFoodLevel(20);
                    p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.feed-success")));
                    return true;
                } else if (args.length == 1) {
                    Player target = plugin.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        target.setFoodLevel(20);
                    } else {
                        p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.player-not-found")));
                    }
                    return true;
                } else {
                    p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.feed-bad-usage")));
                }
            } else {
                p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            }
        } else {
            sender.sendMessage("You must be a player to execute this command");
        }
        return true;
    }
}