// En la clase HologramManager
package utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HologramManager {
    private final JavaPlugin plugin;
    private final FileConfiguration hologramConfig;
    private final File hologramFile;
    private final Map<String, Hologram> holograms;

    public HologramManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.hologramFile = new File(plugin.getDataFolder(), "holograms.yml");
        this.hologramConfig = YamlConfiguration.loadConfiguration(hologramFile);
        this.holograms = new HashMap<>();

        if (!hologramFile.exists()) {
            plugin.saveResource("holograms.yml", false);
        }

        loadHolograms();
    }

    public Hologram createHologram(String hologramName, Location location, String[] lines) {
        Hologram hologram = new Hologram(hologramName, location, lines);
        holograms.put(hologramName, hologram);
        saveHologram(hologramName);
        return hologram;
    }

    public void deleteHologram(String hologramName) {
        if (holograms.containsKey(hologramName)) {
            Hologram hologram = holograms.get(hologramName);
            hologram.despawn();
            holograms.remove(hologramName);
            hologramConfig.set("holograms." + hologramName, null);
            saveHolograms();
        }
    }
    public void moveHologram(String hologramName, Location newLocation) {
        Hologram hologram = holograms.get(hologramName);
        if (hologram != null) {
            hologram.teleport(newLocation);
            saveHologram(hologramName);
        }
    }

    public void addLineToHologram(String hologramName, String line) {
        Hologram hologram = holograms.get(hologramName);
        if (hologram != null) {
            hologram.addLine(line);
            saveHologram(hologramName);
        }
    }

    public void deleteLineFromHologram(String hologramName, int lineIndex) {
        Hologram hologram = holograms.get(hologramName);
        if (hologram != null) {
            if (lineIndex >= 0 && lineIndex < hologram.getTexts().size()) {
                hologram.removeLine(lineIndex);
                saveHologram(hologramName);
            }
        }
    }



    public Hologram getHologram(String hologramName) {
        return holograms.get(hologramName);
    }

    public Set<String> getHologramNames() {
        return holograms.keySet();
    }

    public void saveHolograms() {
        try {
            hologramConfig.save(hologramFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHolograms() {
        holograms.clear();
        if (hologramConfig.isConfigurationSection("holograms")) {
            for (String key : hologramConfig.getConfigurationSection("holograms").getKeys(false)) {
                String path = "holograms." + key;
                String locationString = hologramConfig.getString(path + ".location");
                Location location = locationFromString(locationString);
                String[] lines = hologramConfig.getStringList(path + ".lines").toArray(new String[0]);
                Hologram hologram = new Hologram(key, location, lines);
                holograms.put(key, hologram);
            }
        }
    }

    private void saveHologram(String hologramName) {
        Hologram hologram = holograms.get(hologramName);
        if (hologram != null) {
            String path = "holograms." + hologramName;
            String locationString = locationToString(hologram.getLocation());
            hologramConfig.set(path + ".location", locationString);
            hologramConfig.set(path + ".lines", hologram.getTexts());
            saveHolograms();
        }
    }

    private String locationToString(Location location) {
        if (location != null) {
            return location.getWorld().getName() + "," +
                    location.getX() + "," +
                    location.getY() + "," +
                    location.getZ() + "," +
                    location.getYaw() + "," +
                    location.getPitch();
        }
        return "";
    }

    private Location locationFromString(String locationString) {
        if (locationString != null && !locationString.isEmpty()) {
            String[] parts = locationString.split(",");
            if (parts.length == 6) {
                String worldName = parts[0];
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                double z = Double.parseDouble(parts[3]);
                float yaw = Float.parseFloat(parts[4]);
                float pitch = Float.parseFloat(parts[5]);

                return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            }
        }
        return null;
    }
}
