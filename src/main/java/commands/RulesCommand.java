package commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

import java.util.List;

public class RulesCommand implements CommandExecutor {
    private final KEssentials plugin;

    public RulesCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cOnly players can use this command."));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("kessentials.rules")) {
            player.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            return true;
        }

        // Ver si las reglas están habilitadas en la config
        boolean rulesEnabled = plugin.getConfig().getBoolean("rules-enabled", true);

        if (rulesEnabled) {
            // Obtenemos las rules desde config.yml
            List<String> rules = plugin.getConfig().getStringList("rules");

            for (String rule : rules) {
                player.sendMessage(CC.translate(rule));
            }
            String soundName = plugin.getConfig().getString("rules-sound", "NONE");
            if (!soundName.equalsIgnoreCase("NONE")) {
                try {
                    Sound sound = Sound.valueOf(soundName);
                    player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
                } catch (IllegalArgumentException e) {
                    player.sendMessage(CC.translate("&cSound of the section rules is invalid or does not exist."));
                }
            }
        } else {
            player.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.disable-sections")));
        }

        return true;
    }
}