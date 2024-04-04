package commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

public class StaffChat implements CommandExecutor {

    private final KEssentials plugin;

    public StaffChat(KEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("kessentials.staffchat")) {
            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getMessagesConfig().getString("messages.no-permission"))));
            return true;
        }
        String message = String.join(" ", (CharSequence[]) args);
        String space = "";

        if (args.length == 0) {
            player.sendMessage(CC.translate(PlaceholderAPI.setPlaceholders(player, plugin.getMessagesConfig().getString("messages.staff-chat-use"))));
            return true;
        }
        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("kessentials.staffchat")) {
                String staffChatMessage = PlaceholderAPI.setPlaceholders(staff, plugin.getMessagesConfig().getString("messages.staff-chat-format").replace("%player%", player.getDisplayName() + space) + message);
                staff.sendMessage(CC.translate(staffChatMessage));
            }
        }
        return true;
    }
}