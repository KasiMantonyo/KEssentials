package commands;

import handlers.player.PlayerHashes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

public class FlyCommand implements CommandExecutor {

    private final KEssentials plugin;

    public FlyCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if ((sender instanceof Player)) {

            Player p = (Player) sender;

            if (p.hasPermission("kessentials.fly")) {
                if (args.length == 0) {
                    if (!p.getAllowFlight()) {
                        p.setAllowFlight(true);
                        p.setFlying(true);

                        p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.fly-enabled")));
                        PlayerHashes.enabledFly.put(p.getName(), Boolean.valueOf(true));
                        return true;
                    }
                    PlayerHashes.enabledFly.put(p.getName(), Boolean.valueOf(false));
                    p.setAllowFlight(false);
                    p.setFlying(false);

                    p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.fly-disabled")));
                    return true;

                }
            } else {
                sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
                return true;
            }
            return false;
        }
        return false;
    }
}