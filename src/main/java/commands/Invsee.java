package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

public class Invsee implements CommandExecutor {

    private KEssentials plugin;

    public Invsee(KEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("kessentials.invsee")) {
                if (args.length == 0) {
                    p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.invsee-bad-usage")));
                    return true;
                }
                Player target = Bukkit.getServer().getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.player-not-found")));
                    return true;
                }
                p.openInventory((Inventory) target.getInventory());
                return true; // Agregado para indicar que el comando se manejó correctamente.
            } else {
                sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            }
        } else {
            sender.sendMessage(CC.translate("&cYou must be a player to use this command!"));
            return true;
        }
        return false; // En caso de que no se cumpla ninguna de las condiciones anteriores.
    }
}
