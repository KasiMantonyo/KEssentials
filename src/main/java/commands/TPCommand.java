package commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import org.kasimantonyo.kessentials.KEssentialsUtils;
import utils.CC;

import static org.bukkit.Bukkit.getServer;

public class TPCommand implements CommandExecutor {

    private final KEssentials plugin;

    public TPCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
                sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.tp-usage")));
                return true;
        }

        Player target = getServer().getPlayer(args[0]);

        if (!player.hasPermission("kessentials.tp")) {
            player.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            return true;
        }

        if (target == null) {
            sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.player-not-found").replace("%player%", args[0])));
            return true;
        }

        player.teleport(target.getLocation());
        player.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.teleport-msg").replace("%player%", target.getName())));
        return true;
    }
}
