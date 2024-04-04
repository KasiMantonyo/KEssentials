package commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;
import utils.Hologram;
import utils.HologramManager;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;

public class HologramCommand implements CommandExecutor {

    private HologramManager hologramManager;

    public HologramCommand(KEssentials plugin) {
        this.hologramManager = new HologramManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("kessentials.holograms")) {
                player.sendMessage(CC.translate("&cYou don´t have permission to perform this command."));
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(CC.translate("&cBad usage! Use /hologram (create/delete/addline/delline/movehere)"));
                return true;
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("create")) {
                String hologramName = args[1];
                Location location = player.getLocation();
                String[] lines = String.join(" ", Arrays.copyOfRange(args, 2, args.length)).split(",");

                if (hologramManager.getHologram(hologramName) != null) {
                    player.sendMessage(CC.translate("&cThe hologram does not exist."));
                } else {
                    hologramManager.createHologram(hologramName, location, lines);
                    player.sendMessage(CC.translate("&aHologram created."));
                }
                return true;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
                String hologramName = args[1];
                if (hologramManager.getHologram(hologramName) != null) {
                    hologramManager.deleteHologram(hologramName);
                    player.sendMessage(CC.translate("&aThe hologram has been deleted."));
                } else {
                    player.sendMessage(CC.translate("&cTheres no a hologram with that name!"));
                }
                return true;
            } else if (args.length >= 2 && args[0].equalsIgnoreCase("movehere")) {
                String hologramName = args[1];
                if (hologramManager.getHologram(hologramName) != null) {
                    Hologram hologram = hologramManager.getHologram(hologramName);
                    Location newLocation = player.getLocation();
                    hologram.teleport(newLocation);
                    player.sendMessage(CC.translate("&aThe hologram has been moved to your location."));
                } else {
                    player.sendMessage(CC.translate("&cThe hologram does not exist."));


                }
                return true;
            } else if (args.length >= 3 && args[0].equalsIgnoreCase("addline")) {
                String hologramName = args[1];
                Hologram hologram = hologramManager.getHologram(hologramName);
                if (hologram != null) {
                    String line = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
                    hologram.addLine(line);
                    player.sendMessage(CC.translate("&aLine added."));
                } else {
                    player.sendMessage(CC.translate("&cThe hologram does not exist."));
                }
                return true;
            } else if (args.length == 3 && args[0].equalsIgnoreCase("delline")) {
                String hologramName = args[1];
                int lineIndex = Integer.parseInt(args[2]);
                Hologram hologram = hologramManager.getHologram(hologramName);
                if (hologram != null) {
                    if (lineIndex >= 0 && lineIndex < hologram.getTexts().size()) {
                        hologram.removeLine(lineIndex);
                        player.sendMessage(CC.translate("&aLine deleted."));
                    } else {
                        player.sendMessage(CC.translate("&cInvalid number of line"));
                    }
                } else {
                    player.sendMessage(CC.translate("&cTheres no a hologram with that name!"));
                }
                return true;
            } else {
                player.sendMessage(CC.translate("&cBad usage! Available Commands: /hologram (create/delete/addline/delline/movehere)"));
                return true;
            }
        }
        return false;
    }
}