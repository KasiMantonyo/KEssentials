package runnables;

import commands.TPACommand;
import org.bukkit.entity.Player;
import org.kasimantonyo.kessentials.KEssentials;
import org.kasimantonyo.kessentials.KEssentialsUtils;

public class SyncPlayerTasks extends KEssentialsUtils {

    private KEssentials plugin;

    public int timeTpaccepting = 45;

    public int tpaTask;

    public SyncPlayerTasks(KEssentials plugin) {
        this.plugin = plugin;
    }

    public void tpaRequestCountdown(KEssentials plugin, Player p) {
        this.tpaTask = sh.scheduleSyncRepeatingTask((KEssentials) plugin, () -> {
            if (!TPACommand.tMap.containsKey(p))
                return;
            this.timeTpaccepting--;
            if (this.timeTpaccepting == 0)
                TPACommand.removeTargetTpa(p);
        }, 0L, 20L);
    }
}
