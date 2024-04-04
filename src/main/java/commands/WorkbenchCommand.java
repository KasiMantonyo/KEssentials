package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

public class WorkbenchCommand implements CommandExecutor {

    private final KEssentials plugin;

    public WorkbenchCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("kessentials.workbench.use")) {
            sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            return true;
        }

        Player player = (Player) sender;
        InventoryView craftingTable = player.openWorkbench(null, true);


        return true;
    }
}



