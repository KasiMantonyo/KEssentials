package commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.kasimantonyo.kessentials.KEssentials;
import utils.CC;

public final class Plugins implements CommandExecutor {

    private KEssentials plugin;


    public Plugins(KEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&b&lKEssentials &8|| &fPlugins for &e&nKSeries&r&f:"));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&eKDirecto &7(Spanish Version) v1.3 &8|| &f12KB"));
            sender.sendMessage(CC.translate("&8- &fKDirecto is a highly customizable live-stream plugin."));
            sender.sendMessage(CC.translate(""));
            sender.sendMessage(CC.translate("&eUse &f/download (plugin)&r&f to download."));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("KDirecto")) {

            // KDirecto es un plugin que agrega /directo en tu servidor
            // Link: https://www.spigotmc.org/resources/kdirecto-directo-en-tu-servidor.109954/

            // Verificar si el jugador tiene el permiso 'kessentials.kseries'
            if (!sender.hasPermission("kessentials.kseries")) {
                sender.sendMessage(CC.translate(plugin.getMessagesConfig().getString("messages.no-permission")));
                return true;
            }

            // Resto del código para descargar e instalar el plugin KDirecto
            String pluginName = "KDirecto";
            File pluginsFolder = new File("plugins");

            // Verificar si el plugin KDirecto ya está instalado
            File[] pluginFiles = pluginsFolder.listFiles();
            if (pluginFiles != null) {
                for (File file : pluginFiles) {
                    if (file.isFile() && file.getName().endsWith(".jar") && file.getName().equalsIgnoreCase(pluginName + ".jar")) {
                        sender.sendMessage(CC.translate("&eKDirecto is already installed."));
                        return true;
                    }
                }
            }

            // Si el plugin KDirecto no está instalado, descargarlo y colocarlo en la carpeta "plugins"
            String downloadUrl = "https://github.com/KasiMantonyo/KDirecto/releases/download/1.3/KDirecto.jar"; // Mi github

            // Implementa la lógica para descargar el archivo desde la URL y guardar el archivo en la carpeta "plugins"
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Verifica la respuesta del servidor antes de la descarga
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Crea un archivo en la carpeta "plugins" con el nombre "KDirecto.jar"
                    File outputFile = new File(pluginsFolder, pluginName + ".jar");
                    FileOutputStream output = new FileOutputStream(outputFile);

                    // Descarga el archivo JAR
                    InputStream input = connection.getInputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                    output.close();
                    input.close();

                    sender.sendMessage(CC.translate("&eKDirecto has been installed. Check your plugins folder and then restart your server"));
                } else {
                    // Dará 404
                    sender.sendMessage(CC.translate("&cError while downloading KDirecto. Code:" + responseCode));
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Error
                sender.sendMessage(CC.translate("&cError while downloading KDirecto."));
            }

            return true;
        }
        sender.sendMessage(CC.translate("&cThis plugin does not exist!"));
        return true;
    }
}