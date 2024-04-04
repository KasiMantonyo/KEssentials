package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

import java.lang.reflect.Field;

public class PingCommand implements CommandExecutor {
    private final KEssentials plugin;

    public PingCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int ping = getPing(player);
            String message = (CC.translate(plugin.getMessagesConfig().getString("messages.ping-message")));
            message = message.replace("%ping%", String.valueOf(ping));
            player.sendMessage(message);
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }

    private int getPing(Player player) {
        int ping = 0;
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Field pingField = entityPlayer.getClass().getField("ping");
            ping = pingField.getInt(entityPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ping;
    }


    }
