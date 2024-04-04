package org.kasimantonyo.kessentials;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KEssentialsUtils {

    // Algunas clases no extenderan KEssentials utils ya ser que se me haya
    // olvidado, y eso, pero si se me olvida algo reviso esta clase y pongo
    // sus vainas x default

    private KEssentials plugin;

    public BukkitScheduler sh = Bukkit.getScheduler();

    public Server server = Bukkit.getServer();


    public boolean hasPapi;

    // FUTUROS: GETEAR SPAWN LOCATION Y MAS COSITAS XD
    public final Set<ArmorStand> armorStands = new HashSet<>();

    public final DecimalFormat decimalFormat = new DecimalFormat("###.#");
    // verga


    public static ArrayList<Player> frozenPlayers = new ArrayList<>();

    private Player reporter;

    private Player target;

    private String reason;




}