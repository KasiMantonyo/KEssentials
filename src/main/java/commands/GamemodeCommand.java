package commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.GameMode;
import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

public class GamemodeCommand implements CommandExecutor {
    private KEssentials plugin;

    public GamemodeCommand(KEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cYou must be a player to use this command!"));
            return true;
        }

        Player p = (Player) sender;

        // Verificar si el jugador tiene el permiso 'kessentials.gamemodes'
        if (!p.hasPermission("kessentials.gamemodes")) {
            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.gamemode-usage")));
            return true;
        }

        GameMode gameMode;
        switch (args[0].toLowerCase()) {
            case "0":
            case "surv":
            case "survival":
                gameMode = GameMode.SURVIVAL;
                break;
            case "1":
            case "cr":
            case "creative":
                gameMode = GameMode.CREATIVE;
                break;
            case "2":
            case "adv":
            case "adventure":
                gameMode = GameMode.ADVENTURE;
                break;
            case "3":
            case "spec":
            case "spectator":
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                p.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.invalid-gamemode")));
                return true;
        }

        p.setGameMode(gameMode);
        String gamemodeChangedMessage = plugin.getMessagesConfig().getString("messages.gamemode-changed");
        gamemodeChangedMessage = gamemodeChangedMessage.replace("%gamemode%", gameMode.name());
        p.sendMessage(CC.translate(gamemodeChangedMessage));

        return true;
    }
}
