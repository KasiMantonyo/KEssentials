// En la clase Hologram
package utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hologram {
    private String name;
    private Location location;
    private List<String> texts;
    private List<ArmorStand> stands;

    public Hologram(String name, Location location, String... texts) {
        this.name = name;
        this.location = location;
        this.texts = new ArrayList<>();
        this.stands = new ArrayList<>();
        setTexts(texts);
        spawn();
    }

    public List<String> getTexts() {
        return this.texts;
    }
    public void despawn() {
        this.stands.forEach(x -> x.remove());
    }
    public Location getLocation() {
        return this.location;
    }

    public void setTexts(String... texts) {
        this.texts.clear();
        this.texts.addAll(Arrays.asList(texts));

        // Clear the existing stands
        this.stands.forEach(ArmorStand::remove);
        this.stands.clear();

        // Spawn new stands based on the updated texts
        spawn();
    }

    public void addLine(String text) {
        texts.add(text);
        spawnLine(texts.size() - 1);
        update();
    }

    public void removeLine(int index) {
        if (index >= 0 && index < texts.size()) {
            texts.remove(index);
            stands.get(index).remove();
            stands.remove(index);
            for (int i = index; i < stands.size(); i++) {
                Location loc = stands.get(i).getLocation();
                loc.setY(loc.getY() + 0.3);
                stands.get(i).teleport(loc);
            }
            update();
        }
    }

    public void teleport(Location location) {
        this.location = location.clone();
        for (int i = 0; i < stands.size(); i++) {
            Location loc = location.clone();
            loc.setY(location.getY() - i * 0.3);
            stands.get(i).teleport(loc);
        }
    }

    private void spawnLine(int index) {
        Location spawn = location.clone();
        spawn.setY(location.getY() - index * 0.3);
        ArmorStand stand = location.getWorld().spawn(spawn, ArmorStand.class);
        // Configura el ArmorStand
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(CC.translate(texts.get(index)));
        stand.setGravity(false);
        stand.setMarker(true);
        stands.add(index, stand);
    }

    private void spawn() {
        for (int i = 0; i < texts.size(); i++) {
            spawnLine(i);
        }
    }

    private void update() {
        for (int i = 0; i < texts.size(); i++) {
            String translatedText = ChatColor.translateAlternateColorCodes('&', texts.get(i));
            stands.get(i).setCustomName(translatedText);
        }
    }
    }

