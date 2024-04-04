package events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.ChatColor;
import org.bukkit.event.player.*;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import org.kasimantonyo.kessentials.KEssentials;

import org.kasimantonyo.kessentials.KEssentialsUtils;
import utils.CC;

import java.util.List;
import java.util.Set;

import static org.bukkit.Bukkit.getServer;

public class General extends KEssentialsUtils implements Listener {
    private KEssentials plugin;

    public General(KEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean welcomeEnabled = plugin.getConfig().getBoolean("welcome-message-enabled", true);

        if (welcomeEnabled) {
            List<String> welcomeMessage = plugin.getConfig().getStringList("welcome-message");
            for (String line : welcomeMessage) {
                String message = PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', line));
                player.sendMessage(message);
            }
        }

        boolean entradaSalida = this.plugin.getConfig().getBoolean("message-join-and-leave", true);
        if (entradaSalida) {
            String joinMessage = this.plugin.getConfig().getString("join-message");
            if (joinMessage != null) {
                joinMessage = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinMessage);
                joinMessage = joinMessage.replace("%player%", event.getPlayer().getDisplayName());
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
                event.setJoinMessage("");
            }
            // Reproducir sonido de entrada (join)
            String joinSound = this.plugin.getConfig().getString("join-sound");
            if (!joinSound.equalsIgnoreCase("NONE")) {
                player.playSound(player.getLocation(), Sound.valueOf(joinSound), 1.0F, 1.0F);
            }
            try {
                Sound sound = Sound.valueOf(joinSound);
                player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
            } catch (IllegalArgumentException e) {
                player.sendMessage(CC.translate("&cSound of the section join-sound is invalid or does not exist."));
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        boolean entradaSalida = this.plugin.getConfig().getBoolean("message-join-and-leave", true);
        if (!entradaSalida)
            return;
        String leaveMessage = this.plugin.getConfig().getString("quit-message");
        if (leaveMessage != null) {
            leaveMessage = PlaceholderAPI.setPlaceholders(e.getPlayer(), leaveMessage);
            leaveMessage = leaveMessage.replace("%player%", e.getPlayer().getDisplayName());
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
            e.setQuitMessage("");
        }
        // Reproducir sonido de salida (leave)
        String leaveSound = this.plugin.getConfig().getString("quit-sound");
        if (!leaveSound.equalsIgnoreCase("NONE")) {
            player.playSound(player.getLocation(), Sound.valueOf(leaveSound), 1.0F, 1.0F);
        }
        try {
            Sound sound = Sound.valueOf(leaveSound);
            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
        } catch (IllegalArgumentException o) {
            player.sendMessage(CC.translate("&cSound of the section quit-sound is invalid or does not exist."));
        }
    }



    // Esto se elimnara, solamente era un testeo
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        // Booleaneo que verifica si LuckPerms está disponible
        boolean luckPermsAvailable = Bukkit.getPluginManager().isPluginEnabled("LuckPerms");

        if (!plugin.getConfig().getBoolean("ChatFormat.chatformat-enabled")) {
            return; // Si ChatFormat.enabled es false, no se aplica el formato de chat
        }

        Player sender = e.getPlayer();
        Set<Player> recipients = e.getRecipients();

        for (Player player : server.getOnlinePlayers()) {
            if (!player.getWorld().getName().equals(sender.getWorld().getName())) {
                recipients.remove(player);
                continue;
            }

            String playerName = player.getName();
            String chatFormat = plugin.getConfig().getString("ChatFormat.chat-format");



            // Remplazamos variables por default
            chatFormat = chatFormat
                    .replace("%player%", playerName)
                    .replace("{MESSAGE}", e.getMessage());

            // Utilizamos PlaceholderAPI para reemplazar los marcadores de posición
            chatFormat = PlaceholderAPI.setPlaceholders(player, chatFormat);

            // Envíamos el mensaje formateado a todos los jugadores
            getServer().broadcastMessage(CC.translate(chatFormat));
        }

        // Cancela el evento para evitar que el mensaje se muestre dos veces en el chat
        e.setCancelled(true);
    }


    private String getPlayerPrefix(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user != null) {
            CachedMetaData metaData = user.getCachedData().getMetaData();
            String prefix = metaData.getPrefix();
            if (prefix != null) {
                return prefix;
            }
        }
        return "";
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        Player jugador = e.getPlayer();

        String[] arrayos;
        int j = (arrayos = e.getMessage().split(" ")).length;
        for (int i = 0; i < j; i++) {
            String cmdsBloqueado = arrayos[i];
            if (plugin.getConfig().getString("BlockCommands").contains(cmdsBloqueado.toLowerCase())) {
                e.setCancelled(true);
                // futuro jugador.sendMessage(CC.translate(Config.Message))) future
                jugador.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
                return;
            }

        }


    }




        }






// public void coloredSign(SignChangeEvent e) {
// if (!plugin.getConfig().getBoolean("signs.sign-colors"))
//  return;
// if (!e.getPlayer().hasPermission("kessentials.coloredsigns") || !e.getPlayer().hasPermission("ke.coloredsigns"))
//  return;
// e.setLine(0, e.getLine(0).replace('&','§'));
// e.setLine(1, e.getLine(1).replace('&', '§'));
//  e.setLine(2, e.getLine(2).replace('&', '§'));
// e.setLine(3, e.getLine(3).replace('&', '§'));t


// SOON
//   @EventHandler(priority = EventPriority.HIGH)
//public void test(AsyncPlayerChatEvent e) {
// String message = e.getMessage();
//if (message.contains(":heart:")) {
//  e.setMessage(message.replace(":heart:", "♥"));
// }
// }