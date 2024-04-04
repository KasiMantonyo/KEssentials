package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;
import utils.HologramManager;

public class KEssentialsInfo implements CommandExecutor {

    private final KEssentials plugin;

    public KEssentialsInfo(KEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {

        if (a.length == 0) {
            s.sendMessage(CC.translate("&f"));
            s.sendMessage(CC.translate("&fUsing &b&lKEssentials&b"));
            s.sendMessage(CC.translate("&fAuthor: &eKasi777"));
            s.sendMessage(CC.translate("&fVersion: &e" + plugin.getDescription().getVersion()));
            s.sendMessage(CC.translate("&f"));
            s.sendMessage(CC.translate("&fLibraries/Plugins Available:"));
            s.sendMessage(CC.translate("&8- &eKDirecto &7use /download KDirecto"));
            s.sendMessage(CC.translate("&f"));
            return true;
        }

        if (a.length > 0) {
            if (a[0].equalsIgnoreCase("reload")) {


                if (!s.hasPermission("kessentials.reload")) {
                    s.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
                    return true;
                }
                // Recargar messages.yml
                FileConfiguration messagesConfig = plugin.getMessagesConfig();
                plugin.loadMessagesConfig();

                // Recargar config.yml
                FileConfiguration config = plugin.getConfig();
                plugin.loadConfig();

                // Recargar sounds.yml
                FileConfiguration soundsConfig = plugin.getSoundsConfig();
                plugin.loadSoundsConfig();

                // Hola

                s.sendMessage(CC.translate(("&eReloaded! &7[config.yml, sounds.yml, messages.yml]")));

            } else {
                // Subcomando noexistente
                s.sendMessage(CC.translate(("&cThat command does not exist.")));
            }
            return true;
        }
                return false;
            }
        }


