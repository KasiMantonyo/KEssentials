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

public final class KDirectoPlugin implements CommandExecutor {

    private KEssentials plugin;


    public KDirectoPlugin(KEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("KDirecto")) {
            String pluginName = "KDirecto";
            File pluginsFolder = new File("plugins");

            // Verificar si el plugin KDirecto ya está instalado
            File[] pluginFiles = pluginsFolder.listFiles();
            if (pluginFiles != null) {
                for (File file : pluginFiles) {
                    if (file.isFile() && file.getName().endsWith(".jar") && file.getName().equalsIgnoreCase(pluginName + ".jar")) {
                        sender.sendMessage(ChatColor.GREEN + "El plugin KDirecto ya está instalado.");
                        return true;
                    }
                }
            }

            // Si el plugin KDirecto no está instalado, descargarlo y colocarlo en la carpeta "plugins"
            String downloadUrl = "https://github.com/KasiMantonyo/KDirecto/releases/download/1.3/KDirecto.jar";

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

                    sender.sendMessage(ChatColor.GREEN + "El plugin KDirecto ha sido descargado e instalado correctamente.");
                } else {
                    sender.sendMessage(ChatColor.RED + "No se pudo descargar el plugin KDirecto. Respuesta del servidor: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "Ocurrió un error durante la descarga e instalación del plugin KDirecto.");
            }

            return true;
        }
        return false;
    }
}
