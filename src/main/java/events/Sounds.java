package events;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.kasimantonyo.kessentials.KEssentials;
import org.kasimantonyo.kessentials.KEssentialsUtils;


public class Sounds extends KEssentialsUtils implements Listener {

    private final KEssentials plugin;

    public Sounds(KEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void MakeSound(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            boolean deathSoundEnabled = plugin.getSoundsConfig().getBoolean("death-sound-enabled", true);

            if (deathSoundEnabled && e.getEntity().hasPermission("kessentials.sound.death")) {
                e.getEntity().playSound(e.getEntity().getLocation(),
                        Sound.valueOf(plugin.getSoundsConfig().getString("death-sound")),
                        plugin.getSoundsConfig().getInt("death-volume"),
                        plugin.getSoundsConfig().getInt("death-pitch"));
            }
        }

    }

    @EventHandler
    public void MakeHotBar(PlayerItemHeldEvent e) {
        if (e.getPlayer().hasPermission("playmoresounds.hotbar")) {
            boolean hotbarSoundEnabled = plugin.getSoundsConfig().getBoolean("hotbar-sound-enabled", true);

            if (hotbarSoundEnabled) {
                Player p = e.getPlayer();
                Location loc = p.getLocation();

                p.playSound(loc, Sound.valueOf(plugin.getSoundsConfig().getString("hotbar-sound")),
                        plugin.getSoundsConfig().getInt("hotbar-volume"), plugin.getSoundsConfig().getInt("hotbar-pitch"));
            }
        }
    }
}
