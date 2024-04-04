package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import org.kasimantonyo.kessentials.KEssentialsUtils;
import utils.CC;

public class FHCommands extends KEssentialsUtils implements CommandExecutor {

    private KEssentials plugin;

    public FHCommands(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("kessentials.heal") || p.hasPermission("VERGAXD")) {
                if (args.length == 0) {
                    p.setHealth(20.0);
                    p.setFoodLevel(20);
                    p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.health-healed")));
                    return true;
                } else if (args.length == 1) {
                    Player target = plugin.getServer().getPlayerExact(args[0]);
                    if (target != null) {
                        target.setHealth(20.0);
                        target.setFoodLevel(20);
                    } else {
                        p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.player-not-found")));
                    }
                    return true;
                } else {
                    p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.health-bad-usage")));
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