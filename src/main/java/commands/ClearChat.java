package commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;


public class ClearChat implements CommandExecutor {

    private KEssentials plugin;

    public ClearChat(KEssentials plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("kessentials.clearchat")) {
            sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            return true;
        }
        StringBuilder builder = new StringBuilder(10000);
        for (int i = 0; i < 800; i++)
            Bukkit.broadcastMessage(" ");
        String broadcast = CC.translate(plugin.getMessagesConfig().getString("messages.clearchat-message"));
        broadcast = broadcast.replace("%player%", sender.getName());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(broadcast);
        }
        return true;
    }
}
